package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.Restrict;
import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.dao.ApplicationSettingDAO;
import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.setting.ApplicationSetting;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.UserService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

/**
 * Executes main logics of administrator
 */
@Controller
public class AdministratorController {

	private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

	private final Result result;
	private final UserSession userSession;
	private final UserService userService;
	private final ApplicationSettingDAO settingDAO;
	private final AdministratorService administratorService;

	/**
	 * @deprecated CDI only
	 */
	public AdministratorController() {
		this(null, null, null, null, null);
	}

	@Inject
	public AdministratorController(Result result, 
			AdministratorService administratorService, ApplicationSettingDAO settingDAO,
			UserSession userSession, UserService userService) {
		this.result = result;
		this.administratorService = administratorService;
		this.settingDAO = settingDAO;
		this.userSession = userSession;
		this.userService = userService;
	}

	/**
	 * TODO specific edit page to admin
	 * 
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/edit")
	@Restrict
	public void edit(UrutaUser user) {
		UrutaUser logged = userSession.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userSession.login(logged);
		userService.update(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}

	/**
	 * Set the new first administrator login and password
	 */
	@Post
	@Path("/administratorSettings")
	@Restrict
	public void administratorSettings(UrutaUser user) {
		UrutaUser logged = userSession.getUserLogged();
		logged.setLogin(user.getLogin());
		logged.setPassword(user.getPassword());
		userSession.login(logged);
		userService.update(logged);
		result.redirectTo(AdministratorController.class).welcomeAdministrator();
	}

	@Post("/createFirstAdministrator")
	public void createFirstAdministrator(Administrator administrator) {
		logger.info("Creating first administrator");

		logger.debug("New attributes are");
		logger.debug(administrator.getEmail());
		logger.debug(administrator.getLogin());
		logger.debug(administrator.getLastName());

		administratorService.create(administrator);

		result.redirectTo(this).changeSystemSettings();
	}

	@Post("/changeSystemSettings")
	public void changeSystemSettings(ApplicationSetting setting) {
		logger.info("Setting default configuration of system");

		settingDAO.update(setting);

		result.redirectTo(IndexController.class).index();
	}

	@View
	public void createFirstAdministrator() {}

	@View
	public void changeSystemSettings() {}

	@View
	public void welcomeAdministrator() {}
}
