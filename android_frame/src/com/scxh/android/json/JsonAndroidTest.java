package com.scxh.android.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.scxh.android.httpconnect.HttpConnectUtil;
import com.scxh.android.httpconnect.HttpConnectUtil.HttpConnectionResponse;
import com.scxh.android.httpconnect.HttpConnectUtil.Method;

import android.content.res.AssetManager;
import android.test.AndroidTestCase;
import android.util.Log;

public class JsonAndroidTest extends AndroidTestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void readJsonTest() throws JSONException {
		String str = "{\"student\":{\"name\":\"xiaoming\",\"age\":25}}";

		JSONObject json = new JSONObject(str);
		JSONObject obj = json.getJSONObject("student");

		Log.v("tag", "" + obj.getString("name") + "  " + obj.getInt("age"));
	}

	public void writeToJsonTest() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("name", "lisa");
		json.put("sex", "woman");

	}

	public void readAsset() throws IOException {
		InputStream is = getContext().getAssets().open("hg.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
		String str;
		String s = "";
		while ((str = reader.readLine()) != null) {
			s += str;
		}
		Log.v("Tg", s + ".......");
	}

	public void readFile() {
		new HttpConnectUtil().httpConnect(Method.POST,
				"http://192.168.1.156/gui.txt", new HttpConnectionResponse() {

					@Override
					public void handleHttpResponse(String response) {
						Log.v("tag", "response:  " + response);
					}
				});
	}

	public void listAsset() throws IOException {
		AssetManager manager = getContext().getAssets();
		String[] file = manager.list("");
		for (String path : file) {
			Log.v("Tg", "file:  " + path);
		}
	}

	public void listJsonObject() throws JSONException {
		String str = "{\"class\":[" +
				"{\"classname\":\"first\",\"classperson\":{\"boy\":12,\"girl\":16}}," +
				"{\"classname\":\"second\",\"classperson\":{\"boy\":10,\"girl\":13}}," +
				"{\"classname\":\"third\",\"classperson\":{\"boy\":16,\"girl\":2}}]}";
		JSONObject person1 = new JSONObject(str);
	}
}
