package com.scxh.android.notification;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
	private MediaPlayer mPlayer;

	@Override
	public void onCreate() {
		super.onCreate();
		mPlayer = new MediaPlayer();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// intent传过来的参数进行区分
		int code = intent.getIntExtra("music_state", 0);
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
		}
		switch (code) {
		case 0:
			try {
				mPlayer.reset();
				mPlayer.setDataSource("file://mnt/sdcard/Adele - Someone Like You.mp3");
				mPlayer.prepare();
				mPlayer.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			break;
		case 1:
			if (mPlayer.isPlaying()) {
				mPlayer.pause();
			}
			break;
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
