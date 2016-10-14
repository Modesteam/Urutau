package com.modesteam.urutau.dao;

import com.modesteam.urutau.model.system.setting.ApplicationSetting;

/**
 * Data Access Object for the Configuration
 */
public interface ApplicationSettingDAO {
		
	/**
	 * Gets a object instance that have a field with certain value
	 */
	ApplicationSetting get();
	
	/**
	 * Update an setting
	 */
	void update(ApplicationSetting setting);
	
}
