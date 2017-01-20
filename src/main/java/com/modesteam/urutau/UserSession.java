package com.modesteam.urutau;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.model.UrutaUser;

@SessionScoped
@Named("userSession")
public class UserSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(UserSession.class);

	private UrutaUser userLogged;

	/**
	 * Saves user in session
	 * 
	 * @param user
	 *            to be save in session
	 */
	public void login(UrutaUser user) {
		this.userLogged = user;
	}

	/**
	 * Destroy userLogged.Makes possible the logging out.
	 */
	public void logout() {
		this.userLogged = null;
	}

	/**
	 * Verifies any user in session
	 * 
	 * @return true if user is logged
	 */
	public boolean isLogged() {
		boolean isLogged = true;

		// verifies through if and possible nullpointerexception
		try {
			if (userLogged.getUserID() == null) {
				isLogged = false;
			} else {
				logger.trace("User is logged");
			}
		} catch (NullPointerException npe) {
			isLogged = false;
		}

		return isLogged;
	}

	public UrutaUser getUserLogged() {
		return userLogged;
	}
}
