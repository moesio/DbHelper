package com.seimos.android.dbhelper.persistence;

import com.seimos.android.dbhelper.exception.InvalidNumberOfArguments;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:16:38 PM
 */
public class Filter {

	private String column;
	private Restriction restriction;
	private String[] values;
	private OrderBy orderBy;

	public Filter(String column, Restriction restriction, String... values) {
		if (column == null || restriction == null) {
			throw new IllegalArgumentException("Neither column nor restriction can not be null");
		}
		String expression = restriction.getExpression();
		boolean go = false;
		if (expression.contains("?")) {
			if (restriction.equals(Restriction.IN) || restriction.equals(Restriction.NOTIN)) {
				if (values != null) {
					if (values.length > 0) {
						go = true;
					}
				}
			} else {
				if (values != null) {
					if (expression.split("\\?").length == values.length) {
						go = true;
					}
				}
			}
		} else {
			if (values == null || values.length == 0) {
				go = true;
			}
		}

		if (!go) {
			throw new InvalidNumberOfArguments("Number of arguments does not match to " + restriction.toString());
		}

		//		if ((expression.contains("?") && (!restriction.equals(Restriction.IN) && !restriction.equals(Restriction.NOTIN) && (values == null || expression.split("\\?").length != values.length) )//
		//				|| (restriction.equals(Restriction.IN) || restriction.equals(Restriction.NOTIN)) 
		//				|| (!expression.contains("?") && (values != null && values.length != 0))) {
		//			throw new InvalidNumberOfArguments("Number of arguments does not match to " + restriction.toString());
		//		}
		this.column = column;
		this.values = values;
		this.restriction = restriction;
	}

	public Filter(String column, OrderBy orderBy) {
		if (column == null || restriction == null) {
			throw new IllegalArgumentException("Neither column nor restriction can not be null");
		}
		this.column = column;
		this.orderBy = orderBy;
	}

	public String getColumn() {
		return column;
	}

	public String[] getValues() {
		return values;
	}

	public String getWhere() {
		String expression = restriction.getExpression();
		switch (restriction) {
		case IN:
			for (int i = 1; i < values.length; i++) {
				expression = expression.replaceAll("\\?\\)", "\\?, \\?\\)");
			}
			break;
		case EQPROPERTY:
		case NEPROPERTY:
		case LTPROPERTY:
		case LEPROPERTY:
		case GTPROPERTY:
		case GEPROPERTY:
			expression = expression.replaceAll("\\?", values[0]);
			values = null;
			break;
		default:
			break;
		}
		return new StringBuilder(getColumn()).append(expression).toString();
	}

	public OrderBy getOrderBy() {
		return this.orderBy;
	}

}
