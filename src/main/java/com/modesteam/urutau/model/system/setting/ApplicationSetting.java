package com.modesteam.urutau.model.system.setting;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Each instance of Urutau should be a unique instance of this class
 */
@Entity
public class ApplicationSetting {
	/**
	 * This object should have a unique instance in database. 
	 * So this should be your unique id.
	 */
	public static final int APLICATION_SETTING_ID = 1;

	@Id
	private int id = APLICATION_SETTING_ID;
	private boolean userRegistrationIsOpen = false;
	private boolean onlyTheAdministratorCanInvite = true;
	private boolean requireAdministratorApproval = true;
	private String systemEmail;

	public boolean userRegistrationIsOpen() {
		return userRegistrationIsOpen;
	}

	public void setUserRegistrationIsOpen(boolean userRegistrationIsOpen) {
		this.userRegistrationIsOpen = userRegistrationIsOpen;
	}

	public boolean onlyTheAdministratorCanInvite() {
		return onlyTheAdministratorCanInvite;
	}

	public void setOnlyTheAdministratorCanInvite(boolean onlyTheAdministratorCanInvite) {
		this.onlyTheAdministratorCanInvite = onlyTheAdministratorCanInvite;
	}

	public boolean requiresApprovalOfAdministrator() {
		return requireAdministratorApproval;
	}

	public void setRequireAdministratorApproval(boolean requireAdministratorApproval) {
		this.requireAdministratorApproval = requireAdministratorApproval;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}
}
