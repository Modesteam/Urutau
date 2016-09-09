package com.modesteam.urutau.dao.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.LayerDAO;
import com.modesteam.urutau.model.system.Layer;

public class DefaultLayerDAO extends GenericDAO<Layer> implements LayerDAO {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultLayerDAO.class);
	
	@Inject
	private EntityManager manager;
	
	@Override
	public Layer find(Long id) {

		logger.info("Finds a layer with ID equals to " + id);
		
		return manager.find(Layer.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Layer> loadAllByProject(Long id) {
		String sql = "SELECT layer FROM " + Layer.class.getName() + 
				" layer WHERE layer.projectID=:projectID";
		
		logger.info(sql);
		
		List<Layer> layers = null;
		
		try {
			Query query = manager.createQuery(sql);
			query.setParameter("projectID", id);
			layers = query.getResultList();
			logger.debug("Layers " + layers.size());
		} catch (Exception exception) {
			layers = null;
		}
		
		return layers;
	}
}
