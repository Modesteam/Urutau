package com.modesteam.urutau.dao;

import java.util.List;

import com.modesteam.urutau.model.system.Layer;

/**
 * Data Access Object for Layer
 *
 */
public interface LayerDAO {
	
	void create(Layer layer);
	
	/**
	 * Gets layers that have a field with certain value
	 */
	List<Layer> get(String field, Object value) throws Exception;
	
	/**
	 * Finds by id
	 */
	Layer find(Long id) throws IllegalArgumentException;
	
	Layer update(Layer Layer);
	
	void destroy(Layer Layer);

	/**
	 * Load all Layers of project
	 */
	List<Layer> loadAllByProject(Long id);
}
