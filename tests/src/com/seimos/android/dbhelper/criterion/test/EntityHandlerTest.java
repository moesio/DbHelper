package com.seimos.android.dbhelper.criterion.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.criterion.BaseEntity;
import com.seimos.android.dbhelper.criterion.DatabaseHelper;
import com.seimos.android.dbhelper.criterion.EntityHandler;
import com.seimos.android.dbhelper.exception.InvalidModifierException;
import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Dec 10, 2016 5:34:08 PM
 */
public class EntityHandlerTest extends AndroidTestCase {

	private EntityHandler entityHandler;

	@Override
	protected void setUp() throws Exception {
		try {
			new EntityHandler(null, null);
			fail("Neiar context nor entity parameter can be null");
		} catch (NullPointerException e) {
		}

		entityHandler = new EntityHandler(getContext(), Something.class);

	}

	@Test
	public final void testGetColumns_with_final_fields_in_base_entity() throws InvalidModifierException {
		BaseEntity baseEntity = new BaseEntity() {
			@SuppressWarnings("unused")
			private final String foo = "";

			@Override
			public String toString() {
				return null;
			}
		};

		assertEquals(0, new EntityHandler(getContext(), baseEntity.getClass()).getColumns().length);
	}

	@Test
	public final void testCreateContentValues_for_an_not_defined_type() {
		final Object anObject = new Object() {
			@Override
			public String toString() {
				return "anything at all";
			}
		};
		BaseEntity baseEntity = new BaseEntity() {
			@SuppressWarnings("unused")
			private Object a = anObject;

			@Override
			public String toString() {
				return null;
			}
		};

		ContentValues expectedContentValues = new ContentValues();
		expectedContentValues.put("a", anObject.toString());

		ContentValues actualCreateContentValues = entityHandler.createContentValues(baseEntity);
		assertEquals(expectedContentValues, actualCreateContentValues);
	}

	@Test
	public final void testCreateEntityFromCursor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		getContext().deleteDatabase("entityHandlerTestCreateEntityFromCursor");
		DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), "entityHandlerTestCreateEntityFromCursor", Something.TABLE_CREATION_QUERY, null);
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		Something something = new Something().setaBoolean(true).setaDate(new Date()).setaDouble(0.5).setaInteger(2).setName("something");
		long insert = db.insert("something", null, entityHandler.createContentValues(something));
		if (insert != -1) {
			Cursor cursor = db.query("something", new String[] { "id", "name", "aInteger", "aBoolean", "aDate", "aDouble" }, null, null, null, null, null);
			cursor.moveToFirst();
			Something actual = (Something) entityHandler.createEntityFromCursor(cursor);
			assertEquals(something.getName(), actual.getName());
		} else {
			fail("Cannot insert");
		}
	}

	@Test
	public final void testExtract() {
		getContext().deleteDatabase("entityHandlerTestExtract");
		DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), "entityHandlerTestExtract",
				new String[] { "create table extract (name varchar, flag boolean, value integer);" }, null);
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		db.execSQL("insert into extract values ('John Doe', 'true', 1000)");
		Cursor cursor = db.query("extract", new String[] { "name", "flag", "value" }, null, null, null, null, null);
		try {
			entityHandler.extract(cursor).size();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
		}

	}

	@Test
	public final void testGetColumns() {
		Arrays.sort(Something.COLUMNS);
		assertTrue(Arrays.equals(Something.COLUMNS, entityHandler.getColumns()));
	}

	@Test
	public final void testCreateContentValues() {
		Date now = new Date();
		Something something = new Something();
		something.setaBoolean(true);
		something.setaDate(now);
		something.setaDouble(2.);
		something.setId(1L);
		something.setaInteger(2);
		something.setName("Foo");
		ContentValues expectedContentValues = new ContentValues();
		expectedContentValues.put("aBoolean", true);
		try {
			expectedContentValues.put("aDate", Reflection.getStringValue(Something.class, "aDate", now));
		} catch (NoSuchFieldException e1) {
		}
		expectedContentValues.put("aDouble", 2.);
		expectedContentValues.put("id", 1L);
		expectedContentValues.put("aInteger", 2);
		expectedContentValues.put("name", "Foo");
		ContentValues actualContentValues = entityHandler.createContentValues(something);
		Set<String> keySet = expectedContentValues.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			assertTrue(actualContentValues.containsKey(key));
			assertEquals(expectedContentValues.get(key), actualContentValues.get(key));
			iterator.remove();
		}
		assertEquals(0, keySet.size());
		try {
			entityHandler.createContentValues(null);
			fail("Cannot create content from null as entity");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	@Ignore
	public final void aExtract() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	@Ignore
	public final void sCreateEntityFromCursor() {
		fail("Not yet implemented"); // TODO
	}

}
