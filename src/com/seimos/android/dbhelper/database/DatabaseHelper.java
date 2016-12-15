package com.seimos.android.dbhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seimos.android.dbhelper.util.Application;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static DatabaseHelper instance;
	private String[] initialQueries;
	private Patch[] patches;
	private static SQLiteDatabase database;

	public DatabaseHelper(Context context, String databaseName, String[] initialQueries, Patch[] patches) {
		super(context, databaseName, null, Application.getVersion(context));
		this.initialQueries = initialQueries;
		this.patches = patches;
		DatabaseHelper.instance = this;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (initialQueries != null && initialQueries.length > 0) {
			for (String query : initialQueries) {
				db.execSQL(query);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (patches != null && patches.length > 0) {
			for (int i = oldVersion; i < newVersion; i++) {
				Patch patch = patches[i];
				if (patch != null) {
					patch.apply(db);
				}
			}
		}
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (patches != null && patches.length > 0) {
			for (int i = newVersion - 1; i >= oldVersion; i--) {
				Patch patch = patches[i];
				if (patch != null) {
					patch.revert(db);
				}
			}
		}
	}

	public static SQLiteDatabase openForRead() {
		if (instance == null) {
			throw new IllegalArgumentException("None instance of DatabaseHelper found");
		} else if (database == null || !database.isOpen()) {
			database = instance.getReadableDatabase();
		}
		return database;
	}

	public static SQLiteDatabase openForWrite() {
		if (instance == null) {
			throw new IllegalArgumentException("None instance of DatabaseHelper found");
		} else if (database == null || !database.isOpen()) {
			database = instance.getWritableDatabase();
		}
		return database;
	}

	/**
	 * @author moesio @ gmail.com
	 * @date Dec 3, 2016 9:42:47 AM
	 * 
	 * http://www.greenmoonsoftware.com/2012/02/sqlite-schema-migration-in-android/
	 * 
	 */
	public static class Patch {

		private String[] forwardQueries;
		private String[] rewindQueries;

		public Patch(String[] forwardQueries, String[] rewindQueries) {
			this.forwardQueries = forwardQueries;
			this.rewindQueries = rewindQueries;
		}

		public void apply(SQLiteDatabase db) {
			for (String query : forwardQueries) {
				db.execSQL(query);
			}
		}

		public void revert(SQLiteDatabase db) {
			for (String query : rewindQueries) {
				db.execSQL(query);
			}
		}

	}
}
