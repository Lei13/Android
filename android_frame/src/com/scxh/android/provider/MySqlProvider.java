package com.scxh.android.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.scxh.android.store.sql.ColumnData;

public class MySqlProvider extends ContentProvider {
	private static final String URI_AUTHORITY = "com.scxh.android.provider";
	public static Uri CONTENT_URI = Uri.parse("content://" + URI_AUTHORITY);
	SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		SqlHelper helper = new SqlHelper(getContext());
		db = helper.getReadableDatabase();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		return db.query(ColumnData.UserTable.TABLE_NAME, projection, selection,
				selectionArgs, null, null, sortOrder);
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long data = db.insert(ColumnData.UserTable.TABLE_NAME, null, values);

		return Uri.withAppendedPath(CONTENT_URI, data + "");
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		return db.delete(ColumnData.UserTable.TABLE_NAME, selection,
				selectionArgs);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return db.update(ColumnData.UserTable.TABLE_NAME, values, selection,
				selectionArgs);
	}

	// ***************************************************
	class SqlHelper extends SQLiteOpenHelper {
		private static final String DB_NAME = "user_provider.db";
		private static final int DB_VERSION = 1;
		//private static final String PRIMATY_KEY = " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";

		public SqlHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table " + ColumnData.UserTable.TABLE_NAME + " ("
					+ ColumnData.UserTable.COLUMN_ID + " int ,"
					+ ColumnData.UserTable.COLUMN_NAME + " varchar(20),"
					+ ColumnData.UserTable.COLUMN_PASSWORD + " varchar(20)) ");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}
}
