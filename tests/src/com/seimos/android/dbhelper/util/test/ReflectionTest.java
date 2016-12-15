package com.seimos.android.dbhelper.util.test;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.annotation.Id;
import com.seimos.android.dbhelper.database.BaseEntity;
import com.seimos.android.dbhelper.test.Something;
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
		assertEquals("getBool", Reflection.getGetter(fields[0]));
		assertEquals("getDate", Reflection.getGetter(fields[1]));
		assertEquals("getDoub", Reflection.getGetter(fields[2]));
		assertEquals("getId", Reflection.getGetter(fields[3]));
		assertEquals("getInteger", Reflection.getGetter(fields[4]));
		assertEquals("getName", Reflection.getGetter(fields[5]));
	}

	@Test
	public final void testGetGetterString() {
		assertEquals("getBool", Reflection.getGetter("bool"));
		assertEquals("getDate", Reflection.getGetter("date"));
		assertEquals("getDoub", Reflection.getGetter("doub"));
		assertEquals("getId", Reflection.getGetter("id"));
		assertEquals("getInteger", Reflection.getGetter("integer"));
		assertEquals("getName", Reflection.getGetter("name"));
	}

	@Test
	public final void testGetSetter() {
		assertEquals("setBool", Reflection.getSetter("bool"));
		assertEquals("setDate", Reflection.getSetter("date"));
		assertEquals("setDoub", Reflection.getSetter("doub"));
		assertEquals("setId", Reflection.getSetter("id"));
		assertEquals("setInteger", Reflection.getSetter("integer"));
		assertEquals("setName", Reflection.getSetter("name"));
	}

	@Test
	public final void testGetValue() {
		Date now = new Date();
		something.setBool(true);
		something.setDate(now);
		something.setDoub(2.);
		something.setId(1L);
		something.setInteger(2);
		something.setName("Foo");

		assertEquals(Boolean.TRUE, Reflection.getValue(something, "bool"));
		assertEquals(now, Reflection.getValue(something, "date"));
		assertEquals(2., Reflection.getValue(something, "doub"));
		assertEquals(1L, Reflection.getValue(something, "id"));
		assertEquals(2, Reflection.getValue(something, "integer"));
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

}
