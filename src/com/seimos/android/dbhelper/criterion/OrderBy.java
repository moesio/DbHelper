package com.seimos.android.dbhelper.criterion;

/**
 * @author moesio @ gmail.com
 * @date Aug 7, 2015 10:41:25 PM
 */
public class OrderBy {

	public enum Order {
		ASC, DESC
	}

	private String columnName;
	private Order order;

	public OrderBy(String columnName) {
		this.columnName = columnName;
		this.order = Order.ASC;
	}

	public OrderBy(String columnName, Order order) {
		this.columnName = columnName;
		this.order = order;
	}

	public String getColumnName() {
		return columnName;
	}

	public Order getOrder() {
		return order;
	}

	@Override
	public String toString() {
		return new StringBuilder(columnName).toString();//.append(" ").append(order.toString()).toString();
	}
}
