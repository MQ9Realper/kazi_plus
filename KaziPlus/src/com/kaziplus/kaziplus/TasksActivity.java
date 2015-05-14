package com.kaziplus.kaziplus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.kaziplus.kaziplus.database.EmployeesTable;
import com.kaziplus.kaziplus.database.KaziPlusDatabaseAdapter;
import com.kaziplus.kaziplus.database.ProjectsTable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class TasksActivity extends Activity {
	private Calendar start_date_calendar;
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private EditText txt_date_done;
	private EditText txt_task_name;
	private EditText txt_task_comments;
	private EditText txt_time_taken;
	private KaziPlusDatabaseAdapter database_adapter;
	private Spinner spinner;
	private Spinner emp_spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
		txt_task_name = (EditText) findViewById(R.id.txt_task_name);
		txt_task_comments = (EditText) findViewById(R.id.txt_comment);
		txt_time_taken = (EditText) findViewById(R.id.txt_time_taken);

		spinner = (Spinner) findViewById(R.id.spinner1);
		emp_spinner = (Spinner) findViewById(R.id.spinner2);

		database_adapter = new KaziPlusDatabaseAdapter(this);
		txt_date_done = (EditText) findViewById(R.id.txt_date_done);
		start_date_calendar = Calendar.getInstance();

		listProjectNames();
		listEmployeeNames();
	}

	// Method for adding a task
	public void addTask(View view) {
		try {
			String spinnerString = null;
			Cursor cc = (Cursor) (emp_spinner.getSelectedItem());
			if (cc != null) {
				spinnerString = cc.getString(cc
						.getColumnIndex(EmployeesTable.EMP_ID));
			}
			database_adapter.addTask("# " + spinnerString, spinner
					.getSelectedItem().toString(), txt_task_name.getText()
					.toString(), txt_date_done.getText().toString(),
					txt_time_taken.getText().toString(), txt_task_comments
							.getText().toString());

			Toast.makeText(this, "A new task has been added!",
					Toast.LENGTH_LONG).show();

			// Reset Textboxes
			txt_task_name.setText("");
			txt_task_comments.setText("");
			txt_date_done.setText("");
			txt_time_taken.setText("");
			
			Intent tasks_done_intent = new Intent().setClass(this, TaksDoneActivity.class);
			startActivity(tasks_done_intent);

		} catch (Exception e) {
			Log.d("Adding Task", e.getMessage());
		}

	}

	// Method for listing all Project Names and feeding them to a Spinner
	public void listProjectNames() {
		Cursor project_names = database_adapter.listProjectNames();
		String[] source = new String[] { ProjectsTable.PROJECT_NAME };
		int[] destination = new int[] { R.id.label_project_name };
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.spinner_items, project_names, source, destination);
		spinner.setAdapter(adapter);
	}

	// Method for listing all Employee names and feeding them to a Spinner
	public void listEmployeeNames() {
		Cursor employee_names = database_adapter.listEmployees();
		String[] source = new String[] { EmployeesTable.EMP_FIRST_NAME,
				EmployeesTable.EMP_LAST_NAME };
		int[] destination = new int[] { R.id.label_emp_first_name,
				R.id.label_emp_last_name };
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.employee_spinner_items, employee_names, source,
				destination);
		emp_spinner.setAdapter(adapter);
	}

	// Method for showing date picker
	public void dateDonePicker(View view) {
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
						updateDateDone();

					}
				}, start_date_calendar.get(Calendar.YEAR),
				start_date_calendar.get(Calendar.MONTH),
				start_date_calendar.get(Calendar.DAY_OF_MONTH));
		date_picker.setTitle("Date Done");
		date_picker.setIcon(R.drawable.ic_launcher);
		date_picker.show();

	}

	// Method for updating date done text
	@SuppressLint("SimpleDateFormat")
	public void updateDateDone() {
		SimpleDateFormat start_date_format = new SimpleDateFormat(DATE_FORMAT);
		String start_date_for_textbox = start_date_format
				.format(start_date_calendar.getTime());
		txt_date_done.setText(start_date_for_textbox);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tasks, menu);
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
