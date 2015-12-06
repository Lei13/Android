package com.scxh.android.service;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyStartService extends Service {
	public  MediaPlayer player;

	@Override
	public void onCreate() {
		super.onCreate();
		if (player == null) {
			player = new MediaPlayer();
		}

		try {
			player.setDataSource("file://mnt/sdcard/lie.mp3");
			player.prepare();
			player.start();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			return super.onStartCommand(intent, flags, startId);
		}
		int num = intent.getIntExtra("start", 0);
		switch (num) {
		case 1:
			player.start();
			break;

		case 2:
			player.pause();
			break;
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
