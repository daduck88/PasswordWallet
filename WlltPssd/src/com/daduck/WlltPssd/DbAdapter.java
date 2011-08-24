package com.daduck.WlltPssd;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 * 
 * This has been improved from the first version of this tutorial through the
 * addition of better error handling and also using returning a Cursor instead
 * of using a collection of inner classes (which is less scalable and not
 * recommended).
 */
@Singleton
public class DbAdapter implements DbAdapterI{

	public static final String KEY_NAME = "name";
	public static final String KEY_PSSD = "psswd";
	public static final String KEY_BODY = "body";
	public static final String KEY_QUEST = "quest";
	public static final String KEY_ANSWE = "answe";
	public static final String KEY_LESS = "less";
	public static final String KEY_ROWID = "_id";

	@Inject DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_TABLE = "pssds";
	private static final String DATABASE_TABLE2 = "user";

	private final Context mCtx;

	@Inject protected static Provider<Context> contextProvider;

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	/*public DbAdapter(Context ctx) {
		this.mCtx = ctx;
	}*/
	public DbAdapter() {
		this.mCtx = contextProvider.get();
	}

	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */
	public DbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper();
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Create a new note using the title and body provided. If the note is
	 * successfully created return the new rowId for that note, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param title
	 *            the title of the note
	 * @param body
	 *            the body of the note
	 * @return rowId or -1 if failed
	 */
	public long createPssd(String name, String pssd, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_PSSD, pssd);
		initialValues.put(KEY_BODY, body);

		return mDb.insert(DATABASE_TABLE, null, initialValues);
	}

	public long createUser(String psswd, String quest, String answe) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_PSSD, psswd);
		initialValues.put(KEY_QUEST, quest);
		initialValues.put(KEY_ANSWE, answe);
		initialValues.put(KEY_LESS, 5);

		return mDb.insert(DATABASE_TABLE2, null, initialValues);
	}

	/**
	 * Delete the note with the given rowId
	 * 
	 * @param rowId
	 *            id of note to delete
	 * @return true if deleted, false otherwise
	 */
	public boolean deletePssd(long rowId) {

		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the list of all notes in the database
	 * 
	 * @return Cursor over all notes
	 */
	public Cursor fetchAllPssds() {

		return mDb.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
				KEY_PSSD, KEY_BODY }, null, null, null, null, null);
	}

	/**
	 * Return a Cursor positioned at the note that matches the given rowId
	 * 
	 * @param rowId
	 *            id of note to retrieve
	 * @return Cursor positioned to matching note, if found
	 * @throws SQLException
	 *             if note could not be found/retrieved
	 */
	public Cursor fetchPssd(long rowId) throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
				KEY_PSSD, KEY_BODY }, KEY_ROWID + "=" + rowId, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	public Cursor fetchUser() throws SQLException {

		Cursor mCursor =

		mDb.query(true, DATABASE_TABLE2, new String[] { KEY_PSSD, KEY_QUEST,
				KEY_ANSWE, KEY_LESS }, null, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

	/**
	 * Update the note using the details provided. The note to be updated is
	 * specified using the rowId, and it is altered to use the title and body
	 * values passed in
	 * 
	 * @param rowId
	 *            id of note to update
	 * @param title
	 *            value to set note title to
	 * @param body
	 *            value to set note body to
	 * @return true if the note was successfully updated, false otherwise
	 */
	public boolean updatePssd(long rowId, String name, String pssd, String body) {
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_PSSD, pssd);
		args.put(KEY_BODY, body);

		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	public boolean updateUser(String pssd, long less) {
		ContentValues args = new ContentValues();
		if (pssd == null)
			args.put(KEY_LESS, less);
		else{
			args.put(KEY_PSSD, pssd);
			args.put(KEY_LESS, 5);
		}
		return mDb.update(DATABASE_TABLE2, args, null, null) > 0;
	}
	public void deleteData (){
		mDb.execSQL("DROP TABLE IF EXISTS pssds");
		mDb.execSQL("DROP TABLE IF EXISTS user");
		mDbHelper.onCreate(mDb);		
	}
}