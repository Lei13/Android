package com.scxh.android.service;

import com.scxh.android.frame.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;

public class MyStartServiceActivity extends Activity {
	SeekBar bar;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:

				break;

			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mystart_service_layout);
		bar = (SeekBar) findViewById(R.id.progress_bar);

	}

	public void setServiceBtnListener(View view) {
		switch (view.getId()) {
		case R.id.start_btn:
			Intent intent = new Intent(this,MyStartService.class);
			intent.putExtra("start", 1);
			startService(intent);
			break;

		case R.id.pause_btn:
			intent = new Intent(this,MyStartService.class);
			intent.putExtra("start", 2);
			startService(intent);
			break;
		case R.id.stop_btn:
			intent = new Intent(this,MyStartService.class);
			stopService(intent);
			break;
		}
	}
}
