package com.scxh.android.store.sql;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class MyDatabaseActivity extends Activity {
	SQLiteDatabase db;
	Button inquryBtn;
	Button insertBtn;
	EditText idEdt;
	EditText nameEdt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_database);
		initView();
		MyDBHelper dbHelper = MyDBHelper.getInstance(this);
		db = dbHelper.getReadableDatabase();
	}

	private void initView() {
		idEdt = (EditText) findViewById(R.id.id_edit);
		nameEdt = (EditText) findViewById(R.id.name_edit);
		inquryBtn = (Button) findViewById(R.id.inqury_btn);
		insertBtn = (Button) findViewById(R.id.insert_btn);
	}

	public void studentDBBtnClickListener(View view) {
		switch (view.getId()) {
			case R.id.inqury_btn:
				inquryAllDatabase();
				break;

			case R.id.insert_btn:
				int id = Integer.parseInt(idEdt.getText().toString());
				String name = nameEdt.getText().toString();
				// db.execSQL("insert into student values("+id+","+name+")" );

				ContentValues values = new ContentValues();
				values.put(ColumnData.StudentTable.COLUMN_ID, id);
				values.put(ColumnData.StudentTable.COLUMN_NAME, name);
				db.insert(ColumnData.StudentTable.TABLE_NAME, null, values);

				idEdt.setText("");
				nameEdt.setText("");
				Toast.makeText(this, "插入成功", Toast.LENGTH_SHORT).show();
				break;
		}
	}

	private void inquryAllDatabase() {
		startActivity(new Intent(this, MyDatabaseResultActivity.class));

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (db == null) {
			db.close();
		}
	}
}
