package com.seimos.android.dbhelper.dao.test;

import java.util.Date;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.database.DatabaseHelper;
import com.seimos.android.dbhelper.database.test.Something;

/**
 * @author moesio @ gmail.com
 * @date Dec 12, 2016 10:05:50 PM
 */
public class GenericDaoImplTest extends AndroidTestCase {

	private SomethingDaoImpl dao;

	public void setUp() {
		dao = new SomethingDaoImpl(getContext());
		new DatabaseHelper(getContext(), null, Something.TABLE_CREATION_QUERY, null);
	}

	//	Long id;
	//	String name;
	//	Integer integer;
	//	Boolean bool;
	//	Date date;
	//	Double doub;

	@Test
	public final void testCreate() {
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
