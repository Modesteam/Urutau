package com.modesteam.urutau.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.SettingDAO;
import com.modesteam.urutau.model.system.setting.Setting;

public class DefaultSettingDAO implements SettingDAO {

	private static final Logger logger = LoggerFactory.getLogger(SettingDAO.class);
	
	@Inject
	private EntityManager manager;
	
	@Override
	public Setting get(Setting setting) throws Exception {
		return manager.find(Setting.class, setting.getId());
	}

	@Override
	public void update(Setting setting) {
		logger.info("Change some setting");
		manager.merge(setting);
	}
	
}
