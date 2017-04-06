package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.system.Page;
import com.modesteam.urutau.service.persistence.FinderAdapter;
import com.modesteam.urutau.service.persistence.Order;
import com.modesteam.urutau.test.UrutaUnitTest;

public class RequirementControllerTest extends UrutaUnitTest {

	private static final Long FAKE_REQUIREMENT_ID = 1L;

	@Before
	public void setup() {
		super.setup();
	}

	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();
		
		Epic epic = builderEpic
				.id(FAKE_REQUIREMENT_ID)
				.projectID(1L)
				.title("Example")
				.description("test unit")
				.buildEpic();
		
		when(requirementService.find(FAKE_REQUIREMENT_ID)).thenReturn(epic);
		doNothing().when(requirementService).delete(epic);

		RequirementController controllerMock = new RequirementController(result, flash, 
				requirementService);

		controllerMock.delete(FAKE_REQUIREMENT_ID);
	}

	public void failedToDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();

		Epic epic = builderEpic
				.id(FAKE_REQUIREMENT_ID)
				.projectID(1L)
				.title("Example")
				.description("test unit")
				.buildEpic();

		when(requirementService.find(epic.getId())).thenReturn(epic);
		doThrow(new IllegalArgumentException()).when(requirementService).delete(epic);

		RequirementController controllerMock = new RequirementController(result, flash, 
				requirementService);

		controllerMock.delete(FAKE_REQUIREMENT_ID);
	}

	@Test
	public void testValidShow() throws UnsupportedEncodingException {
		ArtifactBuilder builder = new ArtifactBuilder();
		Generic genericRequirement = builder.id(FAKE_REQUIREMENT_ID).title("Example")
				.description("test unit").buildGeneric();

		when(requirementService.getBy(genericRequirement.getId(), genericRequirement.getTitle()))
				.thenReturn(genericRequirement);

		RequirementController controllerMock = new RequirementController(result, flash, 
				requirementService);

		controllerMock.show(1L, genericRequirement.getTitle());
	}

	@Test
	public void testPaginate() {
		RequirementController controllerMock = new RequirementController(result, flash, 
				requirementService);
		Page page = new Page();
		page.setNumber(7L);
		
		mockFinder(1L, page);

		controllerMock.paginate(1L, page);
	}

	@Test
	public void testViewCalls() {
		RequirementController controllerMock = new RequirementController(result, flash, 
				requirementService);
		controllerMock.detailRequirement();
		controllerMock.showExclusionResult();
	}

	private void mockFinder(Long projectId, Page page) {
		Order mockOrder = mock(Order.class);
		when(requirementService.desc("dateOfCreation")).thenReturn(mockOrder);

		when(mockOrder.between(page.getNumber(), page.getLastIndexItem())).thenReturn(mockOrder);

		FinderAdapter finderMock = mock(FinderAdapter.class);
		when(mockOrder.find()).thenReturn(finderMock);

		List requirements = mock(List.class);
		when(finderMock.where("project_id=" + projectId)).thenReturn(requirements);
	}
}