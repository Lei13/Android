package com.scxh.android.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

public class FastJsonTest extends AndroidTestCase {

	public void fastJsonTest() throws IOException {
		String jsonStr = readFile();
		RootBean rootBean = JSONObject.parseObject(jsonStr, RootBean.class);
		Log.v("tag", "getResultCode: " + rootBean.getResultCode()
				+ "getResultInfo:  " + rootBean.getResultInfo());
		InfoBean infoBean = rootBean.getInfo();
		ArrayList<PicsetBean> picBeanlist = infoBean.getPicSet();
		for (PicsetBean picsetBean : picBeanlist) {
			Log.v("tag", "getAlbumsName:  " + picsetBean.getAlbumsName());
			ArrayList<PicBean> picBeanList = picsetBean.getPicUrlSet();
			for (PicBean picBean : picBeanList) {
				Log.v("tag", "getPicUrl::" + picBean.getPicUrl());
			}
		}

	}

	public String readFile() throws IOException {
		InputStream is = getContext().getResources().getAssets()
				.open("json_xx");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"utf-8"));
		String str = "";
		String s = "";
		while ((str = reader.readLine()) != null) {
			s += str;
		}
		return s;
	}

	public void fastJsonObject() {
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("dojg rgi   " + i);
		}
		array.addAll(list);
		object.put("code", 1);
		object.put("content", array);
		Log.v("tag", object.toString());

	}
}
