package com.daduck.WlltPssd;

import android.database.Cursor;

public interface DbAdapterI {
	DbAdapter open();
	void close();
	long createPssd(String name, String pssd, String body);
	long createUser(String psswd, String quest, String answe);
	boolean deletePssd(long rowId);
	Cursor fetchAllPssds();
	Cursor fetchPssd(long rowId);
	Cursor fetchUser();
	boolean updatePssd(long rowId, String name, String pssd, String body);
	boolean updateUser(String pssd, long less);
	void deleteData ();
}
