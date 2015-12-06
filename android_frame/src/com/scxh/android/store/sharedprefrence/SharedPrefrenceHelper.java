package com.scxh.android.store.sharedprefrence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceHelper {
	Context context;
	private static SharedPrefrenceHelper sHelper;
	private SharedPreferences mSharedPreference;
	private static final String NAME = "com.scxh.android.store.PREFERENCES_FILE_KEY";

	public static SharedPrefrenceHelper getInstance(Context context) {
		if (sHelper == null) {
			sHelper = new SharedPrefrenceHelper(context);
		}
		return sHelper;
	}

	private SharedPrefrenceHelper(Context context) {
		mSharedPreference = context.getSharedPreferences(NAME, 0);
	}

}
