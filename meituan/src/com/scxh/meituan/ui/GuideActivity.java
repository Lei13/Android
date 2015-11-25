package com.scxh.meituan.ui;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class GuideActivity extends Activity {
	ArrayList<View> mData;
	ArrayList<View> mDotData;
	int oldPosition;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_viewpager_main_laylout);
		mViewPager = (ViewPager) findViewById(R.id.guide_viewpager);
		setDotData();
		setAdapter();
		mDotData.get(0).setBackgroundResource(R.drawable.dot_choice);
		setViewPagerListener();
	}

	@SuppressWarnings("deprecation")
	private void setViewPagerListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mDotData.get(arg0).setBackgroundResource(R.drawable.dot_choice);
				mDotData.get(oldPosition).setBackgroundResource(
						R.drawable.dot_no_choice);
				oldPosition = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				//int time = mData.size()-1;
				if (arg0 == 2) {
					startActivity(new Intent(GuideActivity.this,
							TabHostActivity.class));
					finish();
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void setAdapter() {
		MyPagerAdapter adapter = new MyPagerAdapter();
		adapter.setData(setData());
		mViewPager.setAdapter(adapter);
	}

	private void setDotData() {
		mDotData = new ArrayList<View>();
		mDotData.add(this.findViewById(R.id.dot_img1));
		mDotData.add(findViewById(R.id.dot_img2));
		mDotData.add(findViewById(R.id.dot_img3));
	}

	private ArrayList<View> setData() {
		mData = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		View v1 = inflater.inflate(R.layout.guide_item1_laylout, null);
		View v2 = inflater.inflate(R.layout.guide_item2_laylout, null);
		View v3 = inflater.inflate(R.layout.guide_item3_laylout, null);
		mData.add(v1);
		mData.add(v2);
		mData.add(v3);
		return mData;
	}

	public class MyPagerAdapter extends PagerAdapter {
		ArrayList<View> data = new ArrayList<View>();

		@Override
		public int getCount() {
			return data.size();
		}

		private void setData(ArrayList<View> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = data.get(position);
			container.addView(v);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(data.get(position));
		}

	}
}
