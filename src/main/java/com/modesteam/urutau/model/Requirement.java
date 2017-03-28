package com.modesteam.urutau.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Requirement extends Artifact {
    @OneToOne(optional = true)
    private UrutaUser lastModificationAuthor;

    private Calendar lastModificationDate;

    /* Artifact can be delegated to one or more persons */
    @ManyToMany
    @JoinTable(name = "Artifact_Delegate", joinColumns = @JoinColumn(name = "artifact_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UrutaUser> responsables;

    /* Optional relationship */
    @OneToOne(optional = true)
    private Status status;

    public UrutaUser getLastModificationAuthor() {
        return lastModificationAuthor;
    }

    public void setLastModificationAuthor(UrutaUser lastModificationAuthor) {
        this.lastModificationAuthor = lastModificationAuthor;
    }

    public Calendar getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Calendar lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public List<UrutaUser> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<UrutaUser> responsables) {
        this.responsables = responsables;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return toString();
    }

    public String getIdentifierCode() {
    	String code = toString().substring(0, 2);
    	code = code.concat(" ");
    	code = code + Long.toString(getId()); 

    	return code.toUpperCase();
    }
}
