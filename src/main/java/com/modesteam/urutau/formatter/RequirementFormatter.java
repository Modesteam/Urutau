package com.modesteam.urutau.formatter;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;

@RequestScoped
public class RequirementFormatter {

	private static final Logger logger = LoggerFactory.getLogger(RequirementFormatter.class);

	private final UserSession userSession;
	private final ProjectService projectService;
	private final KanbanService kanbanService;

	public RequirementFormatter() {
		this(null, null, null);
	}

	/**
	 * The Superclass {@link EntityCreator} requires an DAO to work
	 */
	@Inject
	public RequirementFormatter(UserSession userSession, ProjectService projectService,
			KanbanService kanbanService) {
		this.userSession = userSession;
		this.projectService = projectService;
		this.kanbanService = kanbanService;
	}

	/**
	 * Called by all methods that makes requirements creation, this have basic
	 * implementation to persisted. IMPORTANT: validate before call this method
	 * through by {@link #validate()}
	 * 
	 * @param requirement
	 *            to be persisted
	 * @throws UnsupportedEncodingException
	 * @throws NumberFormatException
	 */
	public void format(Artifact formated) {
		logger.info("Try persist " + formated.getTitle());

		// Setting current date
		Calendar calendar = getCurrentDate();
		formated.setDateOfCreation(calendar);

		// Setting author
		formated.setAuthor(getCurrentAuthor());

		// Setting project
		final Long projectID = formated.getProjectID();
		formated.setProject(getCurrentProject(projectID));

		// Setting to Backlog Layer
		Layer layer = kanbanService.getBackLogLayer();
		formated.setLayer(layer);
	}

	/**
	 * Get an instance of current date through of {@link Calendar}
	 * 
	 * @return current date
	 */
	private Calendar getCurrentDate() {
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);

		return calendar;
	}

	/**
	 * Get owner({@link UrutaUser}) of Requirement
	 * 
	 * @param requirement
	 *            inserted by form
	 * @return
	 */
	private UrutaUser getCurrentAuthor() {
		UrutaUser logged = userSession.getUserLogged();

		return logged;
	}

	/**
	 * Setting project of Requirement
	 * 
	 * @param requirement
	 *            inserted by form
	 * @return
	 */
	private Project getCurrentProject(final Long projectID) {

		assert (projectID != null);

		// Load by id
		Project referedProject = projectService.find(projectID);

		return referedProject;
	}
}
