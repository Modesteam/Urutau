package com.modesteam.urutau.model.system;

public class Page {
	public static final int DEFAULT_NUMBER_OF_ELEMENTS = 3;

	private int number;
	private int numberOfElements;

	public Page setNumber(int pageNumber) {
		this.number = pageNumber;
		return this;
	}

	public Page setElements(int elements) {
		this.numberOfElements = elements;
		return this;
	}

	public int getElements() {
		if(numberOfElements <= 0) {
			numberOfElements = DEFAULT_NUMBER_OF_ELEMENTS;
		}
		return numberOfElements;
	}

	public int getNumber() {
		return number;
	}
	
	public int getLastIndexItem() {
		return number + getElements();
	}

}
