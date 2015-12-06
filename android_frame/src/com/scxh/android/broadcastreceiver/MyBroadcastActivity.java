package com.scxh.android.broadcastreceiver;

import com.scxh.android.frame.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class MyBroadcastActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybroadcastactivity_layout);

		MyReceiver receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);

	}

	class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
				Toast.makeText(context, "收到一条信息", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
