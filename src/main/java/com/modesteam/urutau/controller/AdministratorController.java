package com.modesteam.urutau.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.annotation.View;
import com.modesteam.urutau.dao.ApplicationSettingDAO;
import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.system.setting.ApplicationSetting;
import com.modesteam.urutau.service.AdministratorService;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

/**
 * Executes main logics of administrator
 */
@Controller
public class AdministratorController {

	private static final Logger logger = LoggerFactory.getLogger(AdministratorController.class);

	private final Result result;

	private final AdministratorService administratorService;

	private final ApplicationSettingDAO settingDAO;

	/**
	 * @deprecated CDI only
	 */
	public AdministratorController() {
		this(null, null, null);
	}

	@Inject
	public AdministratorController(Result result, AdministratorService administratorService,
			ApplicationSettingDAO settingDAO) {
		this.result = result;
		this.administratorService = administratorService;
		this.settingDAO = settingDAO;
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
	public void createFirstAdministrator() {
	}

	@View
	public void changeSystemSettings() {
	}

	@View
	public void welcomeAdministrator() {
	}
}
