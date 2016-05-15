package com.modesteam.urutau.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.UserService;
import com.modesteam.urutau.service.validator.RegisterValidator;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

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
	private final MessageHandler messageHandler;
	private final ErrorMessageHandler errorMessageHandler;

	/**
	 * @deprecated CDI eyes only
	 */
	public UserController() {
		this(null, null, null, null, null, null);
	}

	@Inject
	public UserController(Result result, UserService userService, UserSession userSession,
			Validator validator, MessageHandler messageHandler, ErrorMessageHandler errorMessageHandler) {
		this.result = result;
		this.userService = userService;
		this.userSession = userSession;
		this.errorMessageHandler = errorMessageHandler;
		this.messageHandler = messageHandler;
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

		errorMessageHandler.validates(ContextPlace.LOGIN);

		errorMessageHandler.when(user == null)
			.show("invalid_authenticate");

		// If have any error, include parameters login and password
		// to user try authenticate again
		if(errorMessageHandler.hasErrors()) {
			result.include("login", login);
			result.include("password", password);
		}

		// On error go to index
		errorMessageHandler.redirectingTo(IndexController.class).index();

		// put in session
		userSession.login(user);

		result.use(Results.referer()).redirect();
	}

	@Get("/logout")
	public void logout() {
		userSession.logout();

		messageHandler.use(ContextPlace.SUCCESS_MESSAGE).show("user_logout");

		messageHandler.redirectTo(IndexController.class).index();
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
		
		errorMessageHandler.validates(ContextPlace.REGISTER_VALIDATOR);
		
		RegisterValidator registerValidator = new RegisterValidator(user);

		errorMessageHandler.when(registerValidator.hasNullField())
			.show("all_fields_required");

		errorMessageHandler.redirectingTo(IndexController.class).index();

		errorMessageHandler.when(!registerValidator.validPasswordConfirmation())
			.show("password_are_not_equals");

		errorMessageHandler.when(!userService.canBeUsed(LOGIN_ATTRIBUTE, user.getLogin()))
			.show("login_already_in_use");

		errorMessageHandler.when(!userService.canBeUsed(EMAIL_ATTRIBUTE, user.getEmail()))
			.show("email_already_in_use");

		errorMessageHandler.redirectingTo(IndexController.class).index();
	}
}
