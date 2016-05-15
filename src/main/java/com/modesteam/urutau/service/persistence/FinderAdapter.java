package com.modesteam.urutau.service.persistence;

import java.util.List;

import javax.enterprise.context.Dependent;

/**
 * This class uses the Adapter design pattern. The Order object needs a Finder
 * to perform your search through of where method. So, if the same class,
 * implements the both, this adapter will create an formatted condition
 * parameter to ask results in order.
 */
@Dependent
public class FinderAdapter<Entity> {

	private Finder<Entity> finder;

	private OrderOption option;

	/**
	 * This Adapter not makes sense without a {@link Finder} and a
	 * {@link OrderOption}.
	 */
	public FinderAdapter(Finder<Entity> finder, OrderOption option) {
		this.option = option;
		this.finder = finder;
	}

	/**
	 * This method will configure condition to ordination, and pass by parameter
	 * to {@link Finder#where(String)} method.
	 * 
	 * @param conditions
	 *            of search
	 * @return {@link List} entities Entity
	 */
	public List<Entity> where(final String condition) {
		final String fullCondition = condition.concat(option.toString());

		return finder.where(fullCondition);
	}
}
