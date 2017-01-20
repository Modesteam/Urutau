package com.modesteam.urutau.interceptor;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.annotation.Restrict;
import com.modesteam.urutau.controller.IndexController;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import io.github.projecturutau.vraptor.handler.FlashMessage;

@Intercepts
public class LoginInterceptor implements Interceptor {

	private final FlashMessage flash;
	private final UserSession userSession;

	public LoginInterceptor() {
		this(null, null);
	}

	@Inject
	public LoginInterceptor(FlashMessage flash, UserSession userSession) {
		this.flash = flash;
		this.userSession = userSession;
	}

	/**
	 * When user is logged should proceed, if not redirects to login
	 */
	@Override
	public void intercept(InterceptorStack stack, ControllerMethod method,
			Object controllerInstance) throws InterceptionException {
			if (userSession.isLogged()) {
				stack.next(method, controllerInstance);
			} else {
				flash.use("warning").toShow("login_required")
					.redirectTo(IndexController.class).index();
			}
	}

	/**
	 * See if method or controller class have {@link Restrict} annotation
	 * to accepts this interception or not.
	 */
	@Override
	public boolean accepts(ControllerMethod method) {
		boolean isRestrict = method.containsAnnotation(Restrict.class);

		for (Annotation a : method.getController().getAnnotations()) {
			if (a.annotationType().equals(Restrict.class)) {
				isRestrict = true;
			}
		}

		return isRestrict;
	}
}
