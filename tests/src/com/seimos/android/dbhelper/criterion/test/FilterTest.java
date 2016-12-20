package com.seimos.android.dbhelper.criterion.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.criterion.Filter;
import com.seimos.android.dbhelper.criterion.Restriction;

/**
 * @author moesio @ gmail.com
 * @date Dec 17, 2016 9:54:33 PM
 */
public class FilterTest extends AndroidTestCase {

	@Test
	public final void testFilterStringObjectRestriction() {
		new Filter("column", new Object(), Restriction.GE);
	}

	@Test
	public final void testGetStringValue() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testFilterOrderBy() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetColumn() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetValue() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetRestriction() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetClausule() {
//		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetOrderBy() {
//		fail("Not yet implemented"); // TODO
	}

}
