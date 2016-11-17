package com.seimos.android.dbhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil {

	private static DatabaseHelper databaseHelper;
	private static SQLiteDatabase database;

	public static void instantiateDb(DatabaseHelper helper) {
		databaseHelper = helper;
	}

	public static SQLiteDatabase openForRead(Context context) {
		if (databaseHelper == null) {
			throw new IllegalArgumentException("Instantiate database with DatabaseUtil.instantiateDb(DatabaseHelper)");
		}
		if (database == null || !database.isOpen()) {
			database = databaseHelper.getReadableDatabase();
		}
		return database;
	}

	public static SQLiteDatabase openForWrite(Context context) {
		if (databaseHelper == null) {
			throw new IllegalArgumentException("Instantiate database with DatabaseUtil.instantiateDb(DatabaseHelper)");
		}
		if (database == null || !database.isOpen()) {
			database = databaseHelper.getWritableDatabase();
		}
		return database;
	}

	public static void close() {
		if (databaseHelper != null) {
			databaseHelper.close();
		}
	}
}
