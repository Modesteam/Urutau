package com.modesteam.urutau.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.ValidationException;

import static com.modesteam.urutau.model.system.ContextPlace.REQUIREMENT_CREATE;
import static com.modesteam.urutau.model.system.ContextPlace.PROJECT_PANEL;

public class RequirementCreatorTest extends UrutaUnitTest {

	private Project ownedProject;
	private RequirementFormatter formatter;

	@Before
	public void setup() {
		super.setup();
		
		ownedProject = createMockProject();

		formatter = new RequirementFormatter(userSession, projectService, kanbanService);

		// Runs in all test
		mockI18nMessages("needs_author", REQUIREMENT_CREATE);
		mockI18nMessages("project_not_exist", REQUIREMENT_CREATE);
		mockI18nMessages("requirement.title.empty", REQUIREMENT_CREATE);
		mockI18nMessages("requirement.title.already_used", REQUIREMENT_CREATE);
		// Success message
		mockI18nMessages("requirement_add_with_success", PROJECT_PANEL);

		mockI18nMessages("actor_is_required", REQUIREMENT_CREATE);
	}

	@Test
	public void createValidRequirements() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Feature feature = builder
				.id(1L)
				.title("Test")
				.description("Unit")
				.projectID(1L)
				.buildFeature();

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(feature);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		controllerMock.createFeature(feature);
		assertTrue(result.included().containsKey(PROJECT_PANEL.toString()));
	}

	@Test
	public void createValidGeneric() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildGeneric();

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(generic);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);

		controllerMock.createGeneric(generic);

		assertTrue(result.included().containsKey(ContextPlace.PROJECT_PANEL.toString()));
	}

	@Test
	public void createValidEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildEpic();

		mockI18nMessages("requirement_add_with_success", ContextPlace.PROJECT_PANEL);
		when(i18nCreator.create(ContextPlace.PROJECT_PANEL, "requirement_add_with_success"))
		.thenReturn(mock(I18nMessage.class));

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(epic);
		when(i18nCreator.translate("requirement_add_with_success")).thenReturn(i18nCreator);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		controllerMock.createEpic(epic);

		assertTrue(messageHandler.containsMessageOf(ContextPlace.PROJECT_PANEL));
	}

	@Test
	public void createValidStorie() {
		ArtifactBuilder builderStorie = new ArtifactBuilder();

		Storie storie = builderStorie
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L)
				.buildStorie();

		mockI18nMessages("requirement_add_with_success", ContextPlace.PROJECT_PANEL);

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(storie);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		controllerMock.createUserStory(storie);

		assertTrue(result.included().containsKey(ContextPlace.PROJECT_PANEL.toString()));
	}

	@Test
	public void createValidUseCase() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase
				.id(1L)
				.title("Example")
				.description("test unit")
				.projectID(1L).buildUseCase();

		useCase.setFakeActors("Customer");

		mockI18nMessages("requirement_add_with_success", ContextPlace.PROJECT_PANEL);

		when(i18nCreator.translate("requirement_add_with_success")).thenReturn(i18nCreator);
		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(useCase);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);

		controllerMock.createUseCase(useCase);

		assertTrue(result.included().containsKey(ContextPlace.PROJECT_PANEL.toString()));
	}

	/**
	 * Verifies if a requirement with invalid actors can be saved
	 */
	@Test(expected = ValidationException.class)
	public void createInvalidUseCasePassingActor() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();

		UseCase useCase = builderUseCase
				.id(1L)
				.title("Test")
				.description("Unit")
				.projectID(1L)
				.buildUseCase();

		// Force error
		useCase.setFakeActors(null);

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(useCase);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);

		controllerMock.createUseCase(useCase);
		
		assertTrue(validator.hasErrors());
	}

	/**
	 * Verifies if a requirement with an invalid user can be created.
	 */
	@Test(expected = ValidationException.class)
	public void testWithInvalidUser() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("Unit")
				.projectID(1L)
				.buildGeneric();

		UserSession invalidSessionMock = createInvaliUserSession();
		formatter = new RequirementFormatter(invalidSessionMock, projectService, kanbanService);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		controllerMock.createGeneric(generic);
	}

	/**
	 * Verifies if a requirement with an invalid user can be created.
	 */
	@Test(expected = ValidationException.class)
	public void testWithInvalidProject() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("Unit")
				.buildGeneric();

		when(i18nCreator.translate("project_not_exist")).thenReturn(i18nCreator);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		controllerMock.createGeneric(generic);
	}

	/**
	 * Verifies if a requirement without an obligatory attribute can be created.
	 */
	@Test(expected = ValidationException.class)
	@Ignore("Bean validation")
	public void testWithoutTitle() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("Unit")
				.projectID(1L)
				.buildGeneric();

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);

		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		controllerMock.createGeneric(generic);
	}

	@Test
	public void testViews() {
		RequirementCreator controllerMock = new RequirementCreator(result, messageHandler,
				errorHandler, requirementService, formatter);
		String testValue = String.valueOf(7L);

		controllerMock.generic(testValue);
		assertTrue(result.included().containsValue(testValue));

		controllerMock.storie(testValue);
		assertTrue(result.included().containsValue(testValue));

		controllerMock.feature(testValue);
		assertTrue(result.included().containsValue(testValue));

		controllerMock.epic(testValue);
		assertTrue(result.included().containsValue(testValue));

		controllerMock.useCase(testValue);
		assertTrue(result.included().containsValue(testValue));
	}

	private Project createMockProject() {
		Project ownedProject = mock(Project.class);

		when(ownedProject.getId()).thenReturn(1L);
		when(ownedProject.getTitle()).thenReturn("Simple test");

		return ownedProject;
	}

	private UserSession createInvaliUserSession() {
		UserSession invalidUserMock = mock(UserSession.class);
		when(invalidUserMock.getUserLogged()).thenReturn(null);

		return invalidUserMock;
	}

}