package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.Restrict;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.system.Page;
import com.modesteam.urutau.service.RequirementService;
import com.modesteam.urutau.service.persistence.OrderEnum;
import com.modesteam.urutau.service.persistence.SearchOptions;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import io.github.projecturutau.vraptor.handler.FlashMessage;

/**
 * This class is responsible to manager simple operations of requirements! The
 * systems operations are received by the path /requirement followed by the
 * operation defined path.
 */
@Controller
public class RequirementController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	private final Result result;
	private final FlashMessage flash;
	private final RequirementService requirementService;

	/**
	 * @deprecated CDI eyes only
	 */
	public RequirementController() {
		this(null, null, null);
	}

	@Inject
	public RequirementController(Result result, FlashMessage flash,
			RequirementService requirementService) {
		this.flash = flash;
		this.result = result;
		this.requirementService = requirementService;
	}

	/**
	 * Show a requirement that has a certain id and title
	 * 
	 * @param id
	 *            Unique attribute
	 * @param title
	 *            various requirement can have same title
	 * 
	 * @return {@link Requirement} requirement from database
	 * 
	 * @throws UnsupportedEncodingException
	 *             invalid characters or decodes fails
	 */
	@Get
	@Path("/show/{id}/{title}")
	public void show(Long id, String title) throws UnsupportedEncodingException {
		String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8.name());

		logger.info("Show requirement " + title);

		Requirement requirement = requirementService.getBy(id, decodedTitle);

		if(requirement == null) {
			flash.use("success")
				.toShow("requirement_no_exist")
				.redirectTo(ApplicationController.class)
				.dificultError();
		} else {			
			result.include(requirement);
		}
	}

	/**
	 * Paginate requirements into home page of projects
	 * 
	 * @param page,
	 *            current page
	 * 
	 * @return List of {@link Requirement} to be easy display into home page of a
	 *         project
	 */
	@Get("{projectID}/paginate/{page.number}")
	public void paginate(long projectID, Page page) {
		logger.info("Paging " + page.getFirstPositionInPage() 
			+ " until " + page.getLastPositionInPage());

		@SuppressWarnings("unchecked")
		List<Requirement> requirements = requirementService
				.searchBy(
						new SearchOptions("project_id", projectID, 
								"dateOfCreation", OrderEnum.DESC))
				.setFirstResult(page.getFirstPositionInPage())
				.setMaxResults(page.getLastPositionInPage())
				.getResultList();

		logger.info("Getting " + requirements.size() + " items");

		result.include("requirements", requirements);
		result.include("page", page);
	}

	/**
	 * This method is used to delete one requirement
	 */
	@Delete("/requirement")
	@Restrict
	public void delete(Long requirementID) {
		logger.info("The artifact with the id " + requirementID + " is solicitated for exclusion");

		Requirement requirement = requirementService.find(requirementID);

		try {
			if(requirement != null) {
				requirementService.delete(requirement);
			} else {
				result.redirectTo(ApplicationController.class).invalidRequest();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			flash.use("error")
				.toShow("operation_unsuccessful")
				.redirectTo(ProjectController.class).show(requirement.getProject().getId());
		}

		flash.use("success").toShow("requirement_deleted")
			.redirectTo(ProjectController.class)
			.show(requirement.getProject().getId());
	}

	@View
	public void detailRequirement() {
	}

	@View
	public void showExclusionResult() {
	}

}
