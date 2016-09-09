package com.modesteam.urutau.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ResourceBundle;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.I18nMessageCreator;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.ValidationException;

public class UserControllerTest {

	private static final String LOGIN_ATTRIBUTE = "login";

	private static final String EMAIL_ATTRIBUTE = "email";

	private MockResult result;
	private UserService userService;
	private UserSession userSession;
	private MockValidator validator;
	private I18nMessageCreator i18nCreator;
	private ErrorMessageHandler errorMessageHandler;
	private MessageHandler messageHandler;

	@Before
	public void setup() {
		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// Components of system
		userService = mock(UserService.class);
		userSession = mock(UserSession.class);
		
		i18nCreator = mock(I18nMessageCreator.class);
		
		messageHandler = new MessageHandler(result, i18nCreator);
		errorMessageHandler = new ErrorMessageHandler(validator, i18nCreator);

		Logger.getLogger(UserController.class).setLevel(Level.DEBUG);
	}

	@Test
	public void registerValid() {
		UserBuilder builder = new UserBuilder();

		UrutaUser user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("123456")
				.name("Tester")
				.lastName("Sobrenome")
				.build();
		
		mockI18nMessages(anyString(), ContextPlace.REGISTER_VALIDATOR);

		when(userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin())).thenReturn(true);
		when(userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail())).thenReturn(true);

		doNothing().when(userService).create(user);
		
		UserController controller = new UserController(result, userService,
				userSession, validator, messageHandler, errorMessageHandler);

		controller.register(user);

		assertFalse(validator.hasErrors());
	}

	@Test(expected = ValidationException.class)
	public void registerInvalidCaseOne() {
		UserBuilder builder = new UserBuilder();

		UrutaUser user = builder.build();
		
		mockI18nMessages(anyString(), ContextPlace.REGISTER_VALIDATOR);

		UserController controller = new UserController(result, userService, userSession,
				validator, messageHandler, errorMessageHandler);

		controller.register(user);
	}

	@Test(expected = ValidationException.class)
	public void registerInvalidCaseTwo() {
		UserBuilder builder = new UserBuilder();

		UrutaUser user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("diff")
				.name("Tester")
				.lastName("Sobrenome")
				.build();

		mockI18nMessages(anyString(), ContextPlace.REGISTER_VALIDATOR);

		when(userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin())).thenReturn(true);
		when(userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail())).thenReturn(true);
		
		doNothing().when(userService).create(user);

		UserController controller = new UserController(result, userService, userSession, 
				validator, messageHandler, errorMessageHandler);

		controller.register(user);
	}

	@Test
	public void tryLoginWithSucces() throws Exception {
		UserBuilder builder = new UserBuilder();

		UrutaUser user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("123456")
				.name("Tester")
				.lastName("Sobrenome")
				.build();

		mockI18nMessages(anyString(), ContextPlace.REGISTER_VALIDATOR);

		when(userService.authenticate(user.getLogin(), user.getPassword()))
			.thenReturn(user);

		UserController controller = new UserController(result, userService, userSession, 
				validator, messageHandler, errorMessageHandler);

		controller.authenticate("fulano", "123456");

		assertFalse(validator.hasErrors());
	}

	/**
	 * Throws an validation exception, not coverage by eclemma
	 * 
	 * @throws Exception
	 */
	@Test(expected = ValidationException.class)
	public void tryLoginFail() throws Exception {
		UserBuilder builder = new UserBuilder();

		UrutaUser user = builder
				.email("example@email.com")
				.login("fulano")
				.password("123456")
				.passwordVerify("diff")
				.name("Tester")
				.lastName("Sobrenome")
				.build();
		
		mockI18nMessages(anyString(), ContextPlace.LOGIN);
		
		when(userService.authenticate(user.getLogin(), user.getPassword()))
			.thenReturn(null);
		
		UserController controller = new UserController(result, userService, userSession, 
				validator, messageHandler, errorMessageHandler);

		controller.authenticate(user.getLogin(), user.getPassword());
	}
	
	private void mockI18nMessages(String message, ContextPlace place) {
		when(i18nCreator.translate(message)).thenReturn(i18nCreator);

		I18nMessage i18n = new I18nMessage(place.name(), message);
		i18n.setBundle(ResourceBundle.getBundle("messages"));

		when(i18nCreator.to(place)).thenReturn(i18n);
	}

}