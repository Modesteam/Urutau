package com.modesteam.urutau.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.model.system.Page;
import com.modesteam.urutau.test.UrutaUnitTest;

public class MemberControllerTest extends UrutaUnitTest {

	private static final String MEMBER_DESC = "test";
	private static final Long PROJECT_ID = 1L;
	private UrutaUser userMock;

	@Before
	public void setUp() {
		super.setup();
		this.userMock = mock(UrutaUser.class);
	}

	@Test
	public void addMemberWithoutPrivilegies() {
		prepareMemberPrivilegiesWith(false);

		MemberController controller = new MemberController(result, flash, projectService,
				userSession, userService);

		controller.addMember(PROJECT_ID, MEMBER_DESC);

		Assert.assertTrue(result.included().containsKey("error"));
	}

	@Test
	public void addMemberWithPrivilegies() {
		prepareMemberPrivilegiesWith(true);

		when(userService.findBy("email", MEMBER_DESC)).thenReturn(userMock);
		
		MemberController controller = new MemberController(result, flash, projectService,
				userSession, userService);

		controller.addMember(PROJECT_ID, MEMBER_DESC);

		Assert.assertTrue(result.included().containsKey("success"));
	}

	@Test
	public void showMembers() {
		final int membersQuantity = 5;

		Project projectMock = mock(Project.class);
		when(projectMock.getMembers()).thenReturn(getMembersMocks(membersQuantity));

		when(projectService.find(PROJECT_ID)).thenReturn(projectMock);

		MemberController controller = new MemberController(result, flash, projectService,
				userSession, userService);

		controller.showMembers(PROJECT_ID, getPage());
		
		Assert.assertEquals(membersQuantity, 
				((ArrayList<?>) result.included().get("members")).size());
	}
	
	@Test
	public void removeMemberWithPrivilegies() {
		Project project = prepareMemberPrivilegiesWith(true);
		Mockito.doNothing().when(project).removeMember(userMock);

		final Long userID = 1L;
		when(userService.find(userID)).thenReturn(userMock);

		MemberController controller = new MemberController(result, flash, projectService,
				userSession, userService);

		controller.removeMember(1L, PROJECT_ID);

		Assert.assertTrue(result.included().containsKey("success"));
	}
	
	@Test
	public void removeMemberWithoutPrivilegies() {
		Project project = prepareMemberPrivilegiesWith(false);
		Mockito.doNothing().when(project).removeMember(userMock);

		final Long userID = 1L;
		when(userService.find(userID)).thenReturn(userMock);

		MemberController controller = new MemberController(result, flash, projectService,
				userSession, userService);

		controller.removeMember(1L, PROJECT_ID);

		Assert.assertTrue(result.included().containsKey("error"));
	}
	
	private List<UrutaUser> getMembersMocks(int quantity) {
		List<UrutaUser> membersMock = new ArrayList<>();
		for (int x = 0; x < quantity; x++) {
			membersMock.add(mock(UrutaUser.class));
		}

		return membersMock;
	}

	private Page getPage() {
		Page page = new Page();
		page.setElements(10);
		page.setNumber(1);
		return page;
	}

	private Project prepareMemberPrivilegiesWith(boolean isAdmin) {
		when(userSession.getUserLogged()).thenReturn(userMock);

		Project project = mock(Project.class);
		when(project.isAdministrator(userMock)).thenReturn(isAdmin);

		when(projectService.find(PROJECT_ID)).thenReturn(project);
		
		return project;
	}
}
