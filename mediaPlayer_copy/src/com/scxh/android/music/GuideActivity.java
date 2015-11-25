package com.scxh.android.music;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class GuideActivity extends Activity {
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_activity_layout);
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
					startActivity(new Intent(GuideActivity.this,
							MainActivity.class));
					finish();
				
			}
		},1000);
	}
}
