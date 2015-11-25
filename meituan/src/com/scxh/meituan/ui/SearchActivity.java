package com.scxh.meituan.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;

import com.scxh.meituan.constance.FoodDataBase;
import com.scxh.meituan.constance.ShopBean;

public class SearchActivity extends Activity {
	AutoCompleteTextView mSearch;
	ArrayList<HashMap<String, Object>> mData;
	MySimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_ui_layout);
		mSearch = (AutoCompleteTextView) findViewById(R.id.search_autotext_meituan);
		Log.i("test001", "fist");
		setAutoCompleteTextView();
		Log.i("last", "lase");
	}

	/*
	 * 监听搜索autocompletetextview setadapter
	 */

	public void setAutoCompleteTextView() {
		mSearch.setThreshold(1);
		Log.v("tagG", "mData.length: 1111 ");
		mSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mData = getData();
				//Log.v("tagG", "mData.length:  " + mData.size());
				adapter = new MySimpleAdapter(SearchActivity.this, mData,
						R.layout.search_ui_result_item, new String[] { "test",
								"title" }, new int[] { R.id.result_title_autotext1_item,
								R.id.result_title_autotext_item });
				mSearch.setAdapter(adapter);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	/*
	 * 获得数据源
	 */
	public ArrayList<HashMap<String, Object>> getData() {
		ArrayList<ShopBean> listData = FoodDataBase.getData();

		mData = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Random random = new Random();
		int num = random.nextInt(200)+1;
		
		for (int i = 0; i < listData.size(); i++) {
			String s1 = listData.get(i).getTitle().toString();
			map = new HashMap<String, Object>();
			map.put("test", "约"+num+"个团购");
			map.put("title", s1);
			mData.add(map);

		}
		return mData;
	}

	/*
	 * 监听搜索界面上 返回 <控件
	 */
	public void backClickListener(View view) {
		startActivity(new Intent(this, FoodActivity.class));
	}

	public class MySimpleAdapter extends SimpleAdapter {

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
		}

	}
}