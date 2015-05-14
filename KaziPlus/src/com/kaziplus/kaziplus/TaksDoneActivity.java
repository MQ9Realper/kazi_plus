package com.kaziplus.kaziplus;

import com.kaziplus.kaziplus.database.KaziPlusDatabaseAdapter;
import com.kaziplus.kaziplus.database.ProjectsTable;
import com.kaziplus.kaziplus.database.TasksTable;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TaksDoneActivity extends Activity {
	private KaziPlusDatabaseAdapter database_adapter;
	private ListView tasks_listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taks_done);
		
//		ActionBar action_bar = getActionBar();
//		action_bar.setDisplayHomeAsUpEnabled(true);

		database_adapter = new KaziPlusDatabaseAdapter(this);
		tasks_listview = (ListView) findViewById(R.id.tasks_list);

		listTasks();
	}

	// Method for loading Tasks into a Listview
	public void listTasks() {
		Cursor task_names = database_adapter.listTasks();
		String[] source = new String[] { TasksTable.TASK_ID,
				TasksTable.TASK_NAME, TasksTable.EMPLOYEE_ID,
				TasksTable.DATE_DONE };
		int[] destination = new int[] { R.id.label_task_id1,
				R.id.label_task_name1, R.id.label_emp_name1,
				R.id.label_done_date1 };
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.tasks_done_items, task_names, source, destination);
		tasks_listview.setAdapter(adapter);
	}
}
