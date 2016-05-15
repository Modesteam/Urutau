package com.modesteam.urutau.service.persistence;

/**
 * Ordinate search settings
 */
public class OrderOption {
	private OrderEnum type;
	private String field;
	private String start;
	private String end;

	/**
	 * Create options only if have a field to based and sort option(ASC or DESC)
	 */
	public OrderOption(OrderEnum type, String field) {
		this.type = type;
		this.field = field;
	}

	/**
	 * This creates a order criteria in String format
	 */
	@Override
	public String toString() {
		String option = orderQuery();

		if (!start.equals(null) && !end.equals(null)) {
			option.concat(interval());
		}

		return option;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	private String orderQuery() {
		return " ORDER BY " + field + " " + type.name();
	}

	private String interval() {
		return " BETWEEN " + start + " AND " + end + " ";
	}
}
