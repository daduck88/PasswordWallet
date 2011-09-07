package com.daduck.WlltPssd;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Principal extends RoboActivity {

	@Inject DbAdapterI mDbHelper;
	private String pssd;
	private Long less;
	@InjectView(R.id.Ppssd) EditText nPssdText;
	@InjectView(R.id.Less) TextView nLess;
	@InjectView (R.id.confirm2) Button confirmButton; 

	private static final int ALTER_ID = Menu.FIRST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mDbHelper = new DbAdapter(this);
		//Cambio en el archivo principal para trabajar con el git giu
		//cambio probando GitForce denuevo
		mDbHelper.open();

		setContentView(R.layout.principal);
		setTitle(R.string.principal);

		populatePssd();

		if (pssd == null)
			goUserCreate();

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String psswrd = nPssdText.getText().toString();
				if (psswrd.equals(pssd)) {
					goPad();
					finish();
				} else {
					mDbHelper.updateUser(null, (less - 1));
					populatePssd();
					if (less == 0){
						mDbHelper.deleteData();
						finish();
					}
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ALTER_ID, 0, R.string.menu_alter);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case ALTER_ID:
			goUserEdit();
			// finish();
			return true;
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		populatePssd();
	}

	private void goPad() {
		startActivity(new Intent(Principal.this, PssdPad.class));
	}

	private void goUserEdit() {
		startActivity(new Intent(Principal.this, UserEdit.class));
	}

	private void goUserCreate() {
		startActivity(new Intent(Principal.this, UserCreate.class));
	}

	private void populatePssd() {

		try {

			Cursor note = mDbHelper.fetchUser();
			startManagingCursor(note);
			pssd = note.getString(note
					.getColumnIndexOrThrow(DbAdapter.KEY_PSSD));
			less = note.getLong(note.getColumnIndexOrThrow(DbAdapter.KEY_LESS));
			nLess.setText(String.valueOf(less + " attempt to lock"));
		} catch (Exception e) {
			pssd = null;
		}
	}

}
