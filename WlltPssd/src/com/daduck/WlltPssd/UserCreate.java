package com.daduck.WlltPssd;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserCreate extends RoboActivity {

	@InjectView (R.id.Cquest) EditText nPssdText;
	@InjectView (R.id.Cpssd) EditText nQuestText;
	@InjectView (R.id.Canswe) EditText mAnsweText;
	@InjectView (R.id.confirm3) Button confirmButton;
	private Long mRowId;
	@Inject DbAdapterI mDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mDbHelper = new DbAdapter(this);
		mDbHelper.open();

		setContentView(R.layout.user_create);
		setTitle(R.string.create_user);

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				setResult(RESULT_OK);
				finish();
			}

		});
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
	}

	private void saveState() {
		String quest = nQuestText.getText().toString();
		String pssd = nPssdText.getText().toString();
		String answe = mAnsweText.getText().toString();

		long id = mDbHelper.createUser(pssd, quest, answe);
		if (id > 0)
			mRowId = id;
	}

}