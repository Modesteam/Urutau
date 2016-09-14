package com.modesteam.urutau.model.system;

/**
 * List of constant artifact types
 */
public enum ArtifactType {
	EPIC, GENERIC, FEATURE, STORIE, USECASE;

    public static ArtifactType equivalentTo(String artifactType) {
    	ArtifactType choosed = null;
        
        for (ArtifactType type : ArtifactType.values()) {
            String enumInString = type.name().toLowerCase();

            if (artifactType.equals(enumInString)) {
                choosed = type;
                break;
            }
        }
        
        return choosed;
    }
}
