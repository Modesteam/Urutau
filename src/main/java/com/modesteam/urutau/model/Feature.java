package com.modesteam.urutau.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Feature extends Requirement {

	private String content;

	@OneToOne
	private Epic epic;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
