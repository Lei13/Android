package com.scxh.android.provider;

import java.util.Map;
import java.util.Set;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class MySharedProvider extends ContentProvider{
	public static final Uri TAG_URI = Uri.parse("content://com.scxh.android.mysharedprovider");
	SharedPreferences mSharePreferences;
	
	@Override
	public boolean onCreate() {
		mSharePreferences = getContext().getSharedPreferences("com.scxh.android.provider",0);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String[] columnNames = {"username","password"};
		MatrixCursor cursor = new MatrixCursor(columnNames);
		Map<String,?> map = mSharePreferences.getAll();
		int i =0;
		String[] columnValues = null;
		for (String key : map.keySet()) {
			columnValues[i++] = (String) map.get(key);
		}
		cursor.addRow(columnValues);
		
		return null;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Editor editor  = mSharePreferences.edit();
		for (String va : values.keySet()) {
			editor.putString(va, (String) values.get(va));
		}
		editor.commit();
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
