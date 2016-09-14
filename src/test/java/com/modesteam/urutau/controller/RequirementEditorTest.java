package com.modesteam.urutau.controller;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.system.ArtifactType;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.ValidationException;

public class RequirementEditorTest extends UrutaUnitTest {

	private ArtifactBuilder requirementBuilder = new ArtifactBuilder();

	@Before
	public void setUp() {
		super.setup();

		mockI18nMessages("requirement_updated", ContextPlace.SUCCESS_MESSAGE);
	}

	/**
	 * Verifies a valid epic edition
	 */
	@Test
	public void testValidEpicUpdate() {

		// Creating an hypothetical epic
		Epic epic = requirementBuilder
				.id(10L)
				.projectID(1L)
				.title("Valid Title")
				.description("Valid Description")
				.buildEpic();

		logger.debug("Project id" + epic.getProject().getId());

		when(requirementService.update(epic)).thenReturn(epic);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);

		controller.epic(epic);
	}

	/**
	 * Verifies a valid generic requirement edition.
	 */
	@Test
	public void testValidGenericUpdate() {

		// Creating an hypothetical generic requirement
		Generic generic = requirementBuilder
				.id(10L)
				.projectID(1L)
				.title("Valid Title")
				.description("Valid Description")
				.buildGeneric();

		when(requirementService.update(generic)).thenReturn(generic);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);

		controller.generic(generic);

	}

	/**
	 * Verifies a valid feature edition.
	 */
	@Test
	public void testValidFeatureUpdate() {

		// Creating an hypothetical feature
		Feature feature = requirementBuilder
				.id(15L)
				.projectID(1L)
				.title("Valid Title")
				.description("Valid Description")
				.buildFeature();

		when(requirementService.update(feature)).thenReturn(feature);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);

		controller.feature(feature);

	}

	/**
	 * Verifies a valid use case edition.
	 */
	@Test
	public void testValidUseCaseUpdate() {

		// Creating an hypothetical use case
		UseCase useCase = requirementBuilder
				.id(100L)
				.projectID(1L)
				.title("Valid Title")
				.description("Valid Description")
				.buildUseCase();

		when(requirementService.update(useCase)).thenReturn(useCase);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);

		controller.useCase(useCase);

	}

	/**
	 * Verifies a valid storie edition.
	 */
	@Test
	public void testValidStorieUpdate() {

		// Creating an hypothetical storie
		Storie storie = requirementBuilder
				.id(50L)
				.projectID(1L)
				.title("Valid Title")
				.description("Valid Description")
				.buildStorie();

		when(requirementService.update(storie)).thenReturn(storie);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);

		controller.storie(storie);

	}

	@Test(expected=ValidationException.class)
	public void testEditInvalidRequirement() {
		Long requirementID = 1L;
		when(requirementService.exists(requirementID)).thenReturn(false);

		mockI18nMessages("requirement_inexistent", ContextPlace.PROJECT_PANEL);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);
		controller.edit(1L, requirementID);
		
		Assert.assertFalse(validator.getErrors().isEmpty());
	}

	@Test
	public void testEditInValidProjectOfRequirement() {
		Long requirementID = 1L;
		Long projectID = 1L;
		Long invalidProjectID = 0L;

		when(requirementService.exists(requirementID)).thenReturn(true);
		
		Requirement mockRequirement = mock(Requirement.class);
		Project projectMock = mock(Project.class);

		when(projectMock.getId()).thenReturn(invalidProjectID);
		when(mockRequirement.getProject()).thenReturn(projectMock);
		when(requirementService.find(requirementID)).thenReturn(mockRequirement);

		RequirementEditor controller = new RequirementEditor(result, requirementService,
				messageHandler, errorHandler);
		controller.edit(projectID, requirementID);
	}

	@Test
	public void testEditValidRequirement() {
		Long requirementID = 1L;
		Long projectID = 1L;

		when(requirementService.exists(requirementID)).thenReturn(true);
		
		Generic mockRequirement = mock(Generic.class);
		Project projectMock = mock(Project.class);

		when(projectMock.getId()).thenReturn(projectID);
		when(mockRequirement.getProject()).thenReturn(projectMock);
		when(mockRequirement.getType()).thenReturn("generic");
		when(requirementService.find(requirementID)).thenReturn(mockRequirement);

		for(ArtifactType types : ArtifactType.values()) {
			when(mockRequirement.getType()).thenReturn(types.name().toLowerCase());

			RequirementEditor controller = new RequirementEditor(result, requirementService,
					messageHandler, errorHandler);
			controller.edit(projectID, requirementID);
		}
	}
}
