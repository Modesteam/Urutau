package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.I18nMessage;

public class KanbanControllerTest extends UrutaUnitTest {

	private static final Long STUB_LONG_NUMBER = 1L;
	private static final Long VALID_PROJECT_ID = 1L;

	@Before
	public void setUp() {
		super.setup();
	}

	@Test
	public void testLoadValid() throws Exception {
		Project project = new Project();
		project.setId(VALID_PROJECT_ID);

		when(projectService.find(project.getId())).thenReturn(mock(Project.class));

		KanbanController controller = new KanbanController(kanbanService, projectService, 
				requirementService, result, messageHandler, errorHandler);

		controller.load(project);
	}

	@Test
	public void testValidMove() throws Exception {
		Requirement requirement = createAnMockRequirement();

		when(requirementService.find(STUB_LONG_NUMBER)).thenReturn(requirement);

		when(kanbanService.getLayerByID(STUB_LONG_NUMBER)).thenReturn(mock(Layer.class));

		when(requirementService.update(requirement)).thenReturn(requirement);

		when(i18nCreator.create(ContextPlace.KANBAN, "successfully_moved_requirement"))
				.thenReturn(mock(I18nMessage.class));

		mockI18nMessages("successfully_moved_requirement", ContextPlace.KANBAN);

		KanbanController controller = new KanbanController(kanbanService, projectService, 
				requirementService, result, messageHandler, errorHandler);

		controller.move(STUB_LONG_NUMBER, STUB_LONG_NUMBER);
	}

	@Test
	public void testInvalidMove() throws Exception {
		Requirement requirement = createAnMockRequirement();

		when(requirementService.find(STUB_LONG_NUMBER)).thenReturn(requirement);
		when(kanbanService.getLayerByID(STUB_LONG_NUMBER)).thenReturn(mock(Layer.class));

		doThrow(IllegalArgumentException.class).when(requirementService).update(requirement);

		when(i18nCreator.create(ContextPlace.ERROR, "invalid_request"))
				.thenReturn(mock(I18nMessage.class));

		mockI18nMessages("invalid_request", ContextPlace.ERROR);

		KanbanController controller = new KanbanController(kanbanService, projectService, 
				requirementService, result, messageHandler, errorHandler);

		controller.move(STUB_LONG_NUMBER, STUB_LONG_NUMBER);
	}

	@Test
	public void testCreateValidLayer() throws Exception {
		Layer mockLayer = mock(Layer.class);

		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);

		when(projectService.find(VALID_PROJECT_ID)).thenReturn(mockProject);

		doNothing().when(kanbanService).create(mockLayer);
		when(projectService.update(mockProject)).thenReturn(mockProject);

		KanbanController controller = new KanbanController(kanbanService, projectService, 
				requirementService, result, messageHandler, errorHandler);

		controller.createLayer(VALID_PROJECT_ID, mockLayer);
	}

	@Test
	public void testCreateInvalidLayer() {
		Layer mockLayer = mock(Layer.class);

		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);

		when(projectService.find(VALID_PROJECT_ID)).thenReturn(mockProject);

		doThrow(SystemBreakException.class).when(kanbanService).create(mockLayer);

		KanbanController controller = new KanbanController(kanbanService, projectService, 
				requirementService, result, messageHandler, errorHandler);

		controller.createLayer(VALID_PROJECT_ID, mockLayer);

		Assert.assertTrue(result.used());
	}

	private Requirement createAnMockRequirement() {
		Requirement requirement = mock(Requirement.class);

		Project mockProject = mock(Project.class);

		when(mockProject.getId()).thenReturn(STUB_LONG_NUMBER);

		when(requirement.getProject()).thenReturn(mock(Project.class));

		when(requirement.getProject()).thenReturn(mockProject);

		return requirement;
	}
}
