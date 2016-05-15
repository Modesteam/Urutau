package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.I18nMessageCreator;
import com.modesteam.urutau.service.RequirementService;
import com.modesteam.urutau.service.persistence.FinderAdapter;
import com.modesteam.urutau.service.persistence.Order;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.I18nMessage;

public class RequirementControllerTest {

	private static final Long FAKE_REQUIREMENT_ID = 1L;

	private final Logger logger = Logger.getLogger(RequirementController.class);

	private MockResult result;
	private UserSession userSession;
	private MockValidator validator;
	private I18nMessageCreator i18nCreator;
	private MessageHandler messageHandler;
	private ErrorMessageHandler errorHandler;
	private RequirementService requirementService;

	@Before
	public void setup() {
		// Catch all!
		logger.setLevel(Level.DEBUG);

		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		requirementService = mock(RequirementService.class);
		i18nCreator = mock(I18nMessageCreator.class);

		userSession = mock(UserSession.class);
		UrutaUser userMock = mock(UrutaUser.class);
		when(userSession.getUserLogged()).thenReturn(userMock);

		errorHandler = new ErrorMessageHandler(validator, i18nCreator);
		messageHandler = new MessageHandler(result, i18nCreator);
	}

	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.buildEpic();

		doNothing().when(requirementService).delete(epic);

		RequirementController controllerMock = new RequirementController(result,
				messageHandler, errorHandler, requirementService);

		controllerMock.delete(FAKE_REQUIREMENT_ID);
	}

	@Test
	public void failedToDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(FAKE_REQUIREMENT_ID)
				.title("Example")
				.description("test unit")
				.buildEpic();

		doNothing().when(requirementService).delete(epic);

		RequirementController controllerMock = new RequirementController(result,
				messageHandler, errorHandler, requirementService);

		controllerMock.delete(FAKE_REQUIREMENT_ID);
	}

	@Test
	public void testValidShow() throws UnsupportedEncodingException {
		ArtifactBuilder builder = new ArtifactBuilder();
		Generic genericRequirement = builder.id(FAKE_REQUIREMENT_ID).title("Example")
				.description("test unit").buildGeneric();

		when(requirementService.getBy(genericRequirement.getId(), genericRequirement.getTitle()))
				.thenReturn(genericRequirement);

		mockI18nMessages("requirement_no_exist", ContextPlace.PROJECT_PANEL);

		RequirementController controllerMock = new RequirementController(result,
				messageHandler, errorHandler, requirementService);

		controllerMock.show(1L, genericRequirement.getTitle());
	}

	@Test
	public void testPaginate() {
		mockFinder();
		RequirementController controllerMock = new RequirementController(result,
				messageHandler, errorHandler, requirementService);
		controllerMock.paginate(1L, 7L);
	}

	@Test
	public void testViewCalls(){
		RequirementController controllerMock = new RequirementController(result,
				messageHandler, errorHandler, requirementService);
		controllerMock.detailRequirement();
		controllerMock.showExclusionResult();
	}

	private void mockI18nMessages(String message, ContextPlace place) {
		when(i18nCreator.translate(message)).thenReturn(i18nCreator);
		
		I18nMessage i18n = new I18nMessage(place.name(), message);
		i18n.setBundle(ResourceBundle.getBundle("messages"));

		when(i18nCreator.to(place)).thenReturn(i18n);
	}
	
	private void mockFinder() {
		Order mockOrder = mock(Order.class);
		when(requirementService.desc("dateOfCreation")).thenReturn(mockOrder);
		when(mockOrder.between(7L, 12L)).thenReturn(mockOrder);
		FinderAdapter finderMock = mock(FinderAdapter.class);
		when(mockOrder.find()).thenReturn(finderMock);
		List requirements = mock(List.class);
		when(finderMock.where("project_id=" + 1L)).thenReturn(requirements);
	}
}