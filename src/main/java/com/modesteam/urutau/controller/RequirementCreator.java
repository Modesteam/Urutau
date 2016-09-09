package com.modesteam.urutau.controller;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

/**
 * This is a implementation of {@link EntityCreator}, part of pattern abstract
 * factory He was born to reduce the coupling and increase your cohesion of
 * {@link RequirementController},
 */
@Controller
@Path("/requirement")
public class RequirementCreator {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	private static final String PROJECT_ID_INPUT_VALUE = "projectID";

	// Objects to be injected
	private final Result result;
	private final MessageHandler messageHandler;
	private final ErrorMessageHandler errorHandler;
	private final RequirementService service;
	private final RequirementFormatter formatter;
	private Callable<RequirementCreator> callable;

	/**
	 * CDI only eye
	 */
	public RequirementCreator() {
		this(null, null, null, null, null);
	}

	@Inject
	public RequirementCreator(Result result, MessageHandler messageHandler,
			ErrorMessageHandler errorHandler, RequirementService service,
			RequirementFormatter formatter) {
		this.result = result;
		this.messageHandler = messageHandler;
		this.errorHandler = errorHandler;
		this.service = service;
		this.formatter = formatter;
	}

	@Post
	public void createGeneric(@NotNull @Valid final Generic generic) {
		callable = new Callable<RequirementCreator>() {
			@Override
			public RequirementCreator call() throws Exception {
				result.include(generic);
				errorHandler.redirectingTo(RequirementCreator.class)
					.generic(generic.getProjectID().toString());
				return null;
			}
		};

		try {
			save(generic, callable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Post
	public void createFeature(@NotNull @Valid final Feature feature) {
		callable = new Callable<RequirementCreator>() {	
			@Override
			public RequirementCreator call() throws Exception {
				result.include(feature);
				errorHandler.redirectingTo(RequirementCreator.class)
					.feature(feature.getProjectID().toString());
				return null;
			}
		};

		try {
			save(feature, callable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Post
	public void createUserStory(@NotNull @Valid final Storie storie) {
		callable = new Callable<RequirementCreator>() {	
			@Override
			public RequirementCreator call() throws Exception {
				result.include(storie);
				errorHandler.redirectingTo(RequirementCreator.class)
					.feature(storie.getProjectID().toString());
				return null;
			}
		};

		try {
			save(storie, callable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Post
	public void createEpic(@NotNull @Valid final Epic epic) {
		callable = new Callable<RequirementCreator>() {	
			@Override
			public RequirementCreator call() throws Exception {
				result.include(epic);
				errorHandler.redirectingTo(RequirementCreator.class)
					.feature(epic.getProjectID().toString());
				return null;
			}
		};
		
		try {
			save(epic, callable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Use case creation is more specific so this method implementation is more
	 * robust than the others
	 */
	@Post
	public void createUseCase(@NotNull @Valid final UseCase useCase) {
		callable = new Callable<RequirementCreator>() {	
			@Override
			public RequirementCreator call() throws Exception {
				result.include(useCase);
				errorHandler.redirectingTo(RequirementCreator.class)
					.useCase(useCase.getProjectID().toString());
				return null;
			}
		};

		if (useCase.getFakeActors() != null) {
			useCase.formatToRealActors();
		} else {
			// This will be thrown the save method
			errorHandler.validates(ContextPlace.REQUIREMENT_CREATE);
			errorHandler.add("actor_is_required");
		}

		try {
			save(useCase, callable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This projectID is a reference of the project which the requirement will
	 * be created. All part of URL is created through a script into home.jsp,
	 * projectID number will be included into view page to fill a input hidden.
	 * 
	 * URL: /requirementType/idNumber View:
	 * /WEB-INF/jsp/requirementCreator/requirementType
	 * 
	 * @param projectID
	 *            Truly is an Long number, converted into script to be changed
	 *            foward to Long again.
	 */
	@Get("generic/{projectID}")
	public void generic(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}

	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("storie/{projectID}")
	public void storie(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}

	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("feature/{projectID}")
	public void feature(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}

	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("epic/{projectID}")
	public void epic(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}

	/**
	 * See {@link RequirementCreator#generic(String)}
	 */
	@Get("useCase/{projectID}")
	public void useCase(String projectID) {
		result.include(PROJECT_ID_INPUT_VALUE, projectID);
	}

	/**
	 * Server-side validates of basic information, it will be execute by method
	 * save, right bellow
	 * 
	 * @param requirement
	 *            that will be verifies
	 */
	private void validates(final Requirement requirement) {
		errorHandler.validates(ContextPlace.REQUIREMENT_CREATE);

		errorHandler.when(requirement.getTitle() == null)
			.show("requirement.title.empty");

		errorHandler.when(!service.findBy("title", requirement.getTitle()).isEmpty())
			.show("requirement.title.already_used");

		errorHandler.when(requirement.getAuthor() == null)
			.show("needs_author");

		errorHandler.when(requirement.getProject() == null)
			.show("project_not_exist");
	}

	/**
	 * Generic method to save an artifact
	 * 
	 * @param requirement
	 *            is a user output that will be verified and saved
	 * @param callable
	 * 			  if validation not approved call redirect method
	 * @throws Exception 
	 */
	private void save(final Requirement requirement, Callable<RequirementCreator> callable) {
		// insert some data like author, date and project
		formatter.format(requirement);
		// verify if above format work fine
		validates(requirement);

		// only to protect the invoke of create requirement
		if (!errorHandler.hasErrors()) {
			service.save(requirement);
		} else {
			logger.error("Some errors was found");
			try {
				callable.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		messageHandler.use(ContextPlace.PROJECT_PANEL).show("requirement_add_with_success");

		messageHandler.redirectTo(ProjectController.class).show(requirement.getProject());
	}

}