package com.modesteam.urutau.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@Entity
public class UseCase extends Requirement {
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Actors", joinColumns = @JoinColumn(name = "useCaseID") ,
			inverseJoinColumns = @JoinColumn(name = "actorID") )
	private List<Actor> actors;

	@Transient
	private String fakeActors;

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getFakeActors() {
		return fakeActors;
	}

	public void setFakeActors(String fakeActors) {
		this.fakeActors = fakeActors;
	}

	/**
	 * Sets up a String containing all the actors involved at the current use
	 * case.
	 * 
	 * @param useCase
	 */
	public void formatToRealActors() {

		String fakeActors[] = getFakeActors().split(","); // Separating each
															// actor by ','
		List<Actor> actors = new ArrayList<Actor>();

		for (String actorName : fakeActors) {
			Actor actor = new Actor();
			actor.setName(actorName);
			actors.add(actor);
		}

		setActors(actors);
	}
}
