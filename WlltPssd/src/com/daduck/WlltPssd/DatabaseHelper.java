package com.daduck.WlltPssd;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
@Singleton
public  class DatabaseHelper extends SQLiteOpenHelper implements DatabaseHelperI{
	@Inject protected static Provider<Context> contextProvider;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = "create table pssds (_id integer primary key autoincrement, "
			+ "name text not null, psswd text not null, body text not null);";
	private static final String DATABASE_CREATE2 = "create table user (_id integer primary key autoincrement, "
			+ "psswd text not null, quest text not null, answe text not null,"
			+ "less integer);";
	/**
	 * Database information
	 */

	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 2;


	private static final String TAG = "DbAdapter";
	
	private static final String DATABASE_TABLE = "pssds";
	private static final String DATABASE_TABLE2 = "user";

		public DatabaseHelper() {
			super(contextProvider.get(), DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE2);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE2);
			onCreate(db);
		}
	}