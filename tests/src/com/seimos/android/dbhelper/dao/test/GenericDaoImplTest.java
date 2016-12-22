package com.seimos.android.dbhelper.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.exception.InvalidNumberOfArguments;
import com.seimos.android.dbhelper.exception.NoIdentifierSetted;
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
		assertTrue(dao.create(something) > 0);
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
		ArrayList<Filter> filters;
		List<Something> result;

		filters = new ArrayList<Filter>();
		assertEquals(0, dao.filter(filters).size());

		something.setName("One");
		filters.add(new Filter("name", Restriction.EQ, "Two"));
		assertTrue(dao.filter(filters).isEmpty());

		String[] names = { "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten" };
		for (int i = 0; i < 10; i++) {
			something = new Something().setName(names[i]).setaInteger(i).setaDate(new Date(2016, i + i, 1));
			dao.create(something);
		}
		assertEquals(10, dao.list().size());

		filters = new ArrayList<Filter>();
		filters.add(new Filter("id", Restriction.IN, "1", "3", "5", "7", "9"));
		result = dao.filter(filters);
		assertEquals("One", result.get(0).getName());
		assertEquals("Three", result.get(1).getName());
		assertEquals("Five", result.get(2).getName());
		assertEquals("Seven", result.get(3).getName());
		assertEquals("Nine", result.get(4).getName());

		filters = new ArrayList<Filter>();
		try {
			filters.add(new Filter("id", Restriction.IN));
			fail("Must no accept empty values for Restriction.IN");
		} catch (InvalidNumberOfArguments e) {
		}

		filters = new ArrayList<Filter>();
		filters.add(new Filter("name", Restriction.LIKE, "T%"));
		result = dao.filter(filters);
		assertEquals(3, result.size());

		filters.add(new Filter("name", Restriction.LIKE, "th%"));
		result = dao.filter(filters);
		assertEquals("Three", result.get(0).getName());

		filters = new ArrayList<Filter>();
		filters.add(new Filter("aInteger", Restriction.BETWEEN, "2", "3"));
		result = dao.filter(filters);
		assertEquals(2, result.size());
		assertEquals("Three", result.get(0).getName());
		assertEquals("Four", result.get(1).getName());

		filters.add(new Filter("name", Restriction.LIKE, "T%"));
		result = dao.filter(filters);
		assertEquals("Three", result.get(0).getName());

		something = new Something().setId(1L).setName("1").setaInteger(1).setaDate(null);
		dao.update(something);
		filters = new ArrayList<Filter>();
		filters.add(new Filter("name", Restriction.EQPROPERTY, "aInteger"));
		result = dao.filter(filters);
		assertEquals("1", result.get(0).getName());

		filters = new ArrayList<Filter>();
		try {
			filters.add(new Filter("aDate", Restriction.ISNULL, "aInteger"));
			fail("Must not accept invalid number of arguments");
		} catch (InvalidNumberOfArguments e) {
		}
		
		filters.add(new Filter("aDate", Restriction.ISNULL));
		result = dao.filter(filters);
		assertEquals("1", dao.retrieve(1).getName());

	}

	@Test
	public final void testUpdate() {
		dao.create(something);
		Something retrieve = dao.retrieve(1);
		retrieve.setName("Something else");
		dao.update(retrieve);
		assertEquals("Something else", dao.retrieve(1).getName());

		retrieve.setaDate(null);
		dao.update(retrieve);
		assertNull(dao.retrieve(1).getaDate());

		retrieve.setId(null);
		try {
			dao.update(retrieve);
			fail("Must not accept entity without id setted for retrieving");
		} catch (NoIdentifierSetted e) {
		}

	}

	@Test
	public final void testDelete() {
		dao.create(something);
		dao.delete(1);
		assertEquals(0, dao.list().size());
	}

}
