package com.scxh.meituan.ui;

import java.io.File;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.scxh.meituan.constance.MySQLiteDatabase;

public class Test extends AndroidTestCase {

	/*
	 * public void testDatabase() { MySQLiteDatabase helper = new
	 * MySQLiteDatabase(getContext()); SQLiteDatabase db =
	 * helper.getReadableDatabase();
	 * db.execSQL("insert into user values('admin','123')"); }
	 */

	public void test() {
		SharedPreferences mshaPreferences = getContext().getSharedPreferences(
				"com.scxh.meituan.ui.PREFERENCES_FILE_KEY", 0);
		String user = mshaPreferences.getString("user", "");
		String pass = mshaPreferences.getString("password", "");
		Log.v("Tg", user + "    " + pass);
		System.out.println(user + "   " + pass);
	}

	public void testset() {
		SharedPreferences msPreferences = getContext().getSharedPreferences(
				"com.scxh.meituan.ui.PREFERENCES_FILE_KEY", 0);
		SharedPreferences.Editor editor = msPreferences.edit();
		editor.putString("user", "user001");
		editor.putString("password", "password112");
		editor.commit();
	}

	public void testFile() {
		File file = new File("file://mnt/sdcard/music/");
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
