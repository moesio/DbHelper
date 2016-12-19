package com.seimos.android.dbhelper.dao.test;

import java.util.Date;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.criterion.DatabaseHelper;
import com.seimos.android.dbhelper.criterion.test.Something;

/**
 * @author moesio @ gmail.com
 * @date Dec 12, 2016 10:05:50 PM
 */
public class GenericDaoImplTest extends AndroidTestCase {

	@Test
	public final void testCreate() {
		SomethingDaoImpl dao = new SomethingDaoImpl(getContext());
		getContext().deleteDatabase("genericDaoTest");
		new DatabaseHelper(getContext(), "genericDaoTest", Something.TABLE_CREATION_QUERY, null);
		Something something = new Something().setaBoolean(true).setaDate(new Date()).setaDouble(0.5).setaInteger(2).setName("something");
		assertTrue(dao.create(something));
		try {
			dao.create(null);
			fail("Must not accept null as argument for create");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public final void testRetrieve() {
	}

	@Test
	public final void testList() {
	}

	@Test
	public final void testFilter() {
	}

	@Test
	public final void testUpdate() {
	}

	@Test
	public final void testDelete() {
	}

}
