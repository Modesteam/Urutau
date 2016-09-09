package com.modesteam.urutau.controller.message;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.system.ContextPlace;
import com.modesteam.urutau.service.I18nMessageCreator;

import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;

@Dependent
public class ErrorMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(ErrorMessageHandler.class);

	private final Validator validator;
	private final I18nMessageCreator i18n;

	private ContextPlace currentContext;
	private Boolean conditionToShow;

	/**
	 * @deprecated CDI eyes only
	 */
	public ErrorMessageHandler() {
		this(null, null);
	}

	@Inject
	public ErrorMessageHandler(Validator validator, I18nMessageCreator i18n) {
		this.validator = validator;
		this.i18n = i18n;
	}

	/**
	 * Sets the context where errors will be shows
	 * 
	 * @param context {@link Enum} Object with all contexts of system
	 */
	public void validates(ContextPlace context) {
		this.currentContext = context;
	}

	/**
	 * Adds error independent of any condition
	 * 
	 * @param message Error message
	 */
	public ErrorMessageHandler add(String message) {
		I18nMessage translatedMessage = i18n.translate(message).to(currentContext);
		validator.add(translatedMessage);
		return this;
	}

	/**
	 * Sets a condition to throws an specified error
	 * 
	 * @param condition true to throws
	 * @return this Object to use chain technique
	 */
	public ErrorMessageHandler when(Boolean condition) {
		conditionToShow = condition;
		return this;
	}

	/**
	 * Show the message in a {@link ContextPlace} 
	 * after invoke a {@link #when(Boolean)} method
	 * 
	 * @param message Error message
	 */
	public ErrorMessageHandler show(String message) {
		if(conditionToShow != null) {
			validator.addIf(conditionToShow, 
					(i18n.translate(message).to(currentContext)));
		} else {
			throw new SystemBreakException("Invoke #show without call #when method");
		}

		/**
		 *  This condition should be null to not permit shows
		 *  an message without invoke a when(Boolean) method
		 */
		conditionToShow = null;
		
		return this;
	}

	/**
	 * Uses {@link Validator#onErrorUse(Class)} to send a JSON format
	 * that contains all errors added.
	 */
	public void sendAnJson() {

		logger.info("Send a json with " + validator.getErrors().size() + " errors");

		validator.onErrorUse(Results.json()).withoutRoot()
			.from(validator.getErrors()).serialize();
	}

	/**
	 * Uses {@link Validator#onErrorRedirectTo(Class)} to redirects to another action
	 * 
	 * @param controller contains action that will be called 
	 * @return {@link br.com.caelum.vraptor.Controller} to calls some action
	 */
	public <Controller> Controller redirectingTo(Class<Controller> controller) {
		return validator.onErrorRedirectTo(controller);
	}

	/**
	 * Uses {@link Validator#onErrorUsePageOf(Object)} to redirects to certain page
	 * 
	 * @param controller contains page references
	 * @return {@link br.com.caelum.vraptor.Controller} to calls some page  
	 */
	public <Controller> Controller onErrorUsePageOf(Class<Controller> controller) {
		return validator.onErrorUsePageOf(controller);
	}

	/**
	 * returns {@link Validator#hasErrors()}
	 */
	public boolean hasErrors() {
		return validator.hasErrors();
	}

	/**
	 * @return {@link Validator#getErrors().size()} 
	 */
	public int errorsCount() {
		return validator.getErrors().size();
	}
}
