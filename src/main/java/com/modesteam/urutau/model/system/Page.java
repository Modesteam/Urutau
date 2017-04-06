package com.modesteam.urutau.model.system;

public class Page {
	public static final int DEFAULT_NUMBER_OF_ELEMENTS = 3;

	private Integer number = 0;
	private Integer numberOfElements = 0;

	public Page setNumber(Integer number) {
		this.number = number;
		return this;
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

	public Integer getLastIndexItem() {
		Integer lowerIndex = getNumber() * getElements();
		Integer upperIndex = lowerIndex + getElements();
		return upperIndex;
	}

}
