package com.example.activity;

import com.example.services.IService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Client extends Activity implements OnClickListener {
	Button mButton1, mButton2;
	IService bind;

	ServiceConnection conn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mButton1 = (Button) findViewById(R.id.button);
		mButton2 = (Button) findViewById(R.id.register);

		mButton1.setOnClickListener(this);
		mButton2.setOnClickListener(this);
		
		conn = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				bind = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				bind = IService.Stub.asInterface(service);
				Log.v("tag", "bind :  " + bind.toString());
			}
		};
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register:
			bindService(new Intent("com.example.services.IService"), conn,
					BIND_AUTO_CREATE);
			Log.v("tag", "register :  ");
			break;

		case R.id.button:
			try {
				Log.v("tag", "click :  " + bind);
				if (bind != null) {
					Log.v("tag", "click :  " + bind);
					bind.getString();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		}
	}

}
