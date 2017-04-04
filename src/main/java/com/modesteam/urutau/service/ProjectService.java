package com.modesteam.urutau.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.jboss.weld.exceptions.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.ProjectDAO;
import com.modesteam.urutau.exception.DataBaseCorruptedException;
import com.modesteam.urutau.exception.NotImplementedError;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Project.Searchable;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.persistence.Finder;
import com.modesteam.urutau.service.persistence.Persistence;

public class ProjectService implements Persistence<Project>, Finder<Project> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
	private static final String TITLE_COLUMN = "title";
	private static final int FIRST = 0;

	private final ProjectDAO projectDAO;

	/*
	 * CDI eye only
	 */
	public ProjectService() {
		this(null);
	}

	@Inject
	public ProjectService(ProjectDAO projectDAO) {
		this.projectDAO = projectDAO;
	}

	/**
	 * See if title can be used
	 * 
	 * @param projectTitle
	 * @throws DataBaseCorruptedException
	 */
	public boolean titleAvaliable(final String projectTitle) 
			throws DataBaseCorruptedException {
		boolean avaliable = false;

		try {
			if (projectDAO.get(TITLE_COLUMN, projectTitle).isEmpty()) {
				avaliable = true;
			}
		} catch (NoResultException noResultException) {
			avaliable = true;
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorruptedException(this.getClass().getSimpleName()
					+ " invokes canBeUsed and throw grave exception", exception);
		}

		return avaliable;
	}

	public List<Project> loadAll() {
		return projectDAO.loadAll();
	}

	/**
	 * Update attributes passed by a detached object
	 * 
	 * @param detachedProject
	 *            created in a page form
	 */
	@Override
	public Project update(Project detachedProject) {
		Project managedProject = projectDAO.find(detachedProject.getId());

		final String description = detachedProject.getDescription();
		final String title = detachedProject.getTitle();
		final Integer metodology = detachedProject.getMetodologyCode();
		final boolean isPublic = detachedProject.isPublic();

		if (!description.equals(managedProject.getDescription())) {
			logger.trace("update description");
			managedProject.setDescription(description);
		}

		if (!title.equals(managedProject.getTitle())) {
			logger.trace("update title");
			managedProject.setTitle(title);
		}

		if (!metodology.equals(managedProject.getMetodologyCode())) {
			logger.trace("update metodology");
			managedProject.setMetodologyCode(metodology);
		}

		// ^ is XOR operand
		if (isPublic ^ managedProject.isPublic()) {
			logger.trace("update privacy");
			managedProject.setPublic(isPublic);
		}
		
		return managedProject;
	}

	@Override
	public void save(Project project) {
		projectDAO.create(project);
	}

	@Override
	public void reload(Project entity) {
		// TODO Auto-generated method stub
		throw new NotImplementedError();
	}

	@Override
	public void delete(Project project) {
		try {
			Project projectToDelete = find(project.getId());
			
			if (projectToDelete != null) {
				removeRelationships(projectToDelete);
				projectDAO.destroy(projectToDelete);
			} else {
				throw new SystemBreakException("This project not exist");
			}
		} catch (NullPointerException npe) {
			throw new IllegalArgumentException("Project id is null", npe);
		}
	}

	@Override
	public boolean exists(Long id) {
		boolean projectExists = projectDAO.find(id) != null;

		return projectExists;
	}

	@Override
	public Project find(Long projectID) {
		return projectDAO.find(projectID);
	}

	@Override
	public Project findBy(String field, Object value) {
		throw new NotImplementedError();
	}

	@Override
	public List<Project> where(String conditions) {
		String sql = "SELECT project FROM " + Project.class.getName() + " project WHERE ";

		sql = sql.concat(conditions);

		return projectDAO.findUsing(sql);
	}

	/**
	 * To find by a {@link Searchable} values
	 * 
	 * @param attributeName should be a {@link Searchable} of {@link Project}
	 * @param value a string value
	 * 
	 * @return an unique Project 
	 */
	public Project find(Searchable attributeName, String value) {
		List<Project> result = projectDAO.get(attributeName.name().toLowerCase(), value);

		if (result.size() > 1) {
			throw new SystemBreakException("More than one result when requested by an unitary field");
		} else {

		}

		return result.get(FIRST);
	}

	/**
	 * This method is required to delete a project
	 * 
	 * @param projectToDelete project that will be deleted
	 */
	private void removeRelationships(Project projectToDelete) {
		// Removing all associated members
		for(UrutaUser member : projectToDelete.getMembers()) {
			member.getProjects().remove(projectToDelete);
		}
		// Removing all associated layers
		for(Layer layer : projectToDelete.getLayers()) {
			layer.getProjectsInvolved().remove(projectToDelete);
		}
	}
}
