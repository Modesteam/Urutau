package com.modesteam.urutau.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.I18nMessageCreator;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.I18nMessage;

/**
 * Have some test methods reusable in many places
 */
public abstract class UrutaUnitTest {

	protected static final String SOME_STRING = "something";

	protected final Logger logger = Logger.getLogger(UrutaUnitTest.class);

	protected MockResult result;
	protected MockValidator validator;

	protected ProjectService projectService;
	protected UserService userService;
	protected KanbanService kanbanService;
	protected RequirementService requirementService;
	
	protected UserSession userSession;
	protected RequirementFormatter formatter;
	
	// I18n
	protected I18nMessageCreator i18nCreator;
	protected MessageHandler messageHandler;
	protected ErrorMessageHandler errorHandler;

	/**
	 * You need put {@link Before} annotation into setUp in child.
	 * This setup makes some mock objects needed in many common cases are ready to use
	 */
	public void setup() {
		// Catch all logs
		logger.setLevel(Level.DEBUG);

		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		projectService = mock(ProjectService.class);
		kanbanService = mock(KanbanService.class);
		userService = mock(UserService.class);
		requirementService = mock(RequirementService.class);

		userSession = mock(UserSession.class);

		// Messages
		i18nCreator = mock(I18nMessageCreator.class);
		errorHandler = new ErrorMessageHandler(validator, i18nCreator);
		messageHandler = new MessageHandler(result, i18nCreator);

		formatter = new RequirementFormatter(userSession, projectService, kanbanService);

		// Session
		UrutaUser userLogged = mock(UrutaUser.class);
		when(userSession.getUserLogged()).thenReturn(userLogged);

	}
	
	public void mockI18nMessages(String message, ContextPlace place) {
		when(i18nCreator.translate(message)).thenReturn(i18nCreator);

		I18nMessage i18n = new I18nMessage(place.name(), message);
		i18n.setBundle(ResourceBundle.getBundle("messages"));

		when(i18nCreator.to(place)).thenReturn(i18n);
	}
}
