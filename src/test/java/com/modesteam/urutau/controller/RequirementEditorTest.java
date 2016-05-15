package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.service.RequirementService;

public class RequirementEditorTest {

	private final Logger logger = Logger.getLogger(ProjectController.class);

	private MockResult result;
	private UserSession userSession;
	private MockValidator validator;
	private RequirementService requirementService;
	private ArtifactBuilder requirementBuilder = new ArtifactBuilder();

	@Before
	public void setUp() {
		// Catch all!
		logger.setLevel(Level.DEBUG);

		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		requirementService = mock(RequirementService.class);

		userSession = mock(UserSession.class);

		UrutaUser userMock = mock(UrutaUser.class);

		when(userSession.getUserLogged()).thenReturn(userMock);
	}

	/**
	 * Verifies a valid epic edition
	 */
	@Test
	public void testValidEpicEdition() {

		// Creating an hypothetical epic
		Epic epic = requirementBuilder.id(10L).title("Valid Title").description("Valid Description")
				.buildEpic();

		doNothingWhenEdit(epic);

		RequirementEditor controller = new RequirementEditor(result, validator, userSession,
				requirementService);

		controller.update(epic);
	}

	/**
	 * Verifies a valid generic requirement edition.
	 */
	@Test
	public void testValidGenericEdition() {

		// Creating an hypothetical generic requirement
		Generic generic = requirementBuilder.id(10L).title("Valid Title")
				.description("Valid Description").buildGeneric();

		doNothingWhenEdit(generic);

		RequirementEditor controller = new RequirementEditor(result, validator, userSession,
				requirementService);

		controller.update(generic);

	}

	/**
	 * Verifies a valid feature edition.
	 */
	@Test
	public void testValidFeatureEdition() {

		// Creating an hypothetical feature
		Feature feature = requirementBuilder.id(15L).title("Valid Title")
				.description("Valid Description").buildFeature();

		doNothingWhenEdit(feature);

		RequirementEditor controller = new RequirementEditor(result, validator, userSession,
				requirementService);

		controller.update(feature);

	}

	/**
	 * Verifies a valid use case edition.
	 */
	@Test
	public void testValidUseCaseEdition() {

		// Creating an hypothetical use case
		UseCase useCase = requirementBuilder.id(100L).title("Valid Title")
				.description("Valid Description").buildUseCase();

		doNothingWhenEdit(useCase);

		RequirementEditor controller = new RequirementEditor(result, validator, userSession,
				requirementService);

		controller.update(useCase);

	}

	/**
	 * Verifies a valid storie edition.
	 */
	@Test
	public void testValidStorieEdition() {

		// Creating an hypothetical storie
		Storie storie = requirementBuilder.id(50L).title("Valid Title")
				.description("Valid Description").buildStorie();

		doNothingWhenEdit(storie);

		RequirementEditor controller = new RequirementEditor(result, validator, userSession,
				requirementService);

		controller.update(storie);

	}

	/**
	 * Mocks update method
	 * 
	 * @param requirement
	 */
	private void doNothingWhenEdit(Requirement requirement) {
		doNothing().when(requirementService).update(requirement);
	}
}
