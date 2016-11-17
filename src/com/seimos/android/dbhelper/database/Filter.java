package com.seimos.android.dbhelper.database;

import java.util.Date;

import android.text.format.DateFormat;

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
		this.value = getStringValue(value);
		this.restriction = restriction;
	}
	
	public static String getStringValue(Object value) {
		if (value != null) {
			return (value.getClass() == Date.class) ? ((String) DateFormat.format("yyyy-MM-dd", (Date) value)) : (value.toString());
		} else {
			return "";
		}
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
