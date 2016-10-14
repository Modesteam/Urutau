package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.dao.ApplicationSettingDAO;
import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.system.setting.ApplicationSetting;
import com.modesteam.urutau.service.AdministratorService;

import br.com.caelum.vraptor.util.test.MockResult;

public class AdministratorControllerTest {
	private MockResult result;
	private AdministratorService adminService;
	private ApplicationSettingDAO settingDAO;

	@Before
	public void setUp() {
		result = new MockResult();
		adminService = mock(AdministratorService.class);
		settingDAO = mock(ApplicationSettingDAO.class);
	}

	@Test
	public void createFirstAdministrator() {
		Administrator administrator = new Administrator();
		administrator.setUserID(1L);

		doNothing().when(adminService).create(administrator);

		AdministratorController mockController = 
				new AdministratorController(result, adminService, settingDAO);

		mockController.createFirstAdministrator(administrator);
	}

	@Test
	public void changeSystemSettings() {
		ApplicationSetting setting = mock(ApplicationSetting.class);

		doNothing().when(settingDAO).update(setting);

		AdministratorController mockController = 
				new AdministratorController(result, adminService, settingDAO);

		mockController.changeSystemSettings(setting);
	}
}
