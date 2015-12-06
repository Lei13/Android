package com.scxh.android.store.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper{
	private static MyDBHelper sHelper;
	public static final String DB_NAME = "student_database.db";

	public  static MyDBHelper getInstance(Context context){
		if (sHelper ==null) {
			sHelper = new MyDBHelper(context);
		}
		return sHelper;
	}
	
	private MyDBHelper(Context context) {
		super(context, DB_NAME, null, ColumnData.StudentTable.STUDENT_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table student ("+ColumnData.StudentTable.COLUMN_ID+" int,"+ColumnData.StudentTable.COLUMN_NAME+" varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
