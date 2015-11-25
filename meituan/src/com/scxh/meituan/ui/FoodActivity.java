package com.scxh.meituan.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.scxh.meituan.adapter.ListViewAdapter;
import com.scxh.meituan.constance.FoodDataBase;
import com.scxh.meituan.constance.ShopBean;

public class FoodActivity extends Activity {
	private ListView mList;
	private ArrayList<ShopBean> listData = new ArrayList<ShopBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.food_meituan_layout);
		mList = (ListView) findViewById(R.id.listview_meituan);

		ListViewAdapter adapter = new ListViewAdapter(this);
		listData = FoodDataBase.getData();
		adapter.setData(listData);
		mList.setAdapter(adapter);
		setSpinner();
	}

	/*
	 * 监听search imageview控件
	 */
	public void searchClickListener(View view) {
		startActivity(new Intent(this, SearchActivity.class));
	}

	// <<<<<<<设置spinner
	public void setSpinner() {
		setSpinnerFirst();
		setSpinnerSecond();
		setSpinnerThird();
		setSpinnerFourth();
	}

	// <<<<<<<<<设置4个spinner
	public void setSpinnerFirst() {
		Spinner first_spinner = (Spinner) findViewById(R.id.spinner_first);
		String[] s = { "美食", "旅游", "美容美发" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.food_spinner_item_laylout, s);
		first_spinner.setAdapter(adapter);
		
		first_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				((TextView) view).setGravity(Gravity.CENTER);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}

	public void setSpinnerSecond() {
		Spinner second_spinner = (Spinner) findViewById(R.id.spinner_second);
		String[] s = { "全城", "青羊区", "高新区" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.food_spinner_item_laylout, s);
		second_spinner.setAdapter(adapter);
		second_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				((TextView) view).setGravity(Gravity.CENTER);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
	}

	public void setSpinnerThird() {
		Spinner third_spinner = (Spinner) findViewById(R.id.spinner_third);
		String[] s = { "智能排序", "好评优先", "距离优先" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.food_spinner_item_laylout, s);
		third_spinner.setAdapter(adapter);
		third_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				((TextView) view).setGravity(Gravity.CENTER);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
	}

	public void setSpinnerFourth() {
		Spinner fourth_spinner = (Spinner) findViewById(R.id.spinner_fourth);
		String[] s = { "筛选", "0-99", "99-200" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.food_spinner_item_laylout, s);
		fourth_spinner.setAdapter(adapter);
		fourth_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				((TextView) view).setGravity(Gravity.CENTER);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}

	// 设置4个spinner>>>>>>>>>>>>

	public void setTitleBackImgListener(View v) {
		startActivity(new Intent(this, TabHostActivity.class));
		finish();
	}

}
