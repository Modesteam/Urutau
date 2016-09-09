package com.modesteam.urutau.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An critical exception that should abort operation and notify user and the
 * system administrator
 */
public class DataBaseCorruptedException extends RuntimeException {

	private static final long serialVersionUID = -8652376349870658194L;

	private final Logger logger = LoggerFactory.getLogger(DataBaseCorruptedException.class);

	private Class<?> targetClass;

	/**
	 * @param message
	 * 		explain context or simple description
	 */
	public DataBaseCorruptedException(String message) {
		super(message);
	}

	/**
	 * @param message
	 *            explain context or simple description
	 * @param throwable
	 *            cause of this exception
	 */
	public DataBaseCorruptedException(String message, Throwable throwable) {
		super(message, throwable);

		logger.warn("A fatal exception occurs, read this informations "
				+ "to keep consistence of system", throwable);
	}

	public DataBaseCorruptedException(String message, Throwable throwable, Class<?> targetClass) {
		super(message, throwable);
		this.targetClass = targetClass;
	}

	/**
	 * @return name of system
	 */
	public String getTargetClass() {
		return targetClass.getSimpleName();
	}
}
