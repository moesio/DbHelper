package com.seimos.android.dbhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seimos.android.dbhelper.util.Application;

public class DatabaseHelper extends SQLiteOpenHelper {

	private Patch[] patches;

	public DatabaseHelper(Context context, String databaseName, Patch[] patches) {
		super(context, databaseName, null, Application.getVersion(context));
		this.patches = patches;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (Patch patch : patches) {
			patch.apply(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i < newVersion; i++) {
			patches[i].apply(db);
		}
	}

	/**
	 * @author moesio @ gmail.com
	 * @date Dec 3, 2016 9:42:47 AM
	 */
	public static class Patch {

		private String[] queries;

		public Patch(String[] queries) {
			this.queries = queries;
		}

		public void apply(SQLiteDatabase db) {
			for (String query : queries) {
				db.execSQL(query);
			}
		}

	}
}
