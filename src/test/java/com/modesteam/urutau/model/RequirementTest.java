package com.modesteam.urutau.model;

import org.junit.Assert;
import org.junit.Test;

import com.modesteam.urutau.model.system.ArtifactType;

public class RequirementTest {
    public Requirement requirementTest;

    @Test
    public void testGetType() {
        Requirement feature = new Feature();
        Assert.assertEquals("feature", feature.toString());

        Requirement storie = new Storie();
        Assert.assertEquals("storie", storie.toString());

        Requirement epic = new Epic();
        // More verbose
        Assert.assertEquals("epic", epic.getType());
    }

    @Test
    public void testEnum() {
        Requirement generic = new Generic();
        Assert.assertEquals(ArtifactType.GENERIC, ArtifactType.equivalentTo(generic.getType()));
    }
}
