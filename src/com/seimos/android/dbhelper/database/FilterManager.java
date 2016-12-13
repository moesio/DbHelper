package com.seimos.android.dbhelper.database;

import java.util.ArrayList;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:33:50 PM
 */
public class FilterManager {

	private Filter[] filters;
	private String selection;
	private String[] args;
	private String orderBy;

	public FilterManager(Filter[] filters) {
		this.filters = filters;
		split();
	}

	private void split() {
		if (filters != null && filters.length > 0) {
			ArrayList<String> argsBuilder = new ArrayList<String>();

			StringBuilder selectionBuilder = new StringBuilder();
			for (int i = 0; i < filters.length; i++) {
				if (filters[i].getOrderBy() != null) {
					this.orderBy = filters[i].getOrderBy().toString();
				} else {
					selectionBuilder.append(filters[i].getClausule());
					String arg = Filter.getStringValue(filters[i].getValue());
					if (arg != null) {
						argsBuilder.add(arg);
					}
					
					if (i < (filters.length - 1)) {
						selectionBuilder.append(", ");
					}
				}
			}
			if (selectionBuilder.length() > 0) {
				this.selection = selectionBuilder.toString();
			}
			if (argsBuilder.size() > 0) {
				args = new String[argsBuilder.size()];
				argsBuilder.toArray(args);
			}
		}
	}

	public String getSelection() {
		return selection;
	}

	public String[] getArgs() {
		return args;
	}

	public String getOrderBy() {
		return orderBy;
	}
}
