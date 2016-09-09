package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.controller.message.ErrorMessageHandler;
import com.modesteam.urutau.controller.message.MessageHandler;
import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.service.I18nMessageCreator;
import com.modesteam.urutau.service.KanbanService;
import com.modesteam.urutau.service.ProjectService;
import com.modesteam.urutau.service.RequirementService;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

public class KanbanControllerTest {

	private static final Long STUB_LONG_NUMBER = 1L;
	private static final Long VALID_PROJECT_ID = 1L;

	private Result result;
	private Validator validator;
	private KanbanService kanbanService;
	private ProjectService projectService;
	private RequirementService requirementService;
	private I18nMessageCreator i18nCreator;
	private MessageHandler messageHandler;
	private ErrorMessageHandler errorHandler;

	@Before
	public void setUp() {
		// Mocks supported by vraptor
		result = new MockResult();
		validator = new MockValidator();

		// System components
		requirementService = mock(RequirementService.class);
		kanbanService = mock(KanbanService.class);
		projectService = mock(ProjectService.class);

		// Messages
		i18nCreator = mock(I18nMessageCreator.class);
		errorHandler = new ErrorMessageHandler(validator, i18nCreator);
		messageHandler = new MessageHandler(result, i18nCreator);
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

		doNothing().when(requirementService).update(requirement);

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
		doNothing().when(projectService).update(mockProject);

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
	
	private void mockI18nMessages(String message, ContextPlace place) {
		when(i18nCreator.translate(message)).thenReturn(i18nCreator);
		
		I18nMessage i18n = new I18nMessage(place.name(), message);
		i18n.setBundle(ResourceBundle.getBundle("messages"));

		when(i18nCreator.to(place)).thenReturn(i18n);
	}
}
