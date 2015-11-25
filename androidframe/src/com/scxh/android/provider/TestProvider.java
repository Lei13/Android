package com.scxh.android.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.scxh.android.store.sql.ColumnData;

public class TestProvider extends AndroidTestCase {
	ContentResolver resolver;
	Uri uri = Uri.parse("content://com.scxh.android.provider");

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		resolver = getContext().getContentResolver();

	}

	public void testQueryFromProvider() {
		Log.v("tag", " adding before");
		addUser("王小二", "111444");
		addUser("王二", "555455");

		Log.v("tag", " adding");
		Cursor c = resolver.query(uri, null, null, null, null);
		if (c.getCount()>0) {
			while (c.moveToNext()){
				String name = c.getString(c.getColumnIndex(ColumnData.UserTable.COLUMN_NAME));
				String pass = c.getString(c.getColumnIndex(ColumnData.UserTable.COLUMN_PASSWORD));
				Log.v("tag", name+"   "+ pass);
				
			}
		}
	}

	public void addUser(String name, String password) {
		ContentValues values = new ContentValues();
		values.put(ColumnData.UserTable.COLUMN_NAME, name);
		values.put(ColumnData.UserTable.COLUMN_PASSWORD, password);
		Log.v("tag", " addinginging");
		resolver.insert(uri, values);

	}
}
