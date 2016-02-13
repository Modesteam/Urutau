package com.modesteam.urutau.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.dao.UserDAO;
import com.modesteam.urutau.exception.DataBaseCorrupted;
import com.modesteam.urutau.model.User;

@RequestScoped
public class UserService {
	
	private final static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserDAO userDAO;
	
	public UserService() {
		this(null);
	}
	
	@Inject
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * See {@link UserDAO#create(User)}
	 */
	public void create(User user){
		userDAO.create(user);
	}
	
	/**
	 * Method to verify if exist a user with same email or same login
	 * @param user
	 * @return false if the verification fails
	 */
	public boolean canBeUsed(String attributeName, Object value) {
		boolean valueNotUsed = false;
		
		try{
			if(userDAO.get(attributeName, value) == null) {
				valueNotUsed = true;
			}
		} catch (NonUniqueResultException exception) {
			throw new DataBaseCorrupted(this.getClass().getSimpleName() 
					+ " returns twice " + attributeName + " equals");
		} 
		
		return valueNotUsed;
	}

	
	/**
	 * See {@link UserDAO#update(User)}
	 */
	public void update(User user) {
		userDAO.create(user);
	}
	
	/**
	 * See if an user exist
	 * 
	 * @param login
	 * @return
	 */
	public boolean existsUser(String login) {
		boolean userExistence;
		if(userDAO.get("login", login) != null) {
			userExistence = true;
		} else {
			userExistence = false;
		}
		return userExistence;
	}

	public User authenticate(String login, String password) {
		User user = userDAO.get("login", login);
		
		// if login exists
		if (user != null) {
			
			boolean correctPassword = user.getPassword().equals(password);
			// Get user with all attributes
			if(correctPassword) {
				
			} else {
				logger.info("The password is wrong.");
				user = null;
			}
			
		} else {
			logger.info("The login informed doesn't exist at the system");
		}
		
		return user;
	}
	
	/**
	 * Get user reference
	 *  
	 * @param userID identifier
	 * @return user logged, uses into userSession
	 */
	public User reloadFromDB(Long userID) {
		return userDAO.getReference(userID);
	}

}
