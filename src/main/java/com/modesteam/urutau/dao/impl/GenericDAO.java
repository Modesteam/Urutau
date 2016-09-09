package com.modesteam.urutau.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.exception.SystemBreakException;

/**
 * Defines the methods common to DAO classes
 */
public abstract class GenericDAO<Entity> {
	private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);

	// Used in SQL to be replaced by real parameter of search
	private static final String PARAMETER_NAME = "value";

	// Used to get class type of Entity
	private final Class<?> entityClass;

	private EntityManager entityManager;

	@Inject
	private DaoHelper daoHelper;

	/**
	 * Set with reflection, which Object type is Entity
	 */
	public GenericDAO() {
		this.entityClass = (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Runs a select SQL
	 * 
	 * @return {@link List} of entities
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> findUsing(final String sql) {
		logger.trace("Finding by: " + sql);

		List<Entity> results = null;

		try {
			Query query = entityManager.createQuery(sql);

			results = query.getResultList();
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new SystemBreakException("SQL was not mounted rightly..",
					illegalArgumentException);
		}

		return results;
	}
	
	@SuppressWarnings("unchecked")
	public Entity find(Long id){
		return (Entity) entityManager.find(entityClass, id);
	}

	/**
	 * Gets a Entity by a field with certain value
	 * 
	 * @param field
	 *            Column name into database
	 * @param value
	 *            Simple data
	 * @return Object correspondent to Entity defined by DAO
	 */
	@SuppressWarnings("unchecked")
	public List<Entity> get(String field, Object value) {
		List<Entity> results = null;

		if (value != null && daoHelper.isValidParameter(value)) {
			final String sql = daoHelper.getSelectQuery(entityClass, field);
			Query query = entityManager.createQuery(sql);
			query.setParameter(PARAMETER_NAME, value);

			results = query.getResultList();
		} else {
			throw new IllegalArgumentException(
					"An invalid parameter has been passed to get method in GenericDAO");
		}

		logger.debug(field + " in table " + entityClass.getName() + " has returned? - "
				+ (results.size() + " results"));

		return results;
	}

	/**
	 * Creates a new instance of User into database
	 * 
	 * @return true if operation do not throw any exception
	 */
	public void create(final Entity entity) {
		try {
			entityManager.persist(entity);
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new IllegalArgumentException("An invalid param has been passed to create method");
		} catch (EntityExistsException entityExistsException) {
			throw new SystemBreakException("Entity already exist", entityExistsException);
		}
	}

	/**
	 * Removes everything
	 * 
	 * @param Entity
	 *            to remove
	 * @return false if any error occurs
	 */
	public void destroy(final Entity entity) {
		try {
			entityManager.remove(entity);
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new IllegalArgumentException(
					"An invalid param has been passed to destroy method");
		}
	}

	/**
	 * Update an entity
	 * 
	 * @param entity
	 *            to be merge with database
	 * @return false if any error occurs
	 */
	public Entity update(final Entity entity) {
		Entity entityUpdated = null;

		try {
			entityUpdated = entityManager.merge(entity);
		} catch (IllegalArgumentException exception) {
			throw new IllegalArgumentException("An invalid param has been passed to create method");
		}

		return entityUpdated;
	}

	/**
	 * Reloads object from database, for work of this method it is needed that
	 * Entity have a filled primary key
	 */
	public void refresh(final Entity entity) {
		getSession().refresh(entity);
	}

	/**
	 * @param entityManager
	 */
	public void setEntityManager(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}
}
