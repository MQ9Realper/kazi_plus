package com.kaziplus.kaziplus;

import com.kaziplus.kaziplus.database.KaziPlusDatabaseAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	private KaziPlusDatabaseAdapter database_adapter;
	private EditText txt_first_name;
	private EditText txt_last_name;
	private EditText txt_phone;
	private EditText txt_email;
	private EditText txt_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		database_adapter = new KaziPlusDatabaseAdapter(this);
		
		txt_first_name = (EditText) findViewById(R.id.txt_first_name);
		txt_last_name = (EditText) findViewById(R.id.txt_last_name);
		txt_phone = (EditText) findViewById(R.id.txt_phone);
		txt_email = (EditText) findViewById(R.id.txt_email);
		txt_password = (EditText) findViewById(R.id.txt_password);

		// Set ActionBar background
		ActionBar action_bar = getActionBar();
		action_bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.action_bar));
	}

	// Method for adding a new employee
	public void addEmployee(View view) {
		try {
			database_adapter.addEmployee(txt_first_name.getText().toString(),
					txt_last_name.getText().toString(), txt_phone.getText()
							.toString(), txt_email.getText().toString(),
					txt_password.getText().toString());
			Toast.makeText(
					this,
					txt_first_name.getText().toString()
							+ " has been successfully registered!",
					Toast.LENGTH_LONG).show();

			// Reset textboxes
			txt_first_name.setText("");
			txt_last_name.setText("");
			txt_phone.setText("");
			txt_email.setText("");
			txt_password.setText("");

			// Start the Dashboard Activity
			Intent in = new Intent().setClass(this, DashboardActivity.class);
			startActivity(in);

		} catch (Exception e) {
			Log.d("Employee Registration", e.getMessage().toString());

		}
	}
}
