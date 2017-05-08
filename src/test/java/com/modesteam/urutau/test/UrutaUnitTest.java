package com.modesteam.urutau.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.enterprise.event.Event;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.dao.ApplicationSettingDAO;
import com.modesteam.urutau.formatter.RequirementFormatter;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.Password;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import io.github.projecturutau.vraptor.handler.FlashError;
import io.github.projecturutau.vraptor.handler.FlashMessage;
import io.github.projecturutau.vraptor.test.MockFlash;
import io.github.projecturutau.vraptor.test.MockFlashError;

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
	protected AdministratorService adminService;
	protected ApplicationSettingDAO settingDAO;
	
	protected UserSession userSession;
	protected RequirementFormatter formatter;
	protected FlashMessage flash;
	protected FlashError flashError;

	// for some event needed
	@SuppressWarnings("rawtypes")
	protected Event event;

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
		adminService = mock(AdministratorService.class);
		requirementService = mock(RequirementService.class);

		settingDAO = mock(ApplicationSettingDAO.class);

		userSession = mock(UserSession.class);

		flash = new MockFlash(result);
		flashError = new MockFlashError();

		formatter = new RequirementFormatter(userSession, projectService, kanbanService);

		event = mock(Event.class);

		// Session
		UrutaUser userLogged = mock(UrutaUser.class);
		when(userSession.getUserLogged()).thenReturn(userLogged);
	}

	public Password generatePassword(String passwordPassed) {
		Password password = new Password();
		password.setUserPasswordPassed(passwordPassed);
		password.generateHash();
		
		return password;
	}
}
