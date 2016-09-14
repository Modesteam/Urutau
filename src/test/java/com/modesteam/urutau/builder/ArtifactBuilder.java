package com.modesteam.urutau.builder;

import com.modesteam.urutau.model.Epic;
import com.modesteam.urutau.model.Feature;
import com.modesteam.urutau.model.Generic;
import com.modesteam.urutau.model.Project;
import com.modesteam.urutau.model.Storie;
import com.modesteam.urutau.model.UseCase;

public class ArtifactBuilder {
	private String title;
	private String description;
	private Long id;
	private Project project;
	private Long projectID;

	public ArtifactBuilder title(String title) {
		this.title = title;
		return this;
	}

	public ArtifactBuilder description(String description) {
		this.description = description;
		return this;
	}

	public ArtifactBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public ArtifactBuilder projectID(Long projectID) {
		this.project = new Project();
		this.project.setId(projectID);
		this.projectID = projectID;
		return this;
	}

	public Generic buildGeneric() {
		Generic generic = new Generic();
		generic.setId(id);
		generic.setTitle(title);
		generic.setDescription(description);
		generic.setProjectID(projectID);
		generic.setProject(project);
		return generic;
	}

	public Epic buildEpic() {
		Epic epic = new Epic();
		epic.setId(id);
		epic.setTitle(title);
		epic.setDescription(description);
		epic.setProjectID(projectID);
		epic.setProject(project);
		return epic;
	}

	public Feature buildFeature() {
		Feature feature = new Feature();
		feature.setId(id);
		feature.setTitle(title);
		feature.setDescription(description);
		feature.setProjectID(projectID);
		feature.setProject(project);
		return feature;
	}

	public Storie buildStorie() {
		Storie storie = new Storie();
		storie.setId(id);
		storie.setTitle(title);
		storie.setDescription(description);
		storie.setProjectID(projectID);
		storie.setProject(project);
		return storie;
	}

	public UseCase buildUseCase() {
		UseCase useCase = new UseCase();
		useCase.setId(id);
		useCase.setTitle(title);
		useCase.setDescription(description);
		useCase.setProjectID(projectID);
		useCase.setProject(project);
		return useCase;
	}

}
