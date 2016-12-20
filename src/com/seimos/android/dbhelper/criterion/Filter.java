package com.seimos.android.dbhelper.criterion;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:16:38 PM
 */
public class Filter {

	private String column;
	private Restriction restriction;
	private String[] value;
	private OrderBy orderBy;

	public Filter(String column, Restriction restriction, String... value) {
		this.column = column;
		this.value = value;
		this.restriction = restriction;
	}

	public Filter(String column, OrderBy orderBy) {
		this.column = column;
		this.orderBy = orderBy;
	}

	public String getColumn() {
		return column;
	}

	public String[] getValue() {
		return value;
	}

	public String getWhere() {
		return new StringBuilder(getColumn()).append(restriction.getExpression()).toString();
	}

	public OrderBy getOrderBy() {
		return this.orderBy;
	}

}
