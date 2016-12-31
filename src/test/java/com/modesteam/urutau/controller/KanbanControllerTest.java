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
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.test.UrutaUnitTest;

import br.com.caelum.vraptor.validator.ValidationException;

public class KanbanControllerTest extends UrutaUnitTest {

	@Before
	public void setUp() {
		super.setup();
	}

	@Test
	public void testLoadValid() throws Exception {
		Project project = new Project();
		project.setId(1L);

		when(projectService.find(project.getId())).thenReturn(mock(Project.class));

		KanbanController controller = new KanbanController(kanbanService, projectService,
				requirementService, result, flash, flashError);

		controller.load(project);
	}

	@Test
	public void testValidMove() throws Exception {
		Requirement requirement = createAnMockRequirement();

		when(requirementService.find(1L)).thenReturn(requirement);

		when(kanbanService.getLayerByID(1L)).thenReturn(mock(Layer.class));

		when(requirementService.update(requirement)).thenReturn(requirement);

		KanbanController controller = new KanbanController(kanbanService, projectService,
				requirementService, result, flash, flashError);

		controller.move(1L, 1L);
	}

	@Test
	public void testInvalidMove() throws Exception {
		Requirement requirement = createAnMockRequirement();

		when(requirementService.find(1L)).thenReturn(requirement);
		when(kanbanService.getLayerByID(1L)).thenReturn(mock(Layer.class));

		doThrow(IllegalArgumentException.class).when(requirementService).update(requirement);

		KanbanController controller = new KanbanController(kanbanService, projectService,
				requirementService, result, flash, flashError);

		controller.move(1L, 1L);
	}

	@Test
	public void testCreateValidLayer() throws Exception {
		Layer mockLayer = mock(Layer.class);
		when(mockLayer.getName()).thenReturn(SOME_STRING);
		
		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);

		when(projectService.find(1L)).thenReturn(mockProject);

		doNothing().when(kanbanService).create(mockLayer);
		when(projectService.update(mockProject)).thenReturn(mockProject);

		KanbanController controller = new KanbanController(kanbanService, projectService,
				requirementService, result, flash, flashError);

		controller.createLayer(1L, mockLayer);
	}

	@Test(expected=ValidationException.class)
	public void testCreateInvalidLayerWithWrongData() {
		Layer mockLayer = mock(Layer.class);

		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);

		KanbanController controller = new KanbanController(kanbanService, projectService,
				requirementService, result, flash, flashError);

		controller.createLayer(-1L, mockLayer);
	}
	
	@Test
	public void testCreateInvalidLayer() {
		Layer mockLayer = mock(Layer.class);
		when(mockLayer.getName()).thenReturn(SOME_STRING);

		Project mockProject = mock(Project.class);
		mockProject.add(mockLayer);

		when(projectService.find(1L)).thenReturn(mockProject);

		doThrow(SystemBreakException.class).when(kanbanService).create(mockLayer);

		KanbanController controller = new KanbanController(kanbanService, projectService,
				requirementService, result, flash, flashError);

		controller.createLayer(1L, mockLayer);

		Assert.assertTrue(result.used());
	}

	private Requirement createAnMockRequirement() {
		Requirement requirement = mock(Requirement.class);

		Project mockProject = mock(Project.class);

		when(mockProject.getId()).thenReturn(1L);

		when(requirement.getProject()).thenReturn(mock(Project.class));

		when(requirement.getProject()).thenReturn(mockProject);

		return requirement;
	}
}
