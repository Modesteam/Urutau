package com.modesteam.urutau.database;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.setting.ApplicationSetting;

import br.com.caelum.vraptor.events.VRaptorInitialized;

@Dependent
public class InitialDataCreator {

	private static final Logger logger = LoggerFactory.getLogger(InitialDataCreator.class);

	@Inject
	private EntityManagerFactory factory;

	private EntityManager manager;

	private List<Layer> defaultLayers = new ArrayList<Layer>();

	/**
	 * When {@link VRaptorInitialized} populates the database with:
	 * 
	 * <ul>
	 * 	<li>Default system settings {@link SystemSetting}</li>
	 * 	<li>Default layers</li>
	 * </ul>
	 */
	public void init(@Observes VRaptorInitialized event) {

		logger.info("Preparing database to support system...");

		try {
			manager = factory.createEntityManager();
			EntityTransaction transaction = manager.getTransaction();

			transaction.begin();

			createSystemSettings();
			createDefaultLayer();

			transaction.commit();
		} catch (IllegalStateException exception) {
			logger.error("Something fails with manager");
		} catch (RollbackException rollbackException) {
			logger.error("Commit fails when BasicDataCreator tried persist basic data",
					rollbackException);
		}

		if (manager.isOpen()) {
			manager.close();
		}

		logger.info("The basic data where created");
	}

	@PreDestroy
	public void destroy(EntityManagerFactory factory) {
		if (factory.isOpen()) {
			factory.close();
		}

		this.defaultLayers.clear();
	}

	private void createSystemSettings() {
		if (!settingsExists()) {
			manager.persist(new ApplicationSetting());
		} else {
			logger.info("Settings already created...");
		}
	}

	/**
	 * See if {@link ApplicationSetting} exists searching by 
	 * {@link ApplicationSetting#APLICATION_SETTING_ID}
	 * 
	 * @return true if found some register
	 */
	private boolean settingsExists() {
		ApplicationSetting found = manager.find(ApplicationSetting.class, 
				ApplicationSetting.APLICATION_SETTING_ID);
		return  found != null;
	}

	private void createDefaultLayer() {
		// If there is no layer, then create it
		if (!existsLayer()) {
			// populate layers
			fill();

			for (Layer layer : defaultLayers) {
				manager.persist(layer);
			}
		} else {
			logger.info("The Default layers are already created...");
		}
	}

	private boolean existsLayer() {
		return manager.find(Layer.class, 1L) != null;
	}

	/**
	 * TODO refactor this
	 */
	private void fill() {
		Layer layer1 = new Layer();
		layer1.setName("Backlog");
		layer1.setDescription("Come on");
		defaultLayers.add(layer1);

		Layer layer2 = new Layer();
		layer2.setName("In Progress");
		layer2.setDescription("Hey, ok, i am doing!");
		defaultLayers.add(layer2);

		Layer layer3 = new Layer();
		layer3.setName("Testing");
		layer3.setDescription("It is right, but the computer can not understand me...");
		defaultLayers.add(layer3);

		Layer layer4 = new Layer();
		layer4.setName("Done");
		layer4.setDescription("I won, i won! Yeah...");
		defaultLayers.add(layer4);

	}
}
