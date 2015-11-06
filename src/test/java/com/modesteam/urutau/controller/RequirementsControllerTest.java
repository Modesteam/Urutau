package com.modesteam.urutau.controller;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;

import com.modesteam.urutau.UserManager;
import com.modesteam.urutau.builder.ArtifactBuilder;
import com.modesteam.urutau.builder.UserBuilder;
import com.modesteam.urutau.model.Artifact;
import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;
import com.modesteam.urutau.model.User;
import com.modesteam.urutau.service.RequirementService;

public class RequirementsControllerTest {
	
	private MockResult mockResult;
	private UserManager mockUserSession;
	private MockValidator mockValidator;
	private RequirementService mockArtifactService;
	
	
	@Before
	public void setup() {
		// Mocks supported by vraptor
		mockResult = new MockResult();
		mockValidator = new MockValidator();
				
		// System's components
		mockArtifactService = EasyMock.createMock(RequirementService.class);
		mockUserSession = EasyMock.createMock(UserManager.class);
		
	}
	
	@Test
	public void createValidFeature() {
		ArtifactBuilder builderFeature = new ArtifactBuilder();
		
		Feature feature = builderFeature
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildFeature();

		mockService();
		mockAdd(feature);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(mockResult,mockUserSession,
																			mockArtifactService,mockValidator);
		controllerMock.createFeature(feature);
	}
	
	@Test
	public void createValidEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();
		
		Epic epic = builderEpic
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildEpic();

		mockService();
		mockAdd(epic);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(mockResult,mockUserSession,
																			mockArtifactService,mockValidator);
		controllerMock.createEpic(epic);
	}
	
	@Test
	public void createValidStorie() {
		ArtifactBuilder builderStorie = new ArtifactBuilder();
		
		Storie storie = builderStorie
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildStorie();

		mockService();
		mockAdd(storie);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(mockResult,mockUserSession,
																			mockArtifactService,mockValidator);
		controllerMock.createUserStory(storie);
	}
	
	@Test
	public void createValidUseCase() {
		ArtifactBuilder builderUseCase = new ArtifactBuilder();
		
		UseCase useCase = builderUseCase
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildUseCase();

		mockService();
		mockAdd(useCase);
		PowerMock.replayAll();
		RequirementController controllerMock = new RequirementController(mockResult,mockUserSession,
																			mockArtifactService,mockValidator);
		controllerMock.createUseCase(useCase);
	}

	@Test
	public void successfullyDeletedEpic() {
		ArtifactBuilder builderEpic = new ArtifactBuilder();
		
		Epic epic = builderEpic
					.id(1L)
					.title("exemple")
					.description("blabla")
					.buildEpic();

		mockService();
		mockAdd(epic);
		PowerMock.replayAll();
		mockRemove(1L);
		RequirementController controllerMock = new RequirementController(mockResult,mockUserSession,
																			mockArtifactService,mockValidator);
		controllerMock.excludeRequirement(1L);
		
	}
	
	
	private void mockAdd(Artifact artifact){
		mockArtifactService.save(artifact);
		EasyMock.expectLastCall();
	}
	
	private void mockService() {
		User userMocked = EasyMock.createNiceMock(User.class);
		EasyMock.expect(mockUserSession.getUserLogged()).andReturn(userMocked);
	}
	
	private void mockRemove(Long id){
		mockArtifactService.excludeRequirement(id);
		EasyMock.expectLastCall();
	}
}