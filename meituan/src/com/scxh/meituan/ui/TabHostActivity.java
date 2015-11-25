package com.scxh.meituan.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class TabHostActivity extends android.app.TabActivity {
	TabHost mTabHost;
	TextView mDeal, mBusiness, mMine, mMore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_layout);
		setTabs();
		initView();

	}

	@SuppressWarnings("deprecation")
	private void setTabs() {
		mTabHost = getTabHost();
		TabSpec tab1 = mTabHost.newTabSpec("tab1");
		tab1.setIndicator("tab1");
		tab1.setContent(new Intent(this, MainActivity.class));
		TabSpec tab2 = mTabHost.newTabSpec("tab2");
		tab2.setIndicator("tab2");
		tab2.setContent(new Intent(this, MainActivity.class));

		TabSpec tab3 = mTabHost.newTabSpec("tab3");
		tab3.setIndicator("tab3");
		tab3.setContent(new Intent(this, MineActivity.class));

		TabSpec tab4 = mTabHost.newTabSpec("tab4");
		tab4.setIndicator("tab4");
		tab4.setContent(new Intent(this, MoreActivity.class));

		mTabHost.addTab(tab1);
		mTabHost.addTab(tab2);
		mTabHost.addTab(tab3);
		mTabHost.addTab(tab4);
	}

	private void initView() {
		mDeal = (TextView) findViewById(R.id.main_deal_tab1);
		mBusiness = (TextView) findViewById(R.id.main_business_tab1);
		mMine = (TextView) findViewById(R.id.main_mine_tab1);
		mMore = (TextView) findViewById(R.id.main_more_tab1);
	}

	/*
	 * onClick 方式设置监听
	 */
	public void tabTxtClickListener(View view) {
		switch (view.getId()) {
		case R.id.main_deal_tab1:
			firstSelect();
			mTabHost.setCurrentTabByTag("tab1");
			break;

		case R.id.main_business_tab1:
			secondSelect();
			mTabHost.setCurrentTabByTag("tab2");
			break;
		case R.id.main_mine_tab1:
			thirdSelect();
			mTabHost.setCurrentTabByTag("tab3");
			break;
		case R.id.main_more_tab1:
			fourthSelect();
			mTabHost.setCurrentTabByTag("tab4");
			break;
		}
	}

	private void firstSelect() {
		mDeal.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_deal_on), null, null);
		mDeal.setTextColor(getResources().getColor(R.color.tab_pressed));
		mBusiness.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_poi_off), null, null);
		mBusiness.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_user_off), null, null);
		mMine.setTextColor(getResources().getColor(android.R.color.darker_gray));
		mMore.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_more_off), null, null);
		mMore.setTextColor(getResources().getColor(android.R.color.darker_gray));
	}

	private void secondSelect() {
		mBusiness.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_poi_on), null, null);
		mBusiness.setTextColor(getResources().getColor(R.color.tab_pressed));
		mDeal.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_deal_off), null, null);
		mDeal.setTextColor(getResources().getColor(android.R.color.darker_gray));
		mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_user_off), null, null);
		mMine.setTextColor(getResources().getColor(android.R.color.darker_gray));
		mMore.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_more_off), null, null);
		mMore.setTextColor(getResources().getColor(android.R.color.darker_gray));
	}

	private void thirdSelect() {

		mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_user_on), null, null);
		mMine.setTextColor(getResources().getColor(R.color.tab_pressed));
		mDeal.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_deal_off), null, null);
		mDeal.setTextColor(getResources().getColor(android.R.color.darker_gray));
		mBusiness.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_poi_off), null, null);
		mBusiness.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		mMore.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_more_off), null, null);
		mMore.setTextColor(getResources().getColor(android.R.color.darker_gray));
	}

	private void fourthSelect() {
		mMore.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_more_on), null, null);
		mMore.setTextColor(getResources().getColor(R.color.tab_pressed));
		mDeal.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_deal_off), null, null);
		mDeal.setTextColor(getResources().getColor(android.R.color.darker_gray));
		mBusiness.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_poi_off), null, null);
		mBusiness.setTextColor(getResources().getColor(
				android.R.color.darker_gray));
		mMine.setCompoundDrawablesWithIntrinsicBounds(null, getResources()
				.getDrawable(R.drawable.ic_menu_user_off), null, null);
		mMine.setTextColor(getResources().getColor(android.R.color.darker_gray));
	}

	private static Boolean isExit = false;
	private static Boolean hasTask = false;

	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, "点击两次退出", Toast.LENGTH_SHORT).show();
				if (!hasTask) {
					tExit.schedule(task, 3000);
				}
			} else {
				finish();
				System.exit(0);
			}
		}
		return false;
	}
}
