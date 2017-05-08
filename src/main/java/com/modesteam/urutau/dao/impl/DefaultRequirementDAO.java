package com.modesteam.urutau.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.model.Requirement;

/**
 * Default implementation of RequirementDAO
 */
public class DefaultRequirementDAO extends GenericDAO<Requirement> implements RequirementDAO {

    private final EntityManager manager;

    /**
     * @deprecated CDI only
     */
    public DefaultRequirementDAO() {
        this(null);
    }

    /**
     * To inject manager into GenericDAO is required {@link Inject} annotation
     */
    @Inject
    public DefaultRequirementDAO(EntityManager manager) {
        this.manager = manager;
        super.setEntityManager(manager);
    }

	@Override
	public Query createQuery(String sql) {
		return manager.createQuery(sql);
	}
}
