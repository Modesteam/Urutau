package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.system.ArtifactType;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;

@Controller
public class RequirementEditor {

	private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

	private final Result result;
	private final RequirementService requirementService;
	private final MessageHandler messageHandler;
	private final ErrorMessageHandler errorHandler;

	public RequirementEditor() {
		this(null, null, null, null);
	}

	@Inject
	public RequirementEditor(Result result, RequirementService requirementService,
			MessageHandler messageHandler, ErrorMessageHandler errorHandler) {
		this.result = result;
		this.requirementService = requirementService;
		this.messageHandler = messageHandler;
		this.errorHandler = errorHandler;
	}

	/**
	 * Called to edit a requirement
	 * 
	 * @param requirementID
	 *            identifier of requirement to edit
	 */
	@Get
	@Path("/{projectID}/edit/{requirementID}")
	public void edit(Long projectID, Long requirementID) {

		errorHandler.validates(ContextPlace.PROJECT_PANEL);

		logger.trace("Starting the function edit. Requirement id is " + requirementID);

		boolean requirementExistence = requirementService.exists(requirementID);

		// Verifies the acceptance of the requirement to proceed the requisition
		if (requirementExistence) {
			logger.info("The requirement exists in database");

			Requirement requirement = requirementService.find(requirementID);

			if(requirement.getProject().getId().equals(projectID)) {
				redirectToEditionPage(requirement);
			} else {
				result.redirectTo(ApplicationController.class).invalidRequest();
			}
		} else {
			logger.info("The requirement id informed is unknown.");

			errorHandler.add("requirement_inexistent")
				.redirectingTo(ProjectController.class)
				.show(projectID);
		}
	}

	/**
	 * Provides the redirecting to the requirement type edition page.
	 * 
	 * @param requirement
	 * @param artifactType
	 */
	private void redirectToEditionPage(Requirement requirement) {
		logger.info("Starting the function redirectToEditionPage.");

		final String artifactType = requirement.getType();

		logger.debug("Artifact is " + artifactType);

		result.include(artifactType, requirement);
		
		ArtifactType type = ArtifactType.equivalentTo(artifactType);
		logger.debug("Equivalent type is " + type.name());

		switch (type) {
			case GENERIC:
				result.forwardTo(this).editGeneric();
				break;
			case EPIC:
				result.forwardTo(this).editEpic();
				break;
			case FEATURE:
				result.forwardTo(this).editFeature();
				break;
			case STORIE:
				result.forwardTo(this).editUserStory();
				break;
			case USECASE:
				result.forwardTo(this).editUseCase();
				break;
			default:
				throw new SystemBreakException(
						"RequirementEditor#redirectToEditionPage "
						+ "could not found ArtifactType.");
		}
	}

	@View
	public void editEpic() {

	}

	@View
	public void editGeneric() {

	}

	@View
	public void editFeature() {

	}

	@View
	public void editUseCase() {

	}

	@View
	public void editUserStory() {

	}

	@Put
	public void generic(Generic generic) {
		Generic requirementManaged = (Generic) requirementService.update(generic);

		successfulUpdateOf(requirementManaged);
	}

	@Put
	public void feature(Feature feature) {
		Feature requirementManaged = (Feature) requirementService.update(feature);
		requirementManaged.setContent(feature.getContent());

		successfulUpdateOf(requirementManaged);
	}

	@Put
	public void storie(Storie storie) {
		Storie requirementManaged = (Storie) requirementService.update(storie);
		requirementManaged.setHistory(storie.getHistory());

		successfulUpdateOf(requirementManaged);
	}

	@Put
	public void epic(Epic epic) {
		Epic requirementManaged = (Epic) requirementService.update(epic);
		requirementManaged.setContent(epic.getContent());

		successfulUpdateOf(requirementManaged);
	}

	@Put
	public void useCase(UseCase useCase) {
		UseCase requirementManaged = (UseCase) requirementService.update(useCase);
		// TODO specific update

		successfulUpdateOf(requirementManaged);
	}

	/**
	 * Redirects to the same page of edit with a success message
	 * 
	 * @param requirement
	 *            recently updated
	 */
	private void successfulUpdateOf(Requirement requirement) {
		messageHandler.use(ContextPlace.SUCCESS_MESSAGE)
			.show("requirement_updated")
			.redirectTo(RequirementEditor.class)
			.edit(requirement.getProject().getId(), requirement.getId());
	}
}
