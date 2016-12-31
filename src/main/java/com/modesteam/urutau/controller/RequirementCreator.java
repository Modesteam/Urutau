package com.modesteam.urutau.controller;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.Restrict;
import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.urutau.vraptor.handler.FlashError;
import br.com.urutau.vraptor.handler.FlashMessage;

/**
 * This is a implementation of {@link EntityCreator}, part of pattern abstract
 * factory He was born to reduce the coupling and increase your cohesion of
 * {@link RequirementController},
 */
@Controller
@Path("/requirement")
@Restrict
public class RequirementCreator {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	private static final String PROJECT_ID_INPUT_VALUE = "projectID";

	// Objects to be injected
	private final Result result;
	private final FlashMessage flash;
	private final FlashError flashError;
	private final RequirementService service;
	private final RequirementFormatter formatter;

	private Callable<Void> callable;

	/**
	 * CDI only eye
	 */
	public RequirementCreator() {
		this(null, null, null, null, null);
	}

	@Inject
	public RequirementCreator(Result result, FlashMessage flash, FlashError flashError,
			RequirementService service, RequirementFormatter formatter) {
		this.result = result;
		this.flash = flash;
		this.flashError = flashError;
		this.service = service;
		this.formatter = formatter;
	}

	@Post
	public void createGeneric(@NotNull @Valid final Generic generic) {
		callable = new Callable<Void>() {
			/**
			 * Only to redirect to current page
			 */
			@Override
			public Void call() throws Exception {
				result.include(generic);
				flashError.getValidator().onErrorRedirectTo(RequirementCreator.class)
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
		callable = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				result.include(feature);
				flashError.getValidator().onErrorRedirectTo(RequirementCreator.class)
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
		callable = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				result.include(storie);
				flashError.getValidator().onErrorRedirectTo(RequirementCreator.class)
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
		callable = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				result.include(epic);
				flashError.getValidator().onErrorRedirectTo(RequirementCreator.class)
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
		callable = new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				result.include(useCase);
				flashError.getValidator().onErrorRedirectTo(RequirementCreator.class)
						.useCase(useCase.getProjectID().toString());
				return null;
			}
		};

		if (useCase.getFakeActors() != null) {
			useCase.formatToRealActors();
		} else {
			// This will be thrown the save method
			flash.use("error").toShow("actor_is_required");
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
	 * @param requirement that will be verifies
	 */
	private void validates(final Requirement requirement) {
		flashError.validate("requirement_create");

		if (requirement.getTitle() == null) {
			flashError.add("requirement.title.empty");
		}

		if (requirement.getTitle() == null) {
			flashError.add("requirement.title.empty");
		}

		if (!service.findBy("title", requirement.getTitle()).isEmpty()) {
			flashError.add("requirement.title.already_used");
		}

		if (requirement.getAuthor() == null) {
			flashError.add("needs_author");
		}

		if (requirement.getProject() == null) {
			flashError.add("project_not_exist");
		}
	}

	/**
	 * Generic method to save an artifact
	 * 
	 * @param requirement
	 *            is a user output that will be verified and saved
	 * @param callable
	 *            if validation not approved call redirect method
	 * @throws Exception
	 */
	private void save(final Requirement requirement, Callable<Void> callable) {
		// insert some data like author, date and project
		formatter.format(requirement);
		// verify if above format work fine
		validates(requirement);

		// only to protect the invoke of create requirement
		if (!flashError.getValidator().hasErrors()) {
			service.save(requirement);
		} else {
			logger.error("Some errors was found");
			try {
				callable.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		flash.use("success").toShow("requirement_add_with_success")
				.redirectTo(ProjectController.class).show(requirement.getProject());
	}

}