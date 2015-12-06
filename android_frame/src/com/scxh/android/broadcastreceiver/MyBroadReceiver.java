package com.scxh.android.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadReceiver extends BroadcastReceiver {
	public static final String ACTION_SEND_SMS = "com.scxh.android.broadcastreceiver.send_sms";
	public static final String ACTION_CALL = "com.scxh.android.broadcastreceiver.call";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(ACTION_SEND_SMS)) {
			Toast.makeText(context, "you have message", Toast.LENGTH_SHORT)
					.show();
		} else if (action.equals(ACTION_CALL)) {
			Toast.makeText(context, "you have message", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
