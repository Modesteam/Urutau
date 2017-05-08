package com.modesteam.urutau.dao;

import java.util.List;

import javax.persistence.Query;

import com.modesteam.urutau.model.Requirement;

/**
 *
 * Data access object for Requirements
 *
 */
public interface RequirementDAO {
	void create(Requirement requirement);

	/**
	 * Gets a object instance that have a field with certain value
	 */
	List<Requirement> get(String field, Object value);

	/**
	 * Finds by id
	 */
	Requirement find(Long id);

	Requirement update(Requirement requirement);

	void destroy(Requirement requirement);

	List<Requirement> findUsing(String sql);

	Query createQuery(String sql);
}