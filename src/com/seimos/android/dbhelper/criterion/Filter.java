package com.seimos.android.dbhelper.criterion;

import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:16:38 PM
 */
public class Filter {

	private String column;
	private Restriction restriction;
	private String value;
	private OrderBy orderBy;

	public Filter(String column, Object value, Restriction restriction) {
		this.column = column;
		this.value = Reflection.getStringValue(value);
		this.restriction = restriction;
	}
	
	public Filter(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	public String getColumn() {
		return column;
	}

	public String getValue() {
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
