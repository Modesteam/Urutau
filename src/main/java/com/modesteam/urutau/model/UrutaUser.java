package com.modesteam.urutau.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.modesteam.urutau.model.system.Password;

/**
 * This class implements the generic user witch can be extended to an
 * Administrator.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", discriminatorType = DiscriminatorType.INTEGER)
@TableGenerator(name = "USER_TABLE_ID", initialValue = 0, allocationSize = 1)
@DiscriminatorValue("1")
public class UrutaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_TABLE_ID")
    private Long userID;

    @NotNull
    @Email(message = "{invalid_email}")
    private String email;
    @NotNull
    @Size(min = 3, max = 20, message = "{user.name.size}")
    private String name;
    @NotNull
    @Size(min = 3, max = 20, message = "{user.lastName.size}")
    private String lastName;
    @NotNull
    @Size(min = 6, max = 20, message = "{user.login.size}")
    private String login;
    @OneToOne(cascade=CascadeType.ALL)
    private Password password;
    @Transient
    private String passwordVerify;
    /*
     * 0 - wait (default value) 1 - confirmed
     */
    private int confirmed = 0;
    @ManyToMany(mappedBy = "responsables")
    private List<Requirement> artifactsDelegates = new ArrayList<Requirement>();

    /*
     * All projects that user is integrated
     */
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Project> projects = new ArrayList<Project>();

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public boolean isConfirmed() {
        return confirmed == 1;
    }

    public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public String getPasswordVerify() {
        return passwordVerify;
    }

    public void setPasswordVerify(String passwordVerify) {
        this.passwordVerify = passwordVerify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public List<Requirement> getArtifactsDelegates() {
        return artifactsDelegates;
    }

    public void setArtifactsDelegates(List<Requirement> artifactsDelegates) {
        this.artifactsDelegates = artifactsDelegates;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project basicProject) {
        this.projects.add(basicProject);
    }

	public boolean isMemberOf(Project project) {
		boolean isMember = false;

		for(Project p : projects) {
			if(p.getId() == project.getId()) {
				isMember = true;
				break;
			}
		}

		return isMember;
	}

	public boolean hasNullField() {
		boolean hasNullField = this.getEmail() == null || this.getLogin() == null
				|| this.getName() == null || this.getPasswordVerify() == null
				|| this.getPassword() == null;

		return hasNullField;
	}

	/**
	 * Verify if password confirmation is right
	 * 
	 * @return true if equals to password
	 */
	public boolean isValidPasswordConfirmation() {
		String original = this.getPassword().getUserPasswordPassed();
		String confirmation = this.getPasswordVerify();

		boolean isEquals = false;

		try {
			isEquals = original.equals(confirmation);
		} catch(NullPointerException npe) {
			isEquals = false;
		}

		return isEquals;
	}
}