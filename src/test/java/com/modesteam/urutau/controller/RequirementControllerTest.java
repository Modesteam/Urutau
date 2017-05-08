package com.modesteam.urutau.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.system.Page;
import com.modesteam.urutau.service.persistence.OrderEnum;
import com.modesteam.urutau.service.persistence.SearchOptions;
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

	@Ignore
	@Test
	public void testPaginate() {
		RequirementController controllerMock = new RequirementController(result, flash, 
				requirementService);
		Page page = new Page();
		page.setNumber(1);

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
		Query mockQuery = mock(Query.class);

		when(mockQuery.setFirstResult(page.getFirstPositionInPage())).thenReturn(mockQuery);
		when(mockQuery.setMaxResults(page.getLastPositionInPage())).thenReturn(mockQuery);

		when(mockQuery.getResultList()).thenReturn(new ArrayList<>());
		
		/*
		 * We need remove 'new' from method to be possible test
		 */
		when(requirementService.searchBy(
				new SearchOptions("project_id", projectId, 
						"dateOfCreation", OrderEnum.DESC)))
		.thenReturn(mockQuery);
	}
}