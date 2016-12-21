package com.seimos.android.dbhelper.persistence.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.exception.InvalidNumberOfArguments;
import com.seimos.android.dbhelper.persistence.Filter;
import com.seimos.android.dbhelper.persistence.Restriction;

/**
 * @author moesio @ gmail.com
 * @date Dec 21, 2016 12:47:32 AM
 */
public class FilterTest extends AndroidTestCase {

	@Test
	public final void testFilterStringRestrictionStringArray() {
		try {
			new Filter("column", Restriction.BETWEEN);
			fail("Invalid number of arguments");
		} catch (InvalidNumberOfArguments e) {
		}

		try {
			new Filter(null, null);
			fail("Arguments must not be null");
		} catch (IllegalArgumentException e) {
		}

		try {
			new Filter("column", Restriction.IN);
			fail("Must not accept null arguments");
		} catch (InvalidNumberOfArguments e) {
		}

		Filter filter;

		filter = new Filter("column", Restriction.IN, "foo");
		assertEquals("column in (?)", filter.getWhere());

		filter = new Filter("column", Restriction.IN, "foo", "other", "something");
		assertEquals("column in (?, ?, ?)", filter.getWhere());

		filter = new Filter("column", Restriction.EQPROPERTY, "other");
		assertEquals("column = other", filter.getWhere());

	}

	//	/**
	//	 * Test method for {@link com.seimos.android.dbhelper.persistence.Filter#Filter(java.lang.String, com.seimos.android.dbhelper.persistence.OrderBy)}.
	//	 */
	//	@Test
	//	public final void testFilterStringOrderBy() {
	//		fail("Not yet implemented"); // TODO
	//	}
	//
	//	/**
	//	 * Test method for {@link com.seimos.android.dbhelper.persistence.Filter#getColumn()}.
	//	 */
	//	@Test
	//	public final void testGetColumn() {
	//		fail("Not yet implemented"); // TODO
	//	}
	//
	//	/**
	//	 * Test method for {@link com.seimos.android.dbhelper.persistence.Filter#getValues()}.
	//	 */
	//	@Test
	//	public final void testGetValues() {
	//		fail("Not yet implemented"); // TODO
	//	}
	//
	//	/**
	//	 * Test method for {@link com.seimos.android.dbhelper.persistence.Filter#getWhere()}.
	//	 */
	//	@Test
	//	public final void testGetWhere() {
	//		fail("Not yet implemented"); // TODO
	//	}
	//
	//	/**
	//	 * Test method for {@link com.seimos.android.dbhelper.persistence.Filter#getOrderBy()}.
	//	 */
	//	@Test
	//	public final void testGetOrderBy() {
	//		fail("Not yet implemented"); // TODO
	//	}

}
