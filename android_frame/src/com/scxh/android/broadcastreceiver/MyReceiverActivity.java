package com.scxh.android.broadcastreceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.scxh.android.frame.R;

public class MyReceiverActivity extends Activity{
	Button mSendBroadcastBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybroadcastreceiver_layout);
		mSendBroadcastBtn = (Button) findViewById(R.id.send_broadcast_btn);


		mSendBroadcastBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyBroadReceiver.ACTION_SEND_SMS);
				sendBroadcast(intent);
			}
		});
	}

}
