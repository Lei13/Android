package com.scxh.android.ui.tabhost;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.scxh.android.frame.R;
import com.scxh.android.gridview.GridViewActivity;
import com.scxh.android.listview.DefinedActivity;
import com.scxh.android.listview.SimpleAdapterActivity;
import com.scxh.android.ui.wedget.ViewPagerActivity;

public class MyTabHostActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_mytab_layout);
		// initTabs();
		myTabs();
		
	}

	/*
	 * 用系统自己的
	 */
	private void initTabs() {
		
		TabHost tabHost = getTabHost();
		//页卡标签，自定义页卡时，通过mTabHost.setCurrentTabByTag("tab1");将两者联系起来
		TabSpec tabspec1 = tabHost.newTabSpec("tab1");
		tabspec1.setIndicator("main");//设置tab页卡的名字
		tabspec1.setContent(new Intent(this, DefinedActivity.class));//响应事件，启动activity

		TabSpec tabspec2 = tabHost.newTabSpec("tab2");
		tabspec2.setIndicator("first");
		tabspec2.setContent(new Intent(this, ViewPagerActivity.class));

		TabSpec tabspec3 = tabHost.newTabSpec("tab3");
		tabspec3.setIndicator("last");
		tabspec3.setContent(new Intent(this, SimpleAdapterActivity.class));

		tabHost.addTab(tabspec1);
		tabHost.addTab(tabspec2);
		tabHost.addTab(tabspec3);
	}

	private void myTabs() {

		TabHost tabHost = getTabHost();
		TabSpec tabspec1 = tabHost.newTabSpec("tab1");
		tabspec1.setIndicator(myTabs("first", R.drawable.ic_launcher));
		tabspec1.setContent(new Intent(this, DefinedActivity.class));

		TabSpec tabspec2 = tabHost.newTabSpec("tab2");
		tabspec2.setIndicator(myTabs("second", R.drawable.tabs_src_click_selector));
		tabspec2.setContent(new Intent(this, ViewPagerActivity.class));

		TabSpec tabspec3 = tabHost.newTabSpec("tab3");
		tabspec3.setIndicator(myTabs("first", R.drawable.ic_launcher));
		tabspec3.setContent(new Intent(this, GridViewActivity.class));

		tabHost.addTab(tabspec1);
		tabHost.addTab(tabspec2);
		tabHost.addTab(tabspec3);

	}

	private View myTabs(String title, int src) {
		View v = LayoutInflater.from(this).inflate(
				R.layout.tabhost_tabs_layout, null);
		TextView title1 = (TextView) v.findViewById(R.id.tabs_title);
		title1.setText(title);
		
		ImageView img = (ImageView) v.findViewById(R.id.tabs_img);
		img.setImageResource(src);
		img.setScaleType(ScaleType.FIT_XY);
		return v;
	}
}
