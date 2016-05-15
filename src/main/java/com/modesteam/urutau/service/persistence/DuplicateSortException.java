package com.modesteam.urutau.service.persistence;

/**
 * This are fires when asc and desc methods of {@link Order} are called.
 */
public class DuplicateSortException extends RuntimeException {

	private static final long serialVersionUID = 6139921435754157333L;
	
	public DuplicateSortException(String message) {
		super(message);
	}
}
