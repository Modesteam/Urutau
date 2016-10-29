package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.UserService;
import com.modesteam.urutau.service.validator.RegisterValidator;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.urutau.vraptor.handler.FlashError;
import br.com.urutau.vraptor.handler.FlashMessage;

/**
 * This controller have actions directly connect to user
 */
@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static final String LOGIN_ATTRIBUTE = "login";
	private static final String EMAIL_ATTRIBUTE = "email";

	private final Result result;
	private final UserService userService;
	private final UserSession userSession;
	private final FlashMessage flash;
	private final FlashError flashError;

	/**
	 * @deprecated CDI eyes only
	 */
	public UserController() {
		this(null, null, null, null, null);
	}

	@Inject
	public UserController(Result result, UserService userService, UserSession userSession,
			FlashMessage flash, FlashError flashError) {
		this.result = result;
		this.userService = userService;
		this.userSession = userSession;
		this.flash = flash;
		this.flashError = flashError;
	}

	/**
	 * To register a new user instance
	 * 
	 * @param user
	 *            have beans validations that say to {@link Validator} throws or
	 *            not a error message
	 */
	@Post
	public void register(final @Valid UrutaUser user) {

		logger.info("Request user register");

		// To fills form again
		result.include(user);

		// It makes bean validation and manual validations
		validateBeforeCreate(user);

		userService.create(user);

		result.forwardTo(this).showSignInSucess();
	}

	/**
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/administratorSettings")
	public void administratorSettings(UrutaUser user) {
		UrutaUser logged = userSession.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userSession.login(logged);
		userService.update(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}

	/**
	 * Authenticate user, putting him on session
	 * 
	 * @param login
	 *            field of user
	 * @param password
	 *            secret word of user
	 * @throws Exception
	 */
	@Post
	public void authenticate(String login, String password) throws Exception {
		UrutaUser user = userService.authenticate(login, password);
		
		boolean userFound = user != null;
		
		// If have any error, include parameters login and password
		// to user try authenticate again
		if(!userFound) {
			flash.use("login").toShow("invalid_authenticate");

			result.include("login", login);
			result.include("password", password);
		} else {			
			// put in session
			userSession.login(user);
			
			result.use(Results.referer()).redirect();
		}
	}

	@Get("/logout")
	public void logout() {
		userSession.logout();

		flash.use("success_message").toShow("user_logout")
			.redirectingTo(IndexController.class).index();
	}

	@View
	public void showSignInSucess() {

	}
	
	/**
	 * TODO treat model validations
	 * 
	 * Case of any error redirect to index with errors. 
	 * This method considers beans validation too
	 */
	private void validateBeforeCreate(UrutaUser user) {
		flashError.validate("register_validator");
		
		RegisterValidator registerValidator = new RegisterValidator(user);

		if(registerValidator.hasNullField()) {
			flashError.add("all_fields_required");
		}
		// Above error should redirect to index
		flashError.getValidator().onErrorRedirectTo(IndexController.class).index();

		if(!registerValidator.validPasswordConfirmation()) {
			flashError.add("password_are_not_equals");
		}

		if(!userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin())) {
			flashError.add("login_already_in_use");
		}

		if(!userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail())) {
			flashError.add("email_already_in_use");
		}

		flashError.getValidator().onErrorRedirectTo(IndexController.class).index();
	}
}
