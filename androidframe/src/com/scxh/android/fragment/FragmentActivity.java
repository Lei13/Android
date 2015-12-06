package com.scxh.android.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.scxh.android.frame.R;

public class FragmentActivity extends android.support.v4.app.FragmentActivity {
	private RadioGroup mRadioGroup;
	private ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_fragment_activity_layout);
		initView();
		ArrayList<Fragment> pagerData = new ArrayList<Fragment>();
		pagerData.add(new MyFragment());
		pagerData.add(new Fragment_two());
		setPagerAdapter(pagerData);

		setRadioListener();
		setPagerListener();
	}

	private void initView() {
		mRadioGroup = (RadioGroup) findViewById(R.id.fragment_radios);
		mPager = (ViewPager) findViewById(R.id.fragment_pager);
	}

	private void setPagerAdapter(ArrayList<Fragment> data) {
		android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		PagerAdapter adapter = new PagerAdapter(manager);
		mPager.setAdapter(adapter);
		adapter.setData(data);
	}

	private void setPagerListener(){
		mPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				mRadioGroup.getChildAt(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	private void setRadioListener() {
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.fragment_btn1:
					mPager.setCurrentItem(0);
					break;

				case R.id.fragment_btn2:
					mPager.setCurrentItem(1);
					break;

				case R.id.fragment_btn3:
					mPager.setCurrentItem(0);
					break;

				}
			}
		});
	}

	public class PagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> data = new ArrayList<Fragment>();

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public void setData(ArrayList<Fragment> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public android.support.v4.app.Fragment getItem(int arg0) {
			return data.get(arg0);
		}

		@Override
		public int getCount() {
			return data.size();
		}

	}
}
