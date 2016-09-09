package com.modesteam.urutau.service.persistence;

/**
 * <p>
 * Performs filters to get a customizable result List. Therefore, this interface
 * only makes sense be used when your implementation already is a {@link Finder}
 * type. So, when you need use this, your Service object should call something
 * like:
 * <p>
 * 
 * <p>
 * <i>objectService.asc(sortField).find().where(conditionString);</i>
 * </p>
 * 
 * <p>
 * Order uses chaining method to configure you criteria of search, until you use
 * {@link Order#find()} method, that should return a {@link FinderAdapter}, that
 * have only a {@link FinderAdapter#where(String)} method, to you describe the
 * rest of your search
 * </p>
 * 
 */
public interface Order<Entity> {
	/**
	 * Can be a need sort your results of ascending and descending, this method
	 * configures ascending like option. This is a default form of search, but
	 * is cool that you call this to keep code more explanatory.
	 * 
	 * @param field
	 *            is a criteria element to ordering
	 * @return the same object that calls this method
	 */
	public Order<Entity> asc(String field);

	/**
	 * <p>
	 * See {@link Order#asc(String)}.
	 * </p>
	 * 
	 * <p>
	 * This method configures descending like option.
	 * </p>
	 * 
	 * @param field
	 *            is a criteria element to ordering
	 * @return the same object that calls this method
	 */
	public Order<Entity> desc(String field);

	/**
	 * Set the interval of search, very useful for paginations
	 * 
	 * @param first
	 *            is a lower limit
	 * @param last
	 *            is a upper limit
	 * @return the same object that calls this method
	 */
	public Order<Entity> between(Long first, Long last);

	/**
	 * This method should create a {@link FinderAdapter} with
	 * {@link OrderOption} and after clean this options.
	 * 
	 * @return a new instance of {@link FinderAdapter}
	 */
	public FinderAdapter<Entity> find();
}
