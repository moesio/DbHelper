package com.seimos.android.dbhelper.criterion;

/**
 * @author moesio @ gmail.com
 * @date Aug 3, 2015 7:17:02 PM
 */
public enum Restriction {
	GE {
		@Override
		public String getOperand() {
			return ">=";
		}
	};

	public abstract String getOperand();
}
