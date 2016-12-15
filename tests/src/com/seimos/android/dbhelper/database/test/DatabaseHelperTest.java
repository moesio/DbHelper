package com.seimos.android.dbhelper.database.test;

import org.junit.Test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.database.DatabaseHelper;
import com.seimos.android.dbhelper.database.DatabaseHelper.Patch;
import com.seimos.android.dbhelper.util.Application;

/**
 * @author moesio @ gmail.com
 * @date Dec 14, 2016 5:56:52 PM
 */
public class DatabaseHelperTest extends AndroidTestCase {

	private static DatabaseHelper databaseHelper;

	@Override
	protected void setUp() throws Exception {
		if (databaseHelper == null) {
			try {
				DatabaseHelper.openForRead();
				fail("DatabaseHelper must be instantiated before call openForRead");
			} catch (IllegalArgumentException e) {
			}

			databaseHelper = new DatabaseHelper(getContext(), null, null, null);
		}
	}

	@Test
	public final void testOnCreateSQLiteDatabase() {
		String[] initialQueries = { "CREATE TABLE foo (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR);" };
		new DatabaseHelper(getContext(), null, initialQueries, null) {
			@Override
			public void onCreate(SQLiteDatabase db) {
				super.onCreate(db);
				
				db.execSQL("select * from foo;");
			}
		};
	}

	@Test
	public final void testOnUpgradeSQLiteDatabaseIntInt() {
		// TODO
	}

	@Test
	public final void testOnDowngradeSQLiteDatabaseIntInt() {
		// TODO
	}

	@Test
	public final void testOpenForRead() {
		SQLiteDatabase database = DatabaseHelper.openForRead();
		assertTrue(database.isOpen());
	}

	@Test
	public final void testOpenForWrite() {
		SQLiteDatabase database = DatabaseHelper.openForWrite();
		assertFalse(database.isReadOnly());
	}

}
