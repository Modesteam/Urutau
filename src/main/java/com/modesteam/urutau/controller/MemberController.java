package com.modesteam.urutau.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.Restrict;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.Page;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import io.github.projecturutau.vraptor.handler.FlashMessage;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private final Result result;
	private final FlashMessage flash;
	private final UserSession userSession;
	private final UserService userService;
	private final ProjectService projectService;

	/**
	 * @deprecated CDI eye only
	 */
	public MemberController() {
		this(null, null, null, null, null);
	}

	@Inject
	public MemberController(Result result, FlashMessage flash, ProjectService projectService,
			UserSession userSession, UserService userService) {
		this.flash = flash;
		this.result = result;
		this.userSession = userSession;
		this.userService = userService;
		this.projectService = projectService;
	}

	@Post("/project/addMember")
	@Restrict
	public void addMember(Long projectId, String member) {
		Project project = projectService.find(projectId);

		logger.info("Request to put new member to project " + project.getTitle());

		if (!project.isAdministrator(userSession.getUserLogged())) {
			flash.use("error").toShow("not_permitted")
				.redirectTo(ApplicationController.class).invalidRequest();
		} else {
			UrutaUser userFoundByEmail = userService.findBy("email", member);

			if (userFoundByEmail != null) {
				project.addMember(userFoundByEmail);
				flash.use("success").toShow("new_member_added").stay();
			} else {
				flash.use("error").toShow("non_existent_member").stay();
			}
		}
	}

	@Get("project/{projectId}/members/{page.number}")
	@Restrict
	public void showMembers(Long projectId, Page page) {
		Project project = projectService.find(projectId);

		logger.info("Show members of project " + project.getTitle());

		List<UrutaUser> members = new ArrayList<>();

		// Lazy mode allows load each element when requested
		for (UrutaUser user : project.getMembers()) {
			if (project.getMembers().indexOf(user) < page.getLastPositionInPage()) {
				members.add(user);
			} else {
				break;
			}
		}

		result.include("projectID", projectId);
		result.include("members", members);
		result.include("page", page);
	}

	/**
	 * Remove a member of project
	 */
	@Delete("project/removeMember")
	@Restrict
	public void removeMember(Long userID, Long projectID) {
		Project project = projectService.find(projectID);

		if (project.isAdministrator(userSession.getUserLogged())) {
			UrutaUser user = userService.find(userID);
			project.removeMember(user);

			flash.use("success").toShow("member_deleted").stay();
		} else {
			flash.use("error").toShow("not_permitted")
				.redirectTo(ApplicationController.class).invalidRequest();
		}
	}
}
