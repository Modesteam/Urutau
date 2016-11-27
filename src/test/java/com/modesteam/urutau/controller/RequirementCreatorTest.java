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
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.ValidationException;

public class RequirementCreatorTest extends UrutaUnitTest {

	private Project ownedProject;
	private RequirementFormatter formatter;

	@Before
	public void setup() {
		super.setup();
		
		ownedProject = createMockProject();

		formatter = new RequirementFormatter(userSession, projectService, kanbanService);
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

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
		controllerMock.createFeature(feature);
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

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);

		controllerMock.createGeneric(generic);
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

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
		controllerMock.createEpic(epic);
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

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(storie);

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
		controllerMock.createUserStory(storie);
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

		when(projectService.find(ownedProject.getId())).thenReturn(ownedProject);
		doNothing().when(requirementService).save(useCase);

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);

		controllerMock.createUseCase(useCase);
	}

	/**
	 * Verifies if a requirement with invalid actors can be saved
	 */
	@Test(expected = ValidationException.class)
	@Ignore
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

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);

		controllerMock.createUseCase(useCase);
	}

	/**
	 * Verifies if a requirement with an invalid user can be created.
	 */
	@Test(expected = ValidationException.class)
	@Ignore
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

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
		controllerMock.createGeneric(generic);
	}

	/**
	 * Verifies if a requirement with an invalid user can be created.
	 */
	@Test(expected = ValidationException.class)
	@Ignore
	public void testWithInvalidProject() {
		ArtifactBuilder builder = new ArtifactBuilder();

		Generic generic = builder
				.id(1L)
				.title("Example")
				.description("Unit")
				.buildGeneric();

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
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

		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
		controllerMock.createGeneric(generic);
	}

	@Test
	public void testViews() {
		RequirementCreator controllerMock = new RequirementCreator(result, flash, flashError,
				requirementService, formatter);
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