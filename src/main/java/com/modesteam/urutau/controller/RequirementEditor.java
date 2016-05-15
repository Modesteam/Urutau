package com.modesteam.urutau.controller;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.ArtifactType;
import com.modesteam.urutau.service.RequirementService;

@Controller
public class RequirementEditor {

    private static final Logger logger = LoggerFactory.getLogger(RequirementCreator.class);

    // Validation String
    private static final String REQUIREMENT_MODIFICATION_ERROR = null;

    // Objects to be injected
    private final Result result;
    private final Validator validator;
    private final UserSession userSession;
    private final RequirementService requirementService;

    public RequirementEditor() {
        this(null, null, null, null);
    }

    @Inject
    public RequirementEditor(Result result, Validator validator, UserSession userSession,
            RequirementService requirementService) {
        this.result = result;
        this.validator = validator;
        this.userSession = userSession;
        this.requirementService = requirementService;
    }

    /**
     * Called when anyone wants edit an requirement
     * 
     * @param requirementID
     *            identifier of requirement to edit
     */
    @Get
    @Path("/edit/{requirementID}")
    public void edit(Long requirementID) {

        logger.trace("Starting the function edit. Requirement id is " + requirementID);

        boolean requirementExistence = requirementService.exists(requirementID);

        // Verifies the acceptance of the requirement to proceed the requisition
        if (requirementExistence) {
            logger.info("The requirement exists in database");

            Requirement requirement = requirementService.find(requirementID);

            redirectToEditionPage(requirement);
        } else {
            logger.info("The requirement id informed is unknown.");
            validator.add(new SimpleMessage(REQUIREMENT_MODIFICATION_ERROR,
                    "It is not possible to " + " edit an unknown requirement."));
        }
        // TODO redirect to project page
        validator.onErrorForwardTo(ProjectController.class).index();
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

        logger.trace(artifactType);

        result.include(artifactType, requirement);

        switch (ArtifactType.equivalentTo(artifactType)) {
            case GENERIC:
                result.redirectTo(this).editGeneric();
                break;
            case EPIC:
                result.redirectTo(this).editEpic();
                break;
            case FEATURE:
                result.redirectTo(this).editFeature();
                break;
            case STORIE:
                result.redirectTo(this).editUserStory();
                break;
            case USECASE:
                result.redirectTo(this).editUseCase();
                break;
            default:
                // TODO redirect to project
                result.redirectTo(ProjectController.class).index();
                break;
        }
        validator.onErrorForwardTo(ProjectController.class).index();
    }

    /**
     * Allows the modification of an unique requirement
     * 
     * @param requirement
     */
    public void update(Requirement requirement) {
        logger.info("Starting the function modifyRequirement");

        // Setting the current date and current user
        Calendar lastModificationDate = getCurrentDate();
        requirement.setLastModificationDate(lastModificationDate);
        UrutaUser loggedUser = userSession.getUserLogged();
        requirement.setLastModificationAuthor(loggedUser);

        requirementService.update(requirement);

        // TODO redirect to project
        result.redirectTo(ProjectController.class).index();
    }

    @Get
    public void editEpic() {

    }

    @Get
    public void editGeneric() {

    }

    @Get
    public void editFeature() {

    }

    @Get
    public void editUseCase() {

    }

    @Get
    public void editUserStory() {

    }

    @Post
    public void editFeature(Feature feature) {
    }

    @Post
    public void editGeneric(Generic generic) {
    }

    @Post
    public void editUserStory(Storie storie) {
    }

    /**
     * Get an instance of current date through of {@link Calendar}
     * 
     * @return current date
     */
    private Calendar getCurrentDate() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        return calendar;
    }

}
