package com.modesteam.urutau.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.LazyInitializationException;

import com.modesteam.urutau.model.system.Layer;
import com.modesteam.urutau.model.system.MetodologyEnum;

@Entity
public class Project implements Cloneable {
	
	public enum Searchable {TITLE, METODOLOGY}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "{project.title.empty}")
	@Size(min = 2, max = 20, message = "{project.title.size}")
	private String title;
	private String description;

	/**
	 * Metodology name are not persisted, but converted to a numerical code
	 */
	@Transient
	private String metodology;

	private int metodologyCode;

	@OneToMany(mappedBy = "project", cascade=CascadeType.REMOVE)
	private List<Artifact> requirements = new ArrayList<Artifact>();

	@ManyToOne
	@JoinColumn(name = "userID")
	private UrutaUser author;

	/* Artifact can be delegated to one or more persons */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "User_Project", joinColumns = @JoinColumn(name = "projectID") ,
			inverseJoinColumns = @JoinColumn(name = "userID"))
	private List<UrutaUser> members = new ArrayList<UrutaUser>();

	/* Should be generate automatically */
	private Calendar dateOfCreation;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Project_Layer", joinColumns = @JoinColumn(name = "project_id") ,
			inverseJoinColumns = @JoinColumn(name = "layer_id") )
	private List<Layer> layers = new ArrayList<Layer>();

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isPublic;

	@Transient
	private boolean isEmpty;

	@Override
	public Project clone() throws CloneNotSupportedException {
		return (Project) super.clone();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetodology() {
		return metodology;
	}

	public void setMetodology(String metodology) {
		this.metodology = metodology;
	}

	public int getMetodologyCode() {
		int code = 0;

		if (this.metodologyCode == 0) {
			for (MetodologyEnum metodologyEnum : MetodologyEnum.values()) {
				if (metodologyEnum.refersTo(metodology)) {
					code = metodologyEnum.getId();
				}
			}
		} else {
			code = this.metodologyCode;
		}

		return code;
	}

	public void setMetodologyCode(int metodologyCode) {
		this.metodologyCode = metodologyCode;
	}

	public UrutaUser getAuthor() {
		return author;
	}

	public void setAuthor(UrutaUser author) {
		this.author = author;
	}

	public List<UrutaUser> getMembers() {
		return members;
	}

	public void setMembers(List<UrutaUser> members) {
		this.members = members;
	}

	public Calendar getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Calendar dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public List<Artifact> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Artifact> requirements) {
		this.requirements = requirements;
	}

	public boolean isScrum() {
		return MetodologyEnum.SCRUM.getId() == metodologyCode;
	}

	public boolean isUP() {
		return MetodologyEnum.UNIFIED_PROCESS.getId() == metodologyCode;
	}

	public boolean isGeneric() {
		return MetodologyEnum.GENERIC.getId() == metodologyCode;
	}

	public boolean isEmpty() {
		try {
			return requirements.isEmpty();
		} catch (LazyInitializationException exception) {
			return false;
		}
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	/**
	 * Add an single layer
	 */
	public void add(Layer layer) {
		this.layers.add(layer);
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
}
