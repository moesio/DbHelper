package com.seimos.android.dbhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seimos.android.dbhelper.util.Application;

public class DatabaseHelper extends SQLiteOpenHelper {

	private String[] createDbQueries;

	public DatabaseHelper(Context context, String databaseName, String[] createDbQueries) {
		super(context, databaseName, null, Application.getVersion(context));
		this.createDbQueries = createDbQueries;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (String sql : createDbQueries) {
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO
		//		String[] versions = context.getResources().getStringArray(upgradeSqlStringArray);
		//		for (int i = oldVersion; i < newVersion; i++) {
		//			String[] statements = versions[i - 1].split(";");
		//			for (String statement : statements) {
		//				db.execSQL(statement);
		//			}
		//		}
	}

}
