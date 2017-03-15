package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.system.setting.ApplicationSetting;
import com.modesteam.urutau.test.UrutaUnitTest;

public class AdministratorControllerTest extends UrutaUnitTest {

	@Before
	public void setUp() {
		super.setup();
	}

	@Test
	public void createFirstAdministrator() {
		Administrator administrator = new Administrator();
		administrator.setUserID(1L);

		doNothing().when(adminService).create(administrator);

		AdministratorController mockController = 
				new AdministratorController(result, adminService, settingDAO, 
						userSession, userService);

		mockController.createFirstAdministrator(administrator);
	}

	@Test
	public void changeSystemSettings() {
		ApplicationSetting setting = mock(ApplicationSetting.class);

		doNothing().when(settingDAO).update(setting);

		AdministratorController mockController = 
				new AdministratorController(result, adminService, settingDAO, 
						userSession, userService);

		mockController.changeSystemSettings(setting);
	}
}
