package com.scxh.android.frame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends Activity {
	Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Log.v("tag", "handleMessage " + msg.obj);
			mTxt.setText("" + msg.obj);
		}
	};
	TextView mTxt;
	Button mBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.aync_download_activity_layout);

		mTxt = (TextView) findViewById(R.id.isdown_txt);

		mBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int k = 2;
				while (k > 0) {
					k--;
					Log.v("tag", "111111111");
					Message msg = Message.obtain();
					msg.obj = "消息" + k;
					hander.sendMessageDelayed(msg, 3000);
				}
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
	}
}
