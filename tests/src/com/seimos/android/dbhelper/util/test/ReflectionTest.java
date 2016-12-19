package com.seimos.android.dbhelper.util.test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.criterion.BaseEntity;
import com.seimos.android.dbhelper.criterion.test.Something;
import com.seimos.android.dbhelper.persistence.Id;
import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Dec 11, 2016 3:37:59 PM
 */
public class ReflectionTest extends AndroidTestCase {

	private Something something;

	@Override
	protected void setUp() throws Exception {
		something = new Something();
	}

	@Test
	public final void testIsEntityObject() {
		assertTrue(Reflection.isEntity(something));
	}

	@Test
	public final void testIsEntityClassOfQ() {
		assertFalse(Reflection.isEntity(Object.class));
		assertTrue(Reflection.isEntity(Something.class));
	}

	@Test
	public final void testGetGetterField() {
		Field[] fields = Reflection.getInnerDeclaredFields(Something.class);
		int i = 0;
		assertEquals("getaBoolean", Reflection.getGetter(fields[i++]));
		assertEquals("getaDate", Reflection.getGetter(fields[i++]));
		assertEquals("getaDouble", Reflection.getGetter(fields[i++]));
		assertEquals("getaInteger", Reflection.getGetter(fields[i++]));
		assertEquals("getDateOnly", Reflection.getGetter(fields[i++]));
		assertEquals("getFullDateTime", Reflection.getGetter(fields[i++]));
		assertEquals("getId", Reflection.getGetter(fields[i++]));
		assertEquals("getName", Reflection.getGetter(fields[i++]));
		assertEquals("getTimeOnly", Reflection.getGetter(fields[i++]));
	}

	@Test
	public final void testGetGetterString() {
		assertEquals("getaBoolean", Reflection.getGetter("aBoolean"));
		assertEquals("getaDate", Reflection.getGetter("aDate"));
		assertEquals("getaDouble", Reflection.getGetter("aDouble"));
		assertEquals("getaInteger", Reflection.getGetter("aInteger"));
		assertEquals("getId", Reflection.getGetter("id"));
		assertEquals("getName", Reflection.getGetter("name"));
	}

	@Test
	public final void testGetSetter() {
		assertEquals("setaBoolean", Reflection.getSetter("aBoolean"));
		assertEquals("setaDate", Reflection.getSetter("aDate"));
		assertEquals("setaDouble", Reflection.getSetter("aDouble"));
		assertEquals("setaInteger", Reflection.getSetter("aInteger"));
		assertEquals("setId", Reflection.getSetter("id"));
		assertEquals("setName", Reflection.getSetter("name"));
	}

	@Test
	public final void testGetValue() {
		Date now = new Date();
		something.setaBoolean(true);
		something.setaDate(now);
		something.setaDouble(2.);
		something.setId(1L);
		something.setaInteger(2);
		something.setName("Foo");

		assertEquals(Boolean.TRUE, Reflection.getValue(something, "aBoolean"));
		assertEquals(now, Reflection.getValue(something, "aDate"));
		assertEquals(2., Reflection.getValue(something, "aDouble"));
		assertEquals(1L, Reflection.getValue(something, "id"));
		assertEquals(2, Reflection.getValue(something, "aInteger"));
		assertEquals("Foo", Reflection.getValue(something, "name"));
	}

	@Test
	public final void testGetIdField() {
		BaseEntity baseEntity = new BaseEntity() {
			@Id
			private Integer id;

			@Override
			public String toString() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		assertNull(Reflection.getIdField(Something.class));
		try {
			assertEquals(baseEntity.getClass().getDeclaredField("id"), Reflection.getIdField(baseEntity.getClass()));
		} catch (NoSuchFieldException e) {
			fail("No such field");
		}
	}

	//	@Test
	//	public final void testGetInnerDeclaredFields() {
	//		Field[] fields = Reflection.getInnerDeclaredFields(Something.class);
	//	}

	@Test
	public final void testSetValue() {
		Something something = new Something();
		try {
			Reflection.setValue(something, "a", 50);
			fail("Must not accept an invalid field to set");
		} catch (IllegalArgumentException e) {
		}
		Reflection.setValue(something, "name", "John Doe");
		assertEquals("John Doe", something.getName());
		assertNull(something.getaBoolean());

		class Foo {
			private String bar;

			public String getBar() {
				return bar;
			}

		}

		Foo foo = new Foo();
		Reflection.setValue(foo, "bar", "bar");
		assertEquals("bar", foo.getBar());
	}

	@Test
	public final void testGetDateFormat() {
		Field[] fields = Reflection.getInnerDeclaredFields(Something.class);
		for (Field field : fields) {
			if (field.getName().equals("dateOnly")) {
				assertEquals("2016-12-17", Reflection.getDateFormat(field).format(new GregorianCalendar(2016, 11, 17).getTime()));
			} else if (field.getName().equals("timeOnly")) {
				assertEquals("23:28:30", Reflection.getDateFormat(field).format(new GregorianCalendar(2016, 11, 17, 23, 28, 30).getTime()));
			} else if (field.getName().equals("timeOnly")) {
				assertEquals("2016-12-17 23:28:30", Reflection.getDateFormat(field).format(new GregorianCalendar(2016, 11, 17, 23, 28, 30).getTime()));
			} else if (field.getName().equals("aDate")) {
				assertEquals("2016-12-17", Reflection.getDateFormat(field).format(new GregorianCalendar(2016, 11, 17).getTime()));
			}
		}
	}
}
