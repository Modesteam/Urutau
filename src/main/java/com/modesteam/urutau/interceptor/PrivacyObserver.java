package com.modesteam.urutau.interceptor;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.controller.ApplicationController;
import com.modesteam.urutau.model.Project;

import br.com.caelum.vraptor.Result;

public class PrivacyObserver {
	private final UserSession userSession;
	private final Result result;

	public PrivacyObserver() {
		this(null, null);
	}

	@Inject
	public PrivacyObserver(UserSession userSession, Result result) {
		this.userSession = userSession;
		this.result = result;
	}

	public void userHavePermission(@Observes Project project) {
		if(!project.isPublic()) {
			boolean shouldIntercept = false;
	
			if (userSession.getUserLogged() == null) {
				shouldIntercept = true;
			} else if (userSession.getUserLogged().isMemberOf(project)) {
				// do nothing...
			} else {
				shouldIntercept = true;
			}
	
			if(shouldIntercept) {
				redirectTo404();
			}
		}
	}

	private void redirectTo404() {
		result.redirectTo(ApplicationController.class).invalidRequest();
	}

}
