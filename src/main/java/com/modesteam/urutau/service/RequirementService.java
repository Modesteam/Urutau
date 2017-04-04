package com.modesteam.urutau.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.modesteam.urutau.UserSession;
import com.modesteam.urutau.dao.RequirementDAO;
import com.modesteam.urutau.dao.impl.GenericDAO;
import com.modesteam.urutau.exception.NotImplementedError;
import com.modesteam.urutau.model.Requirement;
import com.modesteam.urutau.model.UrutaUser;
import com.modesteam.urutau.service.persistence.DuplicateSortException;
import com.modesteam.urutau.service.persistence.Finder;
import com.modesteam.urutau.service.persistence.FinderAdapter;
import com.modesteam.urutau.service.persistence.Order;
import com.modesteam.urutau.service.persistence.OrderEnum;
import com.modesteam.urutau.service.persistence.OrderOption;
import com.modesteam.urutau.service.persistence.Persistence;

public class RequirementService
		implements Persistence<Requirement>, Finder<Requirement>, Order<Requirement> {
	private static final Logger logger = LoggerFactory.getLogger(RequirementService.class);

	private final RequirementDAO requirementDAO;
	private final UserSession userSession;

	private OrderOption orderOption;

	/**
	 * @deprecated CDI only
	 */
	public RequirementService() {
		this(null, null);
	}

	@Inject
	public RequirementService(RequirementDAO requirementDAO, UserSession session) {
		this.requirementDAO = requirementDAO;
		this.userSession = session;
	}

	/**
	 * Returns a requirement caught by title that have a certain id
	 * 
	 * @param id
	 *            unique
	 * @param title
	 *            name of Requirement, an usual identifier, but not unique
	 * @return a requirement
	 */
	public Requirement getBy(Long id, String title) {
		Requirement requirement = find(id);

		if (requirement.getTitle() == title) {
			throw new IllegalArgumentException("This title not matches");
		}

		return requirement;
	}

	@Override
	public void reload(Requirement entity) {
		// TODO Auto-generated method stub
		throw new NotImplementedError();
	}

	@Override
	public Requirement update(Requirement entity) {
        logger.info("Starting the function modifyRequirement");
        
        Requirement entityManaged = find(entity.getId());

        // Setting the current date to last modification
        Calendar lastModificationDate = getCurrentDate();
        entityManaged.setLastModificationDate(lastModificationDate);

        // Setting the last user to modify
        UrutaUser loggedUser = userSession.getUserLogged();
        entityManaged.setLastModificationAuthor(loggedUser);
        
        entityManaged.setTitle(entity.getTitle());
        entityManaged.setDescription(entity.getDescription());
        
		return entityManaged;
	}

	@Override
	public void save(Requirement entity) {
		requirementDAO.create(entity);
	}

	/**
	 * See {@link GenericDAO#destroy(Object)}
	 * 
	 * @return true if delete was complete, without errors
	 */
	@Override
	public void delete(Requirement requirement) {
		if (requirement != null) {
			requirementDAO.destroy(requirement);
		} else {
			throw new IllegalArgumentException("This requirement not exist");
		}
	}

	@Override
	public boolean exists(Long id) {
		logger.debug("Verifying the requirement existence in database.");

		Requirement requirement = requirementDAO.find(id);

		if (requirement == null) {
			logger.debug("The requirement is null");
		} else {
			logger.debug("The requirement isn't null");
		}

		return requirement != null;
	}

	@Override
	public Requirement find(Long id) {
		return requirementDAO.find(id);
	}

	@Override
	public Requirement findBy(String field, Object value) {
		Requirement requirement = null;

		try {
			requirement = requirementDAO.get(field, value).get(0);
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.trace("findby receive a invalid argument");
		}

		return requirement;
	}

	@Override
	public List<Requirement> where(String conditions) {
		String sql = "SELECT requirement FROM " + Requirement.class.getName()
				+ " requirement WHERE ";

		sql = sql.concat(conditions);

		return requirementDAO.findUsing(sql);
	}

	@Override
	public Order<Requirement> asc(String field) {
		if (orderOption == null) {
			this.orderOption = new OrderOption(OrderEnum.ASC, field);
		} else {
			throw new DuplicateSortException(
					"Option sort should be configured one time, you probably is calling"
							+ " something like desc(field).asc(field).");
		}
		return this;
	}

	@Override
	public Order<Requirement> desc(String field) {
		if (orderOption == null) {
			this.orderOption = new OrderOption(OrderEnum.DESC, field);
		} else {
			throw new DuplicateSortException(
					"Option sort should be configured one time, you probably is calling"
							+ " something like desc(field).asc(field).");
		}
		return this;
	}

	@Override
	public Order<Requirement> between(Long first, Long last) {
		this.orderOption.setStart(first.toString());
		this.orderOption.setEnd(last.toString());
		return this;
	}

	@Override
	public FinderAdapter<Requirement> find() {
		FinderAdapter<Requirement> adapter = new FinderAdapter<Requirement>(this, orderOption);
		// clean orderOption to others
		orderOption = null;

		return adapter;
	}

    /**
     * Get an instance of current date through of {@link Calendar}
     * 
     * @return current date
     */
    private Calendar getCurrentDate() {
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        return calendar;
    }
}
