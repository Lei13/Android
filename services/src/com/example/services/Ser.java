package com.example.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

public class Ser extends Service {
	ServiceBinder mServiceBinder = new ServiceBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mServiceBinder;
	}

	public class ServiceBinder extends IService.Stub {
		Handler han = new Handler();

		@Override
		public void getString() throws RemoteException {

			han.post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(Ser.this, "success", Toast.LENGTH_SHORT)
							.show();

				}
			});
		}

	}

}
