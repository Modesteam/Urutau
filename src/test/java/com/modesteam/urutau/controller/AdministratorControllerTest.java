package com.modesteam.urutau.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;

import com.modesteam.urutau.service.AdministratorService;
import com.modesteam.urutau.service.setting.system.SettingManagerSystem;

import br.com.caelum.vraptor.util.test.MockResult;

public class AdministratorControllerTest {
	
	private MockResult result;
	private SettingManagerSystem settingManager;
	private AdministratorService adminService;

	@Before
	public void setUp() {
		result = new MockResult();
		
		adminService = mock(AdministratorService.class);
	}
}
