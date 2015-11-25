package com.scxh.android.db;

import Constance.DB_Constance;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class Provider extends ContentProvider {
	private static final String URI_AUTHORITY = "com.scxh.android.db.provider";
	public static final Uri CONTENT_URI = Uri.parse("content://"+ URI_AUTHORITY);
	
	public static final Uri URI_MUSIC_INFO = Uri.parse("content://"+ URI_AUTHORITY+"/"+DB_Constance.Table_musicInfo.TABLE_NAME);

	
	
	private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int MUSIC_INFO = 1;
	private static final int MUSIC_COLLECT = 2;

	SQLiteDatabase mDb;

	@Override
	public boolean onCreate() {
		DbHelper helper = new DbHelper(getContext());
		mDb = helper.getReadableDatabase();

		uriMatcher.addURI(URI_AUTHORITY,DB_Constance.Table_musicInfo.TABLE_NAME, MUSIC_INFO);
		uriMatcher.addURI(URI_AUTHORITY,DB_Constance.Table_musicCollect.TABLE_NAME, MUSIC_COLLECT);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		switch (uriMatcher.match(uri)) {
		case MUSIC_INFO:
			cursor = mDb
					.query(DB_Constance.Table_musicInfo.TABLE_NAME, projection,
							selection, selectionArgs, null, null, sortOrder);
			break;

		case MUSIC_COLLECT:
			cursor = mDb
					.query(DB_Constance.Table_musicCollect.TABLE_NAME,
							projection, selection, selectionArgs, null, null,
							sortOrder);
			break;
		}
		return cursor;

	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = 0;
		switch (uriMatcher.match(uri)) {
		case MUSIC_INFO:

			id = mDb.insert(DB_Constance.Table_musicInfo.TABLE_NAME, null,
					values);
			break;

		case MUSIC_COLLECT:
			id = mDb.insert(DB_Constance.Table_musicCollect.TABLE_NAME, null,
					values);
			break;
		}
		return Uri.withAppendedPath(CONTENT_URI, id + "");
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch (uriMatcher.match(uri)) {
		case MUSIC_INFO:
			mDb.delete(DB_Constance.Table_musicInfo.TABLE_NAME, selection,
					selectionArgs);
			break;
		case MUSIC_COLLECT:

			mDb.delete(DB_Constance.Table_musicCollect.TABLE_NAME, selection,
					selectionArgs);
			break;
		}

		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		switch (uriMatcher.match(uri)) {
		case MUSIC_INFO:
			mDb.update(DB_Constance.Table_musicInfo.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		case MUSIC_COLLECT:
			mDb.update(DB_Constance.Table_musicCollect.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		}
		return 0;
	}

	/*
	 * 
	 */
	public class DbHelper extends SQLiteOpenHelper {
		private static final String PRIMARY_KEY = "INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT";

		private static final String DB_NAME = "music_list.db";
		private static final int DB_VERSION = 1;

		private DbHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table  "
					+ DB_Constance.Table_musicInfo.TABLE_NAME + "  ("
					+ DB_Constance.Table_musicInfo.COLUMN_ID + " "
					+ PRIMARY_KEY + ", "
					+ DB_Constance.Table_musicInfo.COLUMN_NAME
					+ " varchar(20), "
					+ DB_Constance.Table_musicInfo.COLUMN_COMPOSER
					+ " varchar(20), "
					+ DB_Constance.Table_musicInfo.COLUMN_LYRIC
					+ " varchar(50),  "
					+ DB_Constance.Table_musicInfo.COLUMN_IMG + " int,  "
					+ DB_Constance.Table_musicInfo.COLUMN_PATH
					+ " varchar(50))");

			db.execSQL("create table  "
					+ DB_Constance.Table_musicCollect.TABLE_NAME + "   ("
					+ DB_Constance.Table_musicCollect.COLUMN_ID + " "
					+ PRIMARY_KEY + ", "
					+ DB_Constance.Table_musicCollect.COLUMN_NAME
					+ " varchar(20),  "
					+ DB_Constance.Table_musicCollect.COLUMN_COMPOSER
					+ " varchar(20),  "
					+ DB_Constance.Table_musicCollect.COLUMN_LYRIC
					+ " varchar(50),  "
					+ DB_Constance.Table_musicCollect.COLUMN_IMG + "  int,  "
					+ DB_Constance.Table_musicCollect.COLUMN_PATH
					+ " varchar(50))");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

	}

}
