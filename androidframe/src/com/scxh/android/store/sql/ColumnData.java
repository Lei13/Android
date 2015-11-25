package com.scxh.android.store.sql;

public class ColumnData {
	public abstract class StudentTable{
		public static final String TABLE_NAME = "student";
		public static final int STUDENT_VERSION = 1;
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "name";
	}

	public abstract class UserTable{
		public static final String TABLE_NAME = "user";
		public static final int STUDENT_VERSION = 1;
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "username";
		public static final String COLUMN_PASSWORD = "userpassword";

		
	}
}
