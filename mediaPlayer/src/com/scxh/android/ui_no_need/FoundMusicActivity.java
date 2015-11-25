package com.scxh.android.ui_no_need;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.scxh.android.music.R;
import com.scxh.android.music.R.drawable;
import com.scxh.android.music.R.id;
import com.scxh.android.music.R.layout;
import com.scxh.android.music.adapter.MusicPagerAdapter;

public class FoundMusicActivity extends Activity {
	ArrayList<ImageView> dotData = new ArrayList<ImageView>();
	LayoutInflater inflater;
	int oldPosition = 0;
	ImageView dot1, dot2, dot3;
	GridView grid;
	ViewPager findPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fondmusic_activity_layout);
		initView();

		findPager.setCurrentItem(0);
		dotData.get(oldPosition).setImageResource(R.drawable.dot_pressed);

		setPagerListener();
		setPagerAdapter();

	}

	@SuppressWarnings("deprecation")
	private void setPagerListener() {
		findPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				dotData.get(oldPosition).setImageResource(
						R.drawable.dot_default);
				dotData.get(arg0).setImageResource(R.drawable.dot_pressed);
				oldPosition = arg0;

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void setPagerAdapter() {
		ArrayList<View> data = new ArrayList<View>();
		MusicPagerAdapter adapter = new MusicPagerAdapter();
		View item1 = inflater.inflate(R.layout.item1_findpager_layout, null);
		View item2 = inflater.inflate(R.layout.item2_findpager_layout, null);
		View item3 = inflater.inflate(R.layout.item3_findpager_layout, null);
		data.add(item1);
		data.add(item2);
		data.add(item3);
		adapter.setData(data);
		findPager.setAdapter(adapter);
	}

	private void initView() {
		grid = (GridView) findViewById(R.id.grid_found);
		findPager = (ViewPager) findViewById(R.id.pager_found);
		dot1 = (ImageView) findViewById(R.id.dot1_find_img);
		dot2 = (ImageView) findViewById(R.id.dot2_find_img);
		dot3 = (ImageView) findViewById(R.id.dot3_find_img);
		dotData.add(dot1);
		dotData.add(dot2);
		dotData.add(dot3);
	}
}
