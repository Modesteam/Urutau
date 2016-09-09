package com.modesteam.urutau.model;

import javax.persistence.Entity;

@Entity
public class Epic extends Requirement {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
