package com.modesteam.urutau.controller;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.Restrict;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import io.github.projecturutau.vraptor.handler.FlashError;
import io.github.projecturutau.vraptor.handler.FlashMessage;

@Controller
public class KanbanController {
	private static final Logger logger = LoggerFactory.getLogger(KanbanController.class);

	private final KanbanService kanbanService;
	private final ProjectService projectService;
	private final RequirementService requirementService;
	private final Result result;
	private final FlashMessage flashMessage;
	private final FlashError flashError;
	private final Event<Project> privacyEvent;
	
	/**
	 * @deprecated CDI
	 */
	public KanbanController() {
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public KanbanController(KanbanService kanbanService, ProjectService projectService, 
			RequirementService requirementService,  Result result,
			FlashMessage flashMessage,  FlashError flashError, Event<Project> privacyEvent) {
		this.kanbanService = kanbanService;
		this.projectService = projectService;
		this.requirementService = requirementService;
		this.flashMessage = flashMessage;
		this.flashError = flashError;
		this.result = result;
		this.privacyEvent = privacyEvent;
	}

	@Get
	@Path("/kanban/{project.id}")
	public void load(final Project project) {

		Project currentProject = projectService.find(project.getId());

		privacyEvent.fire(project);

		currentProject.loadRequirements();

		// Put a current project as project in result
		result.include(Project.class.getSimpleName().toLowerCase(), currentProject);
	}

	/**
	 * Move requirement to another layer
	 * 
	 * @param requirementID identifier of requirement to be moved
	 * @param layerID identifier of layer target
	 */
	@Post("/kanban/move")
	@Restrict
	public void move(final Long requirementID, final Long layerID) throws Exception {

		logger.info("Requesting the move of one requirement");

		Requirement requirementToMove = null;

		try {
			requirementToMove = requirementService.find(requirementID);

			Layer targetLayer = kanbanService.getLayerByID(layerID);

			requirementToMove.setLayer(targetLayer);

			// update requirement layer
			requirementService.update(requirementToMove);

		} catch (IllegalArgumentException exception) {
			// Sends via JSON the current exception
			flashMessage.use("error").toShow("invalid_request").sendJSON();
		}

		// If not are used, shows success message 
		if(!result.used()) {
			flashMessage.use("success")
				.toShow("successfully_moved_requirement").sendJSON();
		}
	}

	/**
	 * Creates a new layer
	 * 
	 * @param projectID
	 *            project that needs this layer
	 * @param layer
	 *            new instance into database
	 * @throws Exception
	 */
	@Post
	@Restrict
	public void createLayer(final Long projectID, final Layer layer) {
		logger.info("Adding new column in kanban that have projectID " + projectID);

		validateLayer(projectID, layer);

		logger.info("Layer name is " + layer.getName());

		Project managedProject = projectService.find(projectID);

		try {
			kanbanService.create(layer);
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		} catch (SystemBreakException systemBreakException) {
			// TODO create a specific redirect
			result.redirectTo(ApplicationController.class).dificultError();
		}

		managedProject.add(layer);

		projectService.update(managedProject);

		flashMessage.use("success").toShow("column_added");
	}

	@Restrict
	public void deleteLayer() {
	    // TODO
	}

	@Restrict
	public void updateLayer() {
	    // TODO
	}

	@View
	public void editLayer() {
	}

	private void validateLayer(Long projectID, Layer layer) {
		flashError.validate("error");

		if (projectID <= 0) {
			flashError.add("unsucessful_operation");
		} else if(layer == null || layer.getName() == null) {
			flashError.add("invalid_column");
		}

		flashError.getValidator().onErrorUse(Results.referer()).redirect();
	}
}
