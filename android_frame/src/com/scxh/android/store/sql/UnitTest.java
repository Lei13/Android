package com.scxh.android.store.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

public class UnitTest extends AndroidTestCase{
	Context context;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		context = getContext();
	}

	public void testCreateDatabase(){
		SQLiteDatabase db = context.openOrCreateDatabase("database.db", 1, null);
		db.execSQL("create table user(username varchar(20),password vachar(20))");
		Log.v("tag<<", "database........");
	}
}
