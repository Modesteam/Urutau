package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.exception.UserActionException;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
public class KanbanController {
	private static final Logger logger = LoggerFactory.getLogger(KanbanController.class);

	private final KanbanService kanbanService;
	private final ProjectService projectService;
	private final RequirementService requirementService;
	private final Result result;
	private final MessageHandler messageHandler;
	private final ErrorMessageHandler errorHandler;
	
	/**
	 * @deprecated CDI
	 */
	public KanbanController() {
		this(null, null, null, null, null, null);
	}

	@Inject
	public KanbanController(KanbanService kanbanService, ProjectService projectService, 
			RequirementService requirementService, Result result, 
			MessageHandler messageHandler, ErrorMessageHandler errorHandler) {
		this.kanbanService = kanbanService;
		this.projectService = projectService;
		this.requirementService = requirementService;
		this.result = result;
		this.messageHandler = messageHandler;
		this.errorHandler = errorHandler;
	}

	@Get
	@Path("/kanban/{project.id}")
	public void load(final Project project) {

		Project currentProject = projectService.find(project.getId());

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
			messageHandler.use(ContextPlace.ERROR)
				.sendViaJSON("invalid_request");
		}

		// If not are used, shows success message 
		if(!result.used()) {
			messageHandler.use(ContextPlace.KANBAN)
				.sendViaJSON("successfully_moved_requirement");
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
	public void createLayer(final @NotNull Long projectID, @NotNull Layer layer) {
		// TODO more specific
		result.on(UserActionException.class).redirectTo(ApplicationController.class)
				.invalidRequest();

		Project currentProject = projectService.find(projectID);

		try {
			kanbanService.create(layer);
		} catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		} catch (SystemBreakException systemBreakException) {
			// TODO create a specific redirect
			result.redirectTo(ApplicationController.class).dificultError();
		}

		currentProject.add(layer);

		projectService.update(currentProject);

		result.redirectTo(this).load(currentProject);
	}

	public void deleteLayer() {
	    // TODO logo
	}

	public void updateLayer() {
	    // TODO logo
	}

	@View
	public void editLayer() {
	}

}
