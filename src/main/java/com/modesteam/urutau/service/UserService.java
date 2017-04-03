package com.modesteam.urutau.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.exception.DataBaseCorruptedException;
import com.modesteam.urutau.exception.NotImplementedError;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.persistence.Finder;
import com.modesteam.urutau.service.persistence.Persistence;

@RequestScoped
public class UserService implements Finder<UrutaUser>, Persistence<UrutaUser> {

	private final static Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserDAO userDAO;
	private final UserSession userSession;

	/**
	 * @deprecated only CDI eye
	 */
	public UserService() {
		this(null, null);
	}
	
	@Inject
	public UserService(UserDAO userDAO, UserSession userSession) {
		this.userDAO = userDAO;
		this.userSession = userSession;
	}

	/**
	 * See {@link UserDAO#create(UrutaUser)}
	 */
	@Override
	public void save(UrutaUser user) {
		user.getPassword().generateHash();
		userDAO.create(user);
	}

	@Override
	public void reload(UrutaUser user) {
		// TODO
	}

	/**
	 * See {@link UserDAO#destroy(UrutauUser)}
	 */
	@Override
	public void delete(UrutaUser user) {
		userDAO.destroy(user);
	}

	/**
	 * Method to verify if exist a user with same email or same login
	 * 
	 * @return false if the verification fails
	 */
	public boolean canBeUsed(final String attributeName, final Object value) {
		boolean available = false;

		try{
			// if not exist a user with this attribute
			if(userDAO.get(attributeName, value).isEmpty()) {
				// so it is available
				available = true;
			}
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorruptedException("Duplicate register", exception, this.getClass());
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		logger.debug(attributeName + " can be used? " + available);

		return available;
	}

	
	/**
	 * TODO treat better this update
	 * See {@link UserDAO#update(UrutaUser)}
	 */
	@Override
	public UrutaUser update(UrutaUser user) {
		UrutaUser userTarget = userDAO.find(userSession.getUserLogged().getUserID());
		userTarget.setLogin(user.getLogin());
		userTarget.setEmail(user.getEmail());
		userTarget.setName(user.getName());
		userTarget.setLastName(user.getLastName());
		userSession.login(userTarget);
		return userTarget;
	}

	/**
	 * See if an user exist
	 * 
	 * @param login
	 * @return
	 */
	public boolean existsUser(String login) {
		boolean userExistence = false;
		
		try {
			if(userDAO.get("login", login) != null) {
				userExistence = true;
			} else {
				userExistence = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userExistence;
	}

	/**
	 * If returns null, user was not authenticate
	 * 
	 * @param login to verifies
	 * @param password to compare with database instance
	 * @return {@link UrutaUser} instance of database
	 * @throws Exception TODO treat
	 */
	public UrutaUser authenticate(String login, String password) throws Exception {
		UrutaUser user = null;
		
		// Should return a unique register
		List<UrutaUser> users = userDAO.get("login", login);

		if (users.size() > 1) {
			throw new DataBaseCorruptedException("Accept two or more users with the same login");
		} else {
			try {
				user = users.get(0);
				// Put password passed
				user.getPassword().setUserPasswordPassed(password);
			} catch (IndexOutOfBoundsException exception) {
				exception.printStackTrace();
			} catch (NullPointerException nullPointerException) {
				nullPointerException.printStackTrace();
			}
		}

		// Case exists, login is true and verifies password
		if (user != null && !user.getPassword().authenticated()) {
			// reset user
			user = null;
		} else {
			logger.info("The login informed doesn't exist at the system");
		}
		
		return user;
	}

	@Override
	public boolean exists(Long id) {
		throw new NotImplementedError();
	}

	@Override
	public UrutaUser find(Long id) {
		return userDAO.find(id);
	}

	@Override
	public List<UrutaUser> findBy(String field, Object value) {
		List<UrutaUser> users = new ArrayList<>();

		try {
			users.addAll(userDAO.get(field, value));
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.trace("findby receive a invalid argument");
		}

		return users;
	}

	@Override
	public List<UrutaUser> where(String conditions) {
		throw new NotImplementedError();
	}
}
