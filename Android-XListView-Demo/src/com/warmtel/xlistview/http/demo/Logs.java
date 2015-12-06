package com.warmtel.xlistview.http.demo;

import android.util.Log;

public class Logs {
	private static String tag = "scxh";

	private static boolean flag = true;

	public static void v(String message) {
		if (flag) {
			Log.v(tag, message);
		}
	}

	public static void d(String message) {
		if (flag) {
			Log.d(tag, message);
		}
	}

	public static void i(String message) {
		if (flag) {
			Log.i(tag, message);
		}
	}

	public static void w(String message) {
		if (flag) {
			Log.w(tag, message);
		}
	}

	public static void e(String message) {
		if (flag) {
			Log.e(tag, message);
		}
	}
}
