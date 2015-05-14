package com.kaziplus.kaziplus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.kaziplus.kaziplus.database.KaziPlusDatabaseAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class ProjectsActivity extends Activity {
	private Calendar start_date_calendar;
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private EditText start_date_text;
	private EditText end_date_text;
	private EditText txt_project_name;
	private KaziPlusDatabaseAdapter database_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects);

		start_date_calendar = Calendar.getInstance();
		start_date_text = (EditText) findViewById(R.id.txt_start_date);
		end_date_text = (EditText) findViewById(R.id.txt_end_date);
		txt_project_name = (EditText) findViewById(R.id.txt_project_name);
		database_adapter = new KaziPlusDatabaseAdapter(this);
		
		

	}

	// Method for adding a project
	public void addProject(View view) {
		try {
			database_adapter.addProject(txt_project_name.getText().toString(),
					start_date_text.getText().toString(), end_date_text
							.getText().toString());
			Toast.makeText(
					this,
					txt_project_name.getText().toString()
							+ " has been successfully added!",
					Toast.LENGTH_LONG).show();
			
			// Restore textboxes
			txt_project_name.setText("");
			start_date_text.setText("");
			end_date_text.setText("");

		} catch (Exception e) {
			Log.d("Kazi+ Project(s) Addition", e.getMessage());
		}

	}

	// Method for showing date picker
	public void showDatePicker(View view) {
		DatePickerDialog date_picker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						start_date_calendar.set(Calendar.YEAR, year);
						start_date_calendar.set(Calendar.MONTH, monthOfYear);
						start_date_calendar.set(Calendar.DAY_OF_MONTH,
								dayOfMonth);
						updateStartDate();

					}
				}, start_date_calendar.get(Calendar.YEAR),
				start_date_calendar.get(Calendar.MONTH),
				start_date_calendar.get(Calendar.DAY_OF_MONTH));
		date_picker.setTitle("Set Start Date");
		date_picker.setIcon(R.drawable.ic_launcher);
		date_picker.show();

	}

	// Method for updating start date
	public void updateStartDate() {
		SimpleDateFormat start_date_format = new SimpleDateFormat(DATE_FORMAT);
		String start_date_for_textbox = start_date_format
				.format(start_date_calendar.getTime());
		start_date_text.setText(start_date_for_textbox);

	}

	// Method for showing date picker
	public void showEndDatePicker(View view) {

		DatePickerDialog date_picker = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						start_date_calendar.set(Calendar.YEAR, year);
						start_date_calendar.set(Calendar.MONTH, monthOfYear);
						start_date_calendar.set(Calendar.DAY_OF_MONTH,
								dayOfMonth);
						updateEndDate();

					}
				}, start_date_calendar.get(Calendar.YEAR),
				start_date_calendar.get(Calendar.MONTH),
				start_date_calendar.get(Calendar.DAY_OF_MONTH));
		date_picker.setTitle("Set End Date");
		date_picker.setIcon(R.drawable.ic_launcher);
		date_picker.show();
	}

	// Update end date textbox
	public void updateEndDate() {
		SimpleDateFormat end_date_format = new SimpleDateFormat(DATE_FORMAT);
		String start_date_for_textbox = end_date_format
				.format(start_date_calendar.getTime());
		end_date_text.setText(start_date_for_textbox);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.projects, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
