package com.modesteam.urutau.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.urutau.vraptor.handler.FlashError;
import br.com.urutau.vraptor.handler.FlashMessage;
import br.com.urutau.vraptor.test.MockFlash;
import br.com.urutau.vraptor.test.MockFlashError;

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
	protected FlashMessage flash;
	protected FlashError flashError;

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

		flash = new MockFlash();
		flashError = new MockFlashError();

		formatter = new RequirementFormatter(userSession, projectService, kanbanService);

		// Session
		UrutaUser userLogged = mock(UrutaUser.class);
		when(userSession.getUserLogged()).thenReturn(userLogged);

	}
}
