package com.modesteam.urutau.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.validation.MessageInterpolator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Alternative of
 * {@link br.com.caelum.vraptor.validator.beanvalidation.MessageInterpolatorFactory}
 * .
 * 
 * This class makes javax.validator search models constraints like {@link Size}
 * or {@link NotNull} search the messages into message.properties file
 */
@Alternative
@ApplicationScoped
public class MessageInterpolatorFactory {
	private final Logger logger = LoggerFactory.getLogger(MessageInterpolatorFactory.class);

	@Produces
	@ApplicationScoped
	public MessageInterpolator getMessageInterpolator() {
		logger.info("Configuring message interpolator");

		ResourceBundleLocator resourceBundleLocator = new PlatformResourceBundleLocator("messages");

		return new ResourceBundleMessageInterpolator(resourceBundleLocator);
	}
}
