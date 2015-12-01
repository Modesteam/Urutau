package com.modesteam.urutau.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="id")
public class Generic extends Artifact {
	
	public Generic () {
		super.setArtifactType(ArtifactType.GENERIC);
	}
	
}
