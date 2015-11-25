package com.scxh.android.db;

import Constance.DB_Constance;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

public class DbHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "music_list.db";
	private static final int DB_VERSION = 1;
	public static DbHelper dbHelper;
	
	private  DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}
	public static DbHelper getInstance(Context context){
		if (dbHelper ==null) {
			dbHelper = new DbHelper(context);
		}
		return dbHelper;
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table  " + DB_Constance.Table_musicInfo.TABLE_NAME + "  ("
				+ DB_Constance.Table_musicInfo.COLUMN_ID + "  int  "
				+ DB_Constance.Table_musicInfo.COLUMN_NAME + "  varchar(20) "
				+ DB_Constance.Table_musicInfo.COLUMN_COMPOSER + "  varchar(20) "
				+ DB_Constance.Table_musicInfo.COLUMN_LYRIC + "  varchar(100000))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
