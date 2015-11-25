package com.scxh.android.store.sql;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class MyDatabaseResultActivity extends Activity {
	ListView mListView;
	ArrayList<StudentBean> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydatabase_list_layout);
		mListView = (ListView) findViewById(R.id.mydatabase_listview);
		Cursor cursor = MyDBHelper.getInstance(this).getReadableDatabase().query(ColumnData.StudentTable.TABLE_NAME, null, null, null, null, null, null);
		MyCursorAdapter adapter = new MyCursorAdapter(this,cursor,true);
		mListView.setAdapter(adapter);

	}

	class MyCursorAdapter extends CursorAdapter {
		ArrayList<StudentBean> data = new ArrayList<StudentBean>();
		LayoutInflater inflater ;

		public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
			super(context, c, autoRequery);
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			
			return inflater.inflate(R.layout.database_list_item, null);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			
			TextView id = (TextView) view.findViewById(R.id.database_list_item_id);
			TextView name = (TextView) view.findViewById(R.id.database_list_item_name);
			if (cursor.getCount()>0) {
				id.setText(""+cursor.getInt(cursor.getColumnIndex(ColumnData.StudentTable.COLUMN_ID)));
				name.setText(cursor.getString(cursor.getColumnIndex(ColumnData.StudentTable.COLUMN_NAME)));
			}else {
				Toast.makeText(context, "没有数据", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
