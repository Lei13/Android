package com.android.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

public class MainActivity extends Activity {
	MyPagerView mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPager = (MyPagerView) findViewById(R.id.mypager);
		ImageView img =  (ImageView) LayoutInflater.from(this).inflate(R.layout.image_layout, null);
		mPager.addView(img, R.drawable.ic_launcher);
	}
}
