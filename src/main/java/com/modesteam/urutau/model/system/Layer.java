package com.modesteam.urutau.model.system;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.modesteam.urutau.model.Project;

@Entity
public class Layer {
	@Id
	@GeneratedValue
	private Long layerID;

	@NotNull
	@Size(min = 2)
	private String name;

	private String description;

	@ManyToMany(mappedBy = "layers")
	private List<Project> projectsInvolved = new ArrayList<Project>();

	public Long getLayerID() {
		return layerID;
	}

	public void setLayerID(Long layerID) {
		this.layerID = layerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Project> getProjectsInvolved() {
		return projectsInvolved;
	}

	public void setProjectsInvolved(List<Project> projectsInvolved) {
		this.projectsInvolved = projectsInvolved;
	}
}
