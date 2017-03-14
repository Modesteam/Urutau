package com.modesteam.urutau.controller;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.ValidationException;

public class UserControllerTest extends UrutaUnitTest {

	private static final String LOGIN_ATTRIBUTE = "login";

	private static final String EMAIL_ATTRIBUTE = "email";

	@Before
	public void setup() {
		super.setup();
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
		
		when(userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin())).thenReturn(true);
		when(userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail())).thenReturn(true);

		doNothing().when(userService).save(user);
		
		UserController controller = new UserController(result, userService, 
				userSession, flash, flashError);

		controller.register(user);

		assertFalse(validator.hasErrors());
	}

	@Test(expected=ValidationException.class)
	public void registerInvalidCaseOne() {
		UserBuilder builder = new UserBuilder();

		UrutaUser user = builder.build();

		UserController controller = new UserController(result, userService, 
				userSession, flash, flashError);

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

		when(userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin())).thenReturn(true);
		when(userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail())).thenReturn(true);

		doNothing().when(userService).save(user);

		UserController controller = new UserController(result, userService, 
				userSession, flash, flashError);

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

		when(userService.authenticate(user.getLogin(), user.getPassword().getUserPasswordPassed()))
			.thenReturn(user);

		UserController controller = new UserController(result, userService, 
				userSession, flash, flashError);

		controller.authenticate("fulano", "123456");

		assertFalse(validator.hasErrors());
	}

	/**
	 * Throws an validation exception, not coverage by eclemma
	 * 
	 * @throws Exception
	 */
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
		
		when(userService.authenticate(user.getLogin(), user.getPassword().getUserPasswordPassed()))
			.thenReturn(null);
		
		UserController controller = new UserController(result, userService, 
				userSession, flash, flashError);

		controller.authenticate(user.getLogin(), user.getPassword().getUserPasswordPassed());
	}

	@Test
	public void testEdit() {
		UserController controller = new UserController(result, userService, 
				userSession, flash, flashError);
		controller.edit();
	}

	@Test
	public void testUpdateBasic() {
		UrutaUser user = new UrutaUser();

		when(userService.update(user)).thenReturn(user);

		UserController controller = new UserController(result, userService,
				userSession, flash, flashError);
		controller.updateBasic(user);
	}

	@Test
	public void testUpdatePassword() {
		UrutaUser user = new UrutaUser();
		user.setUserID(1L);

		user.setPassword(generatePassword("teste"));

		when(userSession.getUserLogged()).thenReturn(user);

		when(userService.find(user.getUserID())).thenReturn(user);

		UserController controller = new UserController(result, userService,
				userSession, flash, flashError);

		controller.updatePassword("teste", "new_password", "new_password");
	}

	@Test
	public void testDelete() {
		UrutaUser user = new UrutaUser();
		user.setUserID(1L);

		user.setPassword(generatePassword("teste"));

		when(userSession.getUserLogged()).thenReturn(user);
		when(userService.find(user.getUserID())).thenReturn(user);

		UserController controller = new UserController(result, userService,
				userSession, flash, flashError);

		controller.delete("teste");
	}
}