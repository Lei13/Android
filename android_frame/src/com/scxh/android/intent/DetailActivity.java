package com.scxh.android.intent;

import com.scxh.android.frame.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends Activity {
	TextView mPersonShowtxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intent_detail_layout);
		mPersonShowtxt = (TextView) findViewById(R.id.text_details);
	}
}
