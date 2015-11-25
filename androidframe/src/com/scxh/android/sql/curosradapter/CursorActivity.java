package com.scxh.android.sql.curosradapter;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.scxh.android.frame.R;
import com.scxh.android.store.sql.ColumnData;
import com.scxh.android.store.sql.MyDBHelper;

public class CursorActivity extends ListActivity {
	SQLiteDatabase mDb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyDBHelper helper = MyDBHelper.getInstance(this);
		mDb = helper.getReadableDatabase();
		Cursor cursor = mDb.query(ColumnData.StudentTable.TABLE_NAME,null, null, null, null, null, null);
		MyCursorAdapter adapter = new MyCursorAdapter(this, cursor);
		setListAdapter(adapter);
		
	}
	
	public class MyCursorAdapter extends CursorAdapter{
		LayoutInflater inflater;
		

		public MyCursorAdapter(Context context, Cursor c) {
			super(context, c, true);
			inflater = LayoutInflater.from(context);
		}

		@Override
		public void bindView(View arg0, Context arg1, Cursor arg2) {
			TextView idTxt = (TextView) arg0.findViewById(R.id.database_list_item_id);
			TextView nameTxt = (TextView) arg0.findViewById(R.id.database_list_item_name);
			
			String name = arg2.getString(arg2.getColumnIndex(ColumnData.StudentTable.COLUMN_NAME));
			int id = arg2.getInt(arg2.getColumnIndex(ColumnData.StudentTable.COLUMN_ID));
		
			
			nameTxt.setText(name);
			idTxt.setText(String.valueOf(id));
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			
			return inflater.inflate(R.layout.database_list_item, null);
		}
		
	}
}