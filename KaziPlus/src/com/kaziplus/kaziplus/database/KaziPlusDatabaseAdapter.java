package com.kaziplus.kaziplus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class KaziPlusDatabaseAdapter extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "kazi_plus.db";
	private static final int DATABASE_VERSION = 1;

	public KaziPlusDatabaseAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
		}

		// Create Projects Table
		db.execSQL("CREATE TABLE " + ProjectsTable.TABLE_NAME + "("
				+ ProjectsTable.PROJECT_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ ProjectsTable.PROJECT_NAME + " TEXT,"
				+ ProjectsTable.PROJECT_START_DATE + " TEXT,"
				+ ProjectsTable.PROJECT_END_DATE + " TEXT" + ");");

		// Create Employees Table
		db.execSQL("CREATE TABLE " + EmployeesTable.TABLE_NAME + "("
				+ EmployeesTable.EMP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ EmployeesTable.EMP_FIRST_NAME + " TEXT,"
				+ EmployeesTable.EMP_LAST_NAME + " TEXT,"
				+ EmployeesTable.EMP_PHONE + " TEXT,"
				+ EmployeesTable.EMP_EMAIL + " TEXT,"
				+ EmployeesTable.EMP_PASSWORD + " TEXT" + ");");

		// Create Tasks Table
		db.execSQL("CREATE TABLE " + TasksTable.TABLE_NAME + "("
				+ TasksTable.TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ TasksTable.EMPLOYEE_ID + " INTEGER REFERENCES "
				+ EmployeesTable.TABLE_NAME + "(" + EmployeesTable.EMP_ID
				+ ")," + TasksTable.PROJECT_NAME + " TEXT,"
				+ TasksTable.TASK_NAME + " TEXT," + TasksTable.TIME_TAKEN
				+ " TEXT," + TasksTable.DATE_DONE + " TEXT,"
				+ TasksTable.TASK_COMMENTS + " TEXT" + ");");

	}

	// Helper method for adding Employee(s)
	public long addEmployee(String first_name, String last_name, String phone,
			String email, String password) {
		ContentValues employee_contents = new ContentValues();
		employee_contents.put(EmployeesTable.EMP_FIRST_NAME, first_name);
		employee_contents.put(EmployeesTable.EMP_LAST_NAME, last_name);
		employee_contents.put(EmployeesTable.EMP_PHONE, phone);
		employee_contents.put(EmployeesTable.EMP_EMAIL, email);
		employee_contents.put(EmployeesTable.EMP_PASSWORD, password);

		SQLiteDatabase db = getWritableDatabase();
		long result = db.insert(EmployeesTable.TABLE_NAME,
				EmployeesTable.EMP_FIRST_NAME, employee_contents);
		return result;

	}

	// Helper method for adding Project(s)
	public long addProject(String project_name, String start_date,
			String end_date) {
		ContentValues project_contents = new ContentValues();
		project_contents.put(ProjectsTable.PROJECT_NAME, project_name);
		project_contents.put(ProjectsTable.PROJECT_START_DATE, start_date);
		project_contents.put(ProjectsTable.PROJECT_END_DATE, end_date);

		SQLiteDatabase db = getWritableDatabase();
		long result = db.insert(ProjectsTable.TABLE_NAME,
				ProjectsTable.PROJECT_NAME, project_contents);
		return result;

	}

	// Helper method for adding a Task
	public long addTask(String emp_id, String project_name, String task_name,
			String date_done, String time_taken, String comments) {
		ContentValues task_contents = new ContentValues();
		task_contents.put(TasksTable.EMPLOYEE_ID, emp_id);
		task_contents.put(TasksTable.PROJECT_NAME, project_name);
		task_contents.put(TasksTable.TASK_NAME, task_name);
		task_contents.put(TasksTable.DATE_DONE, date_done);
		task_contents.put(TasksTable.TIME_TAKEN, time_taken);
		task_contents.put(TasksTable.TASK_COMMENTS, comments);

		SQLiteDatabase db = getWritableDatabase();
		long result = db.insert(TasksTable.TABLE_NAME, TasksTable.TASK_NAME,
				task_contents);
		return result;

	}

	// Helper method for listing Tasks
	public Cursor listTasks() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor tasks = db.query(TasksTable.TABLE_NAME, new String[] {
				TasksTable.TASK_ID, TasksTable.TASK_NAME,
				TasksTable.EMPLOYEE_ID, TasksTable.DATE_DONE }, null, null, null, null, null);

		return tasks;

	}

	// Helper method for listing Project names
	public Cursor listProjectNames() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor project_names = db.query(ProjectsTable.TABLE_NAME, new String[] {
				ProjectsTable.PROJECT_ID, ProjectsTable.PROJECT_NAME }, null,
				null, null, null, null);

		return project_names;

	}

	// Helper method for listing Employees
	public Cursor listEmployees() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor employees = db.query(EmployeesTable.TABLE_NAME, new String[] {
				EmployeesTable.EMP_ID, EmployeesTable.EMP_FIRST_NAME,
				EmployeesTable.EMP_LAST_NAME }, null, null, null, null, null);

		return employees;

	}

	// Helper method for returning a user (employee) in the event of logging in
	public String signin(String email) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor user = db.query(EmployeesTable.TABLE_NAME, null,
				EmployeesTable.EMP_EMAIL + "=?", new String[] { email }, null,
				null, null);
		if (user.getCount() < 1) {
			user.close();
			return "User does not exist";
		} else {
			user.moveToFirst();
			String password = user.getString(user
					.getColumnIndex(EmployeesTable.EMP_PASSWORD));
			return password;

		}

	}

	public void onUpgrade() {
		SQLiteDatabase db = getWritableDatabase();
		Log.w("LOG_TAG", "Upgrading database");
		// KILL PREVIOUS
		db.execSQL("DROP TABLE IF EXISTS " + EmployeesTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + ProjectsTable.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TasksTable.TABLE_NAME);
		onCreate(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
