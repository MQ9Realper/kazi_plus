package com.kaziplus.kaziplus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class DashboardActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		// Set ActionBar background
		ActionBar action_bar = getActionBar();
		action_bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.action_bar));

		TabHost tab_host = getTabHost();

		// Projects Tab
		Intent projects_intent = new Intent().setClass(this,
				ProjectsActivity.class);
		TabHost.TabSpec projects_tabspec = tab_host.newTabSpec("Projects")
				.setIndicator("PROJECTS").setContent(projects_intent);
		tab_host.addTab(projects_tabspec);

		// Tasks Tab
		Intent tasks_intent = new Intent().setClass(this, TasksActivity.class);
		TabHost.TabSpec tasks_tabspec = tab_host.newTabSpec("Tasks")
				.setIndicator("TASKS").setContent(tasks_intent);
		tab_host.addTab(tasks_tabspec);

		// Tasks Done Tab
		Intent tasks_done_intent = new Intent().setClass(this,
				TaksDoneActivity.class);
		TabHost.TabSpec tasks_done_tabspec = tab_host.newTabSpec("Tasks Done")
				.setIndicator("TASKS DONE").setContent(tasks_done_intent);
		tab_host.addTab(tasks_done_tabspec);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {

			AlertDialog.Builder upgrade_alert = new AlertDialog.Builder(this);
			upgrade_alert.setIcon(R.drawable.ic_launcher);
			upgrade_alert.setTitle("Kazi+ Logout");
			upgrade_alert.setMessage("Are you sure you want to logout?");
			upgrade_alert.setPositiveButton("Logout", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent logout_intent = new Intent().setClass(
							getApplicationContext(), MainActivity.class);
					startActivity(logout_intent);

					Toast.makeText(getApplicationContext(),
							"You have successfully logged out",
							Toast.LENGTH_LONG).show();

				}
			});

			upgrade_alert.setNegativeButton("Cancel",
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
