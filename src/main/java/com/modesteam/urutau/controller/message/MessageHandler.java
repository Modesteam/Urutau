package com.modesteam.urutau.controller.message;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.I18nMessageCreator;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Dependent
public class MessageHandler {
	private final Result result;
	private final I18nMessageCreator i18n;

	private ContextPlace currentContext;

	public MessageHandler() {
		this(null, null);
	}

	@Inject
	public MessageHandler(Result result, I18nMessageCreator i18n) {
		this.result = result;
		this.i18n = i18n;
	}

	/**
	 * Mark a context to put a message
	 *  
	 * @param context some place into views
	 * @return this Handler to calls {@link #show(String)} by chain method
	 */
	public MessageHandler use(ContextPlace context) {
		this.currentContext = context;
		return this;
	}

	/**
	 * Shows a translated message into some {@link ContextPlace}
	 *  
	 * @param message reference to translate
	 */
	public MessageHandler show(String message) {
		String translatedMessage = i18n.translate(message).to(currentContext).getMessage();
		result.include(currentContext.toString(), translatedMessage);
		return this;
	}

	/**
	 * Uses {@link Result#redirectingTo(Class)} and redirects to another action
	 * 
	 * @param controller contains action that will be called
	 * @return {@link br.com.caelum.vraptor.Controller} to calls some page
	 */
	public <Controller> Controller redirectTo(Class<Controller> controller) {
		return result.redirectTo(controller);
	}

	/**
	 * Send a result in JSON format
	 */
	public void sendViaJSON() {
		result.use(Results.json()).withoutRoot().from(result).serialize();
	}

	/**
	 * When catch an Exception return this interface to some treat
	 *  
	 * @param exception passed by other layer of architecture
	 * @return this
	 */
	public MessageHandler whenCatch(Class<? extends Exception> exception) {
		result.on(exception);
		return this;
	}

	public boolean containsMessageOf(ContextPlace projectPanel) {
		return result.included().containsKey(projectPanel.toString());
	}
}