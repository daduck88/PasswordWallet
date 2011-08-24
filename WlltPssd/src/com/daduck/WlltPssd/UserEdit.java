package com.daduck.WlltPssd;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserEdit extends RoboActivity {

	@InjectView(R.id.Equest) EditText nQuesText;
	@InjectView(R.id.Eanswe) EditText mAnswText;
	@InjectView(R.id.Epssd) EditText nPssdText;
	@InjectView(R.id.confirm4) Button confirmButton;
	private Long mRowId;
	@Inject DbAdapterI mDbHelper;

	private String Answer;
	//private String Question;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mDbHelper = new DbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.user_edit);
		setTitle(R.string.edit_user);

		populateFields();

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});
	}

	private void populateFields() {
		Cursor note = mDbHelper.fetchUser();
		startManagingCursor(note);
		Answer = note
				.getString(note.getColumnIndexOrThrow(DbAdapter.KEY_ANSWE));
		nQuesText.setText(note.getString(note
				.getColumnIndexOrThrow(DbAdapter.KEY_QUEST)));
		//Question = nQuesText.getText().toString();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(DbAdapter.KEY_ROWID, mRowId);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveState();
	}

	@Override
	protected void onResume() {
		super.onResume();
		populateFields();
	}

	private void saveState() {
		String pssd = nPssdText.getText().toString();
		String answ = mAnswText.getText().toString();
		if (answ.equals(Answer))
			mDbHelper.updateUser(pssd, 0);
	}

}