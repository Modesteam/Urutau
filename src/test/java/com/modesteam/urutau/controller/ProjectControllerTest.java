package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.ProjectBuilder;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Project.Searchable;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.MetodologyEnum;
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.ValidationException;

public class ProjectControllerTest extends UrutaUnitTest {

	@Before
	public void setup() {
		super.setup();
	}

	@Test
	public void createValidProject() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title("Example Valid")
				.description("test unit")
				.metodology(MetodologyEnum.GENERIC.toString())
				.builProject();

		when(projectService.titleAvaliable(project.getTitle())).thenReturn(true);

		mockGetDefaultLayers();

		doNothing().when(projectService).save(project);

		mockI18nMessages("title_already_in_used", ContextPlace.MODAL_ERROR);

		when(i18nCreator.create(ContextPlace.MODAL_ERROR, "title_already_in_used"))
				.thenReturn(mock(I18nMessage.class));

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.create(project);
	}

	@Test(expected = ValidationException.class)
	public void createInvalidProject() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title(SOME_STRING)
				.description(SOME_STRING)
				.builProject();

		when(projectService.titleAvaliable(SOME_STRING)).thenReturn(false);

		mockI18nMessages("title_already_in_used", ContextPlace.MODAL_ERROR);

		when(i18nCreator.create(ContextPlace.MODAL_ERROR, "title_already_in_used"))
				.thenReturn(mock(I18nMessage.class));

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.create(project);
	}

	@Test
	public void deleteValidProject() {
		Project project = new Project();
		project.setId(1L);

		when(projectService.find(1L)).thenReturn(project);
		doNothing().when(projectService).delete(project);

		mockI18nMessages("project_already_deleted", ContextPlace.INDEX_PANEL);
		mockI18nMessages("project_deleted", ContextPlace.INDEX_PANEL);

		when(i18nCreator.create(ContextPlace.INDEX_PANEL, "project_already_deleted"))
				.thenReturn(mock(I18nMessage.class));
		when(i18nCreator.create(ContextPlace.INDEX_PANEL, "project_deleted"))
		.thenReturn(mock(I18nMessage.class));

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.delete(project);
	}

	@Test(expected = ValidationException.class)
	public void deleteInvalidProject() {
		Project project = new Project();
		project.setId(1L);

		when(projectService.find(1L)).thenReturn(null);
		doNothing().when(projectService).delete(project);
		
		mockI18nMessages("project_already_deleted", ContextPlace.INDEX_PANEL);

		when(i18nCreator.create(ContextPlace.INDEX_PANEL, "project_already_deleted"))
				.thenReturn(mock(I18nMessage.class));

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.delete(project);
	}

	@Test
	public void editProject() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title(SOME_STRING)
				.builProject();

		when(projectService.find(Searchable.TITLE, SOME_STRING)).thenReturn(project);

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.edit(project);
	}

	@Test
	public void updateProject() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title(SOME_STRING)
				.builProject();

		when(projectService.find(project.getId())).thenReturn(project);

		when(projectService.update(project)).thenReturn(project);

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.update(project);
	}

	@Test
	public void showProject() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title("title+should+be+like+that")
				.builProject();

		when(projectService.find(Searchable.TITLE, "title should be like that"))
				.thenReturn(project);

		mockI18nMessages("invalid_link", ContextPlace.MODAL_ERROR);

		when(i18nCreator.create(ContextPlace.MODAL_ERROR, "invalid_link"))
				.thenReturn(mock(I18nMessage.class));

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		Assert.assertEquals(controllerMock.show(project), project);
	}

	@Test
	public void showProjectByID() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title(SOME_STRING)
				.builProject();

		when(projectService.find(1L)).thenReturn(project);

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.show(1L);
	}

	@Test
	public void indexProject() {
		ProjectBuilder projectBuilder = new ProjectBuilder();

		Project project = projectBuilder
				.id(1L)
				.title(SOME_STRING)
				.builProject();

		List<Project> projects = new ArrayList<Project>();
		projects.add(project);

		when(userSession.getUserLogged().getProjects()).thenReturn(projects);

		ProjectController controllerMock = new ProjectController(result, userSession,
				projectService, userService, kanbanService, errorHandler, messageHandler);

		controllerMock.index();
	}

	private void mockGetDefaultLayers() {
		List<Layer> layers = new ArrayList<Layer>();
		layers.add(mock(Layer.class));

		when(kanbanService.getDefaultLayers()).thenReturn(layers);
	}

}