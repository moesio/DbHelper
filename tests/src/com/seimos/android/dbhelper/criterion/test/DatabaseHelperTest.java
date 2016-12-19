package com.seimos.android.dbhelper.criterion.test;

import org.junit.Test;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.criterion.DatabaseHelper;

/**
 * @author moesio @ gmail.com
 * @date Dec 14, 2016 5:56:52 PM
 */
public class DatabaseHelperTest extends AndroidTestCase {

	private static DatabaseHelper databaseHelper;

	@Override
	protected void setUp() throws Exception {
		if (databaseHelper == null) {
			getContext().deleteDatabase("databaseHelperTest");
			databaseHelper = new DatabaseHelper(getContext(), "databaseHelperTest", null, null);
		}
	}

	@Test
	public final void testOnCreateSQLiteDatabase() {
		String[] initialQueries = { "CREATE TABLE foo (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR);" };
		getContext().deleteDatabase("entityHandlerTestOnCreate");
		new DatabaseHelper(getContext(), "entityHandlerTestOnCreate", initialQueries, null) {
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
	public final void testOpen() {
		// TODO Test it with not writable database, without space for example
		SQLiteDatabase database = DatabaseHelper.open();
		assertTrue(database.isOpen());
	}
}
