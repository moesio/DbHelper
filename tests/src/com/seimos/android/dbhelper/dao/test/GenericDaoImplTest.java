package com.seimos.android.dbhelper.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.persistence.DatabaseHelper;
import com.seimos.android.dbhelper.persistence.Filter;
import com.seimos.android.dbhelper.persistence.Restriction;
import com.seimos.android.dbhelper.persistence.test.Something;

/**
 * @author moesio @ gmail.com
 * @date Dec 12, 2016 10:05:50 PM
 */
public class GenericDaoImplTest extends AndroidTestCase {

	private SomethingDaoImpl dao;
	private Something something;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new SomethingDaoImpl(getContext());
		getContext().deleteDatabase("genericDaoTest");
		new DatabaseHelper(getContext(), "genericDaoTest", Something.TABLE_CREATION_QUERY, null);
		something = new Something().setaBoolean(true).setaDate(new Date()).setaDouble(0.5).setaInteger(2).setName("something");
	}
	
	@Test
	public final void testCreate() {
		assertTrue(dao.create(something));
		try {
			dao.create(null);
			fail("Must not accept null as argument for create");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public final void testRetrieve() {
		dao.create(something);
		assertEquals(new Something().setId(1L).toString(), dao.retrieve(1).toString());
	}

	@Test
	public final void testList() {
		for (int i = 0; i < 10; i++) {
			dao.create(something);
		}
		assertEquals(10, dao.list().size());
	}

	@Test
	public final void testFilter() {
		ArrayList<Filter> filtersList;
		Filter[] filters;
		
		filtersList = new ArrayList<Filter>();
		filters = new Filter[filtersList.size()];
		filtersList.toArray(filters);
		assertEquals(0, dao.filter(filters).size());
		
		something.setName("One");
		filtersList.add(new Filter("name", Restriction.EQ, "Two"));
		filters = new Filter[filtersList.size()];
		filtersList.toArray(filters);
		assertTrue(dao.filter(filters).isEmpty());
		
		String[] names = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
		for (int i = 0; i < 10; i++) {
			something = new Something().setName(names[i]).setaInteger(i).setaDate(new Date(2016, i + i, 1));
			dao.create(something);
		}
		assertEquals(10,dao.list().size());
		
		filtersList = new ArrayList<Filter>();
		filtersList.add(new Filter("name", Restriction.LIKE, "T%"));
		filters = new Filter[filtersList.size()];
		filtersList.toArray(filters);
		List<Something> result = dao.filter(filters);
		assertEquals(3, result.size());
		
		filtersList = new ArrayList<Filter>();
		filtersList.add(new Filter("aInteger", Restriction.BETWEEN, "2", "3"));
		filters = new Filter[filtersList.size()];
		filtersList.toArray(filters);
		result = dao.filter(filters);
		assertEquals(2, result.size());
		assertEquals("Three", result.get(0).getName());
		assertEquals("Four", result.get(1).getName());
		
		filtersList.add(new Filter("name", Restriction.LIKE, "T%"));
		filters = new Filter[filtersList.size()];
		filtersList.toArray(filters);
		result = dao.filter(filters);
		assertEquals("Three", result.get(0).getName());
	}

	@Test
	public final void testUpdate() {
		dao.create(something);
		Something retrieve = dao.retrieve(1);
		retrieve.setName("Something else");
		dao.update(retrieve);
		assertEquals("Something else", dao.retrieve(1).getName());
	}

	@Test
	public final void testDelete() {
		dao.create(something);
		dao.delete(1);
		assertEquals(0, dao.list().size());
	}

}
