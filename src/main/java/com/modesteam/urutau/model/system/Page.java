package com.modesteam.urutau.model.system;

public class Page {
	public static final int DEFAULT_NUMBER_OF_ELEMENTS = 5;

	private Integer number = 0;
	private Integer numberOfElements = 0;

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getElements() {
		if(numberOfElements == null || numberOfElements <= 0) {
			numberOfElements = DEFAULT_NUMBER_OF_ELEMENTS;
		}
		return numberOfElements;
	}

	public Page setElements(Integer elements) {
		this.numberOfElements = elements;
		return this;
	}

	public Integer getFirstPositionInPage() {
		Integer lowerIndex = getNumber() * getElements();

		return lowerIndex;
	}

	public Integer getLastPositionInPage() {
		Integer upperIndex = getFirstPositionInPage() + getElements();
		return upperIndex;
	}

}
