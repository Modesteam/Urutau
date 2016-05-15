package com.modesteam.urutau.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.model.Project;

public class DefaultProjectDAO extends GenericDAO<Project> implements ProjectDAO {
    
	private static final Logger logger = LoggerFactory.getLogger(ProjectDAO.class);
	
	private final EntityManager manager;

	/**
	 * @deprecated CDI only
	 */
	public DefaultProjectDAO() {
	    this(null);
    }

	/**
	 * To inject manager into GenericDAO is required {@link Inject} annotation
	 */
	@Inject
	public DefaultProjectDAO(EntityManager manager) {
	    this.manager = manager;
	    // TODO Rethink this with liskov principle
		super.setEntityManager(manager);
	}

    @Override
	public Project find(Long id) {
		return manager.find(Project.class, id);
	}

    @Override
    @SuppressWarnings("unchecked")
	public List<Project> loadAll() {
		String sql = "SELECT project " + getClass().getSimpleName() + " FROM project";

		logger.trace(sql);

		Query query = manager.createQuery(sql);
		List<Project> projectList = query.getResultList();

		return projectList;
	}
}
