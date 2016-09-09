package com.modesteam.urutau.service.persistence;

/**
 * All services of persistence should implement this contract
 * 
 * @param <Entity>
 *            related entity to service
 */
public interface Persistence<Entity> {

	/**
	 * Persist an entity instance
	 */
	public void save(Entity entity);

	/**
	 * Gets your instance of database to synchronize with it
	 * 
	 * @param entity
	 *            edited, unmanaged bean
	 */
	public void reload(Entity entity);

	/**
	 * Merge alterations for a database instance
	 * 
	 * @param entity
	 *            should be a managed bean in JPA
	 */
	public void update(Entity entity);

	/**
	 * Delete database instance
	 * 
	 * @param entity
	 *            Managed bean to be deleted
	 */
	public void delete(Entity entity);
}
