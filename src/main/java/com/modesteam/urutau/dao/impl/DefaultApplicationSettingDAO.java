package com.modesteam.urutau.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.ApplicationSettingDAO;
import com.modesteam.urutau.exception.DataBaseCorruptedException;
import com.modesteam.urutau.model.system.setting.ApplicationSetting;

public class DefaultApplicationSettingDAO implements ApplicationSettingDAO {

	private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationSettingDAO.class);
	
	@Inject
	private EntityManager manager;
	
	@Override
	public ApplicationSetting get() throws DataBaseCorruptedException {
		ApplicationSetting applicationSetting = manager.find(ApplicationSetting.class, 
				ApplicationSetting.APLICATION_SETTING_ID);

		if(applicationSetting == null) {
			throw new DataBaseCorruptedException("Application Settings could not be found");
		}

		return applicationSetting;
	}

	@Override
	public void update(final ApplicationSetting setting) throws DataBaseCorruptedException {
		logger.info("Change system settings");

		ApplicationSetting managedSetting = this.get();

		managedSetting.setOnlyTheAdministratorCanInvite(setting.onlyTheAdministratorCanInvite());
		managedSetting.setRequireAdministratorApproval(setting.requiresApprovalOfAdministrator());
		managedSetting.setSystemEmail(setting.getSystemEmail());
		managedSetting.setUserRegistrationIsOpen(setting.userRegistrationIsOpen());
	}
	
}
