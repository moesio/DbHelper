package com.seimos.android.dbhelper.database.test;

import java.util.Arrays;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import android.content.ContentValues;
import android.content.Context;
import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.database.BaseEntity;
import com.seimos.android.dbhelper.database.EntityHandler;
import com.seimos.android.dbhelper.database.Filter;
import com.seimos.android.dbhelper.exception.InvalidModifierException;

/**
 * @author moesio @ gmail.com
 * @date Dec 10, 2016 5:34:08 PM
 */
public class EntityHandlerTest extends AndroidTestCase {

	/**
		 * @author moesio @ gmail.com
		 * @date Dec 10, 2016 10:28:13 PM
		 */
	public class Something extends BaseEntity {

		private Long id;
		@SuppressWarnings("unused")
		private String name;
		@SuppressWarnings("unused")
		private Integer integer;
		@SuppressWarnings("unused")
		private Boolean bool;
		@SuppressWarnings("unused")
		private Date date;
		@SuppressWarnings("unused")
		private Double doub;

		@Override
		public String toString() {
			return "" + id;
		}

	}

	private Context context;
	private EntityHandler entityHandler;
	private Something something;

	@Override
	protected void setUp() throws Exception {
		try {
			new EntityHandler(null, null);
			fail("Neither context nor entity parameter can be null");
		} catch (NullPointerException e) {
		}

		context = getContext();
		entityHandler = new EntityHandler(context, Something.class);
		something = new Something();
	}

	@Test
	public final void testGetTableName() {
		assertFalse("Something".equals(entityHandler.getTableName()));
		assertEquals("something", entityHandler.getTableName());
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

		try {
			new EntityHandler(context, baseEntity.getClass()).getColumns();
			fail("Final fields should not be accepted for entities");
		} catch (InvalidModifierException e) {
		}
	}

	//	@Test
	//	public final void testCreateContentValues_for_an_not_defined_type() {
	//		BaseEntity baseEntity = new BaseEntity() {
	//			private Object a;
	//
	//			@Override
	//			public String toString() {
	//				return null;
	//			}
	//		};
	//
	//		
	//		try {
	//			new EntityHandler(context, baseEntity.getClass()).createContentValues(baseEntity);
	//			fail("Type not mapped should not be accepted for entities");
	//		} catch (RuntimeException e) {
	//		}
	//	}

	@Test
	public final void testGetColumns() {
		assertTrue(Arrays.equals(new String[] { "bool", "date", "doub", "id", "integer", "name" }, entityHandler.getColumns()));
	}

	@Test
	public final void testCreateContentValues() {
		something.bool = true;
		Date now = new Date();
		something.date = now;
		something.doub = 2.;
		something.id = 1L;
		something.integer = 2;
		something.name = "Foo";
		ContentValues contentValuesExpected = new ContentValues();
		contentValuesExpected.put("bool", true);
		contentValuesExpected.put("date", Filter.getStringValue(now));
		contentValuesExpected.put("doub", 2.);
		contentValuesExpected.put("id", 1L);
		contentValuesExpected.put("integer", 2);
		contentValuesExpected.put("name", "Foo");
		ContentValues contentValuesActual = entityHandler.createContentValues(something);
		assertEquals(contentValuesExpected, contentValuesActual);
	}

	@Test
	public final void testToDatabaseName() {
		assertEquals("abcdefghijklmnopqrstuvwxyz", entityHandler.toDatabaseName("abcdefghijklmnopqrstuvwxyz"));
		assertEquals("abcdefghijklmnopqrstuvwxyz", entityHandler.toDatabaseName("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
		assertEquals("abcdefghijklmnopqrstuvwxyz1234567890", entityHandler.toDatabaseName("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"));
		assertEquals("t3st3", entityHandler.toDatabaseName("t3st3"));
		assertEquals("t_st__", entityHandler.toDatabaseName("t#st@$"));
		assertEquals("t_s_t__", entityHandler.toDatabaseName("t#s t@$"));
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
