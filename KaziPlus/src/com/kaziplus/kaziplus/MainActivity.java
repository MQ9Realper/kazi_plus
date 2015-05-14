package com.kaziplus.kaziplus;

import com.kaziplus.kaziplus.database.KaziPlusDatabaseAdapter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private KaziPlusDatabaseAdapter database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		database = new KaziPlusDatabaseAdapter(this);
		// Set ActionBar background and title
		ActionBar action_bar = getActionBar();
		action_bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.action_bar));
		action_bar.setTitle(" ");
	}

	// Method for starting SignupActivity
	public void createAccount(View view) {
		Intent create_account = new Intent(this, SignupActivity.class);
		startActivity(create_account);

	}

	// Method for starting SigninActivity
	public void signIn(View view) {
		Intent sign_in = new Intent(this, SigninActivity.class);
		startActivity(sign_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_about) {
			CharSequence[] about_details = new CharSequence[]{"Developer: Dennis Mwebia", "Phone: +254 728 591 754", "Email: mwebia@live.com"};

			AlertDialog.Builder about_alert = new AlertDialog.Builder(this);
			about_alert.setIcon(R.drawable.ic_launcher);
			about_alert.setTitle("About App");
			about_alert.setItems(about_details, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			about_alert.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			});

			AlertDialog alert = about_alert.create();
			alert.show();

		} else if (id == R.id.action_upgrade) {
			AlertDialog.Builder upgrade_alert = new AlertDialog.Builder(this);
			upgrade_alert.setIcon(R.drawable.ic_launcher);
			upgrade_alert.setTitle("Kazi+ Database Upgrade");
			upgrade_alert.setMessage("Are you sure you want to proceed?");
			upgrade_alert.setPositiveButton("Yes", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					database.onUpgrade();
					Toast.makeText(getApplicationContext(),
							"Process is complete!", Toast.LENGTH_LONG).show();

				}
			});

			upgrade_alert.setNegativeButton("No",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
			AlertDialog alert = upgrade_alert.create();
			alert.show();

		}
		return super.onOptionsItemSelected(item);
	}
}
