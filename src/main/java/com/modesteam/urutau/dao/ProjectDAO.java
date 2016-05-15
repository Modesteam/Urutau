package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.Project;

public interface ProjectDAO {
	void create(Project project);

	/**
	 * Gets a object instance that have a field with certain value
	 */
	List<Project> get(String field, Object value);

	/**
	 * Finds by id
	 */
	Project find(Long id);

	List<Project> findUsing(String sql);

	Project update(Project Project);

	void destroy(Project Project);

	/**
	 * Load all projects
	 */
	List<Project> loadAll();

	void refresh(Project project);
}
