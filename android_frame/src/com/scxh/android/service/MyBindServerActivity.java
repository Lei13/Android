package com.scxh.android.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.scxh.android.frame.R;
import com.scxh.android.service.MyBindService.ServiceIBinder;

public class MyBindServerActivity extends Activity {
	TextView mShow;
	ServiceIBinder serviceBinder;
	ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serviceBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			serviceBinder = (ServiceIBinder) service;
			Log.v("Connected","serviceBinder:  " +serviceBinder);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myservice_activity_layout);
		mShow = (TextView) findViewById(R.id.show_service_txt);
		bindService(new Intent(this, MyBindService.class), connection, BIND_AUTO_CREATE);

	}

	public void buttonClickListener(View view) {
		switch (view.getId()) {
		case R.id.set_service_btn:
			int count = 30;
			serviceBinder.setCount(count);

			break;

		case R.id.get_service_btn:
			int s = serviceBinder.getCount();
			mShow.setText(s+"");
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (serviceBinder != null) {
			unbindService(connection);
		}
	}

}
