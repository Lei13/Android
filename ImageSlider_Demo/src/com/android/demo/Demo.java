package com.android.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.warmtel.android.slider.R;


public class Demo extends Activity {
	ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_activity_layout);
		mListView = (ListView) findViewById(R.id.demo_listview);
		
	}

	
	
	
}
