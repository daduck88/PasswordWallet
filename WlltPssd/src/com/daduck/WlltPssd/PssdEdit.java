package com.daduck.WlltPssd;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PssdEdit extends RoboActivity {

	@InjectView (R.id.name) EditText nNameText;
	@InjectView (R.id.pssd) EditText nPssdText;
	@InjectView (R.id.body) EditText mBodyText;
	@InjectView (R.id.confirm) Button confirmButton;
	private Long mRowId;
	@Inject DbAdapterI mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mDbHelper = new DbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.pssd_edit);
		setTitle(R.string.edit_pssd);

		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState
						.getSerializable(DbAdapter.KEY_ROWID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(DbAdapter.KEY_ROWID)
					: null;
		}

		populateFields();

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchPssd(mRowId);
			startManagingCursor(note);
			nNameText.setText(note.getString(note
					.getColumnIndexOrThrow(DbAdapter.KEY_NAME)));
			nPssdText.setText(note.getString(note
					.getColumnIndexOrThrow(DbAdapter.KEY_PSSD)));
			mBodyText.setText(note.getString(note
					.getColumnIndexOrThrow(DbAdapter.KEY_BODY)));
		}
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
		String name = nNameText.getText().toString();
		String pssd = nPssdText.getText().toString();
		String body = mBodyText.getText().toString();

		if (mRowId == null) {
			long id = mDbHelper.createPssd(name, pssd, body);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updatePssd(mRowId, name, pssd, body);
		}
	}

}