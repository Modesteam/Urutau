package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.model.Administrator;
import com.modesteam.urutau.model.system.setting.SystemSetting;
import com.modesteam.urutau.model.system.setting.SystemSettingContext;
import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.setting.SystemSettingManager;

import br.com.caelum.vraptor.util.test.MockResult;

public class AdministratorControllerTest {
	private MockResult result;
	private SystemSettingManager settingManager;
	private AdministratorService adminService;

	@Before
	public void setUp() {
		result = new MockResult();

		settingManager = mock(SystemSettingManager.class);
		adminService = mock(AdministratorService.class);
	}

	@Test
	public void createFirstAdministrator() {
		Administrator administrator = new Administrator();
		administrator.setUserID(1L);

		doNothing().when(adminService).create(administrator);

		AdministratorController mockController = new AdministratorController(result, adminService,
				settingManager);

		mockController.createFirstAdministrator(administrator);
	}

	@Test
	public void changeSystemSettings() {
		String email = "admin@email.com";

		List<String> settings = new ArrayList<String>();
		settings.add(email);

		SystemSetting setting = new SystemSetting(SystemSettingContext.SYSTEM_EMAIL);
		setting.setValue(email);
		doNothing().when(settingManager).save(setting);

		AdministratorController mockController = new AdministratorController(result, adminService,
				settingManager);

		mockController.changeSystemSettings(settings);
	}
}
