package com.modesteam.urutau.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

/**
 * This class is responsible to manager simple operations of requirements! The
 * systems operations are received by the path /requirement followed by the
 * operation defined path.
 */
@Controller
public class RequirementController {

	private static final Logger logger = LoggerFactory.getLogger(RequirementController.class);

	private static final int DEFAULT_PAGINATION_SIZE = 5;

	private final Result result;
	private final MessageHandler messageHandler;
	private final ErrorMessageHandler errorMessageHandler;
	private final RequirementService requirementService;

	/**
	 * @deprecated CDI eyes only
	 */
	public RequirementController() {
		this(null, null, null, null);
	}

	@Inject
	public RequirementController(Result result, MessageHandler messageHandler,
			ErrorMessageHandler errorMessageHandler,
			RequirementService requirementService) {
		this.result = result;
		this.messageHandler = messageHandler;
		this.errorMessageHandler = errorMessageHandler;
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

		errorMessageHandler.validates(ContextPlace.PROJECT_PANEL);
		
		errorMessageHandler.when(requirement == null)
			.show("requirement_no_exist")
			.redirectingTo(ApplicationController.class)
			.dificultError();

		result.include(requirement);
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
	@Get("{projectID}/paginate/{page}")
	public void paginate(long projectID, long page) {
		long upperLimit = page + DEFAULT_PAGINATION_SIZE;
		List<Requirement> requirements = requirementService
				.desc("dateOfCreation")
				.between(page, upperLimit)
				.find()
				.where("project_id=" + projectID);

		result.include("requirements", requirements);
	}

	/**
	 * This method is used to delete one requirement
	 */
	@Delete("/requirement")
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
			errorMessageHandler.validates(ContextPlace.ERROR);

			errorMessageHandler
				.add("operation_unsuccessful")
				.redirectingTo(ProjectController.class).show(requirement.getProject().getId());
		}
		
		messageHandler.use(ContextPlace.PROJECT_PANEL)
			.show("requirement_deleted")
			.redirectTo(ProjectController.class).show(requirement.getProject().getId());;
	}

	@View
	public void detailRequirement() {
	}

	@View
	public void showExclusionResult() {
	}

}
