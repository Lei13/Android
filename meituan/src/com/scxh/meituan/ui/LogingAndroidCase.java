package com.scxh.meituan.ui;

import com.scxh.meituan.constance.ColumnName;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class LogingAndroidCase extends AndroidTestCase {
	SQLiteDatabase mDb;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MyDbHelpler helper = new MyDbHelpler(getContext());
		mDb = helper.getReadableDatabase();
	}
	
	public void testLoging() {
		String name = "admin";
		
		Cursor cursor = mDb.rawQuery("select * from user where username='"
				+ name + "'", null);

		if (cursor.getCount() > 0){
			Log.v("tag", "´æÔÚ");
		}else{
			Log.v("tag", "²»´æÔÚ");
		}
		
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String username = cursor.getString(cursor.getColumnIndex(ColumnName.UserTable.COLUMN_PASSWORD));
				Log.v("tag", "username: " + username);
			}
		}
	}
}
