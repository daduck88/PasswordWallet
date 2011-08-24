package com.daduck.WlltPssd;

import android.database.sqlite.SQLiteDatabase;

public interface DatabaseHelperI {
	void onCreate(SQLiteDatabase db);
	void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
