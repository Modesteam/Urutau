package com.modesteam.urutau.service;

import java.util.ResourceBundle;

import javax.enterprise.context.Dependent;

import com.modesteam.urutau.exception.SystemBreakException;
import com.modesteam.urutau.model.system.ContextPlace;

import br.com.caelum.vraptor.i18n.Message;
import br.com.caelum.vraptor.validator.I18nMessage;

@Dependent
public class I18nMessageCreator {
	private static final String FILE_MESSAGE = "messages";

	private I18nMessage i18nMessage;

	private String message;

	/**
	 * Creates a new message internationalized. See {@link I18nMessage} and
	 * {@link Message}
	 * 
	 * @param category
	 *            of message
	 * @param message
	 *            link of message
	 * 
	 * @return a {@link I18nMessage}
	 */
	public I18nMessage create(ContextPlace category, final String message) {
		i18nMessage = new I18nMessage(category.name().toLowerCase(), message);
		i18nMessage.setBundle(ResourceBundle.getBundle(FILE_MESSAGE));
		return i18nMessage;
	}

	public I18nMessageCreator translate(String message) {
		this.message = message;
		return this;
	}

	public I18nMessage to(ContextPlace category) {
		try {
			i18nMessage = new I18nMessage(category.name().toLowerCase(), message);
			i18nMessage.setBundle(ResourceBundle.getBundle(FILE_MESSAGE));
		} catch(NullPointerException npe) {
			throw new SystemBreakException("Category of message has no passed!");
		}

		return i18nMessage;
	}
}
