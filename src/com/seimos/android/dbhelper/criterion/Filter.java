package com.seimos.android.dbhelper.criterion;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:16:38 PM
 */
public class Filter {

	private String column;
	private Restriction restriction;
	private Object value;
	private OrderBy orderBy;

	public Filter(String column, Object value, Restriction restriction) {
		this.column = column;
		this.value = value;
		this.restriction = restriction;
	}

	public Filter(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public String getColumn() {
		return column;
	}

	public Object getValue() {
		return value;
	}

	public Restriction getRestriction() {
		return restriction;
	}

	public String getClausule() {
		return new StringBuilder(getColumn()).append(restriction.getOperand()).append("?").toString();
	}

	public OrderBy getOrderBy() {
		return this.orderBy;
	}

}
