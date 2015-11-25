package com.scxh.meituan.ui;

import com.scxh.meituan.constance.ColumnName;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelpler extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "database.db";
	private final static int DB_VERSION = 1;
	private final static String PRIMARY_KEY = "INTEGER PRIMARY KEY AUTOINCREMENT";

	public MyDbHelpler(Context context) {
		super(context, DATABASE_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + ColumnName.UserTable.TABLE_NAME + "("
				+ ColumnName.UserTable.COLUMN_ID + " "+PRIMARY_KEY+","
				+ ColumnName.UserTable.COLUMN_USERNAME + " varchar(20),"
				+ ColumnName.UserTable.COLUMN_PASSWORD + " varchar(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("insert into user values(test,001)");
	}

}
