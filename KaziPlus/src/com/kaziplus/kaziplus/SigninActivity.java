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

public class SigninActivity extends Activity {
	private KaziPlusDatabaseAdapter database_adapter;
	private EditText txt_signin_email;
	private EditText txt_signin_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);

		txt_signin_email = (EditText) findViewById(R.id.txt_login_email);
		txt_signin_password = (EditText) findViewById(R.id.txt_login_password);

		// Set ActionBar background
		ActionBar action_bar = getActionBar();
		action_bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.action_bar));

		database_adapter = new KaziPlusDatabaseAdapter(this);
	}

	// Method for signing in
	public void signin(View view) {
		try {
			String stored_password = database_adapter.signin(txt_signin_email
					.getText().toString());
			if (txt_signin_password.getText().toString()
					.equals(stored_password)) {
				Toast.makeText(this, "You have successfully logged in!",
						Toast.LENGTH_LONG).show();
				
				Intent in = new Intent()
						.setClass(this, DashboardActivity.class);
				in.putExtra("email", txt_signin_email.getText().toString());
				startActivity(in);

			} else {
				Toast.makeText(this, "User does not exist. Try again!",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception e) {
			Log.d("User Login", e.getMessage());
		}

	}
}
