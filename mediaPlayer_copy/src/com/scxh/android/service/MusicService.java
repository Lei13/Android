package com.scxh.android.service;

import java.io.IOException;
import java.util.ArrayList;

import com.scxh.android.music.PlayActivity;
import com.scxh.android.music.R;

import Constance.MusicBean;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

public class MusicService extends Service {
	MusicReciever receiver;
	MediaPlayer mPlayer;

	boolean mIsPlaying = true;
	ArrayList<MusicBean> mListData;
	int currentPosition;

	@Override
	public void onCreate() {
		super.onCreate();

	}

	/*
	 * 初始化MediaPlayer
	 */
	private void initPlayer() {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
		}
		try {
			mPlayer.reset();
			mPlayer.setDataSource(mListData.get(currentPosition).getPath());
			mPlayer.prepare();
			mPlayer.start();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				if (++currentPosition > mListData.size() - 1) {
					currentPosition = 0;
				}

				mp.reset();
				initPlayer();

			}
		});
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mListData = intent.getParcelableArrayListExtra("musicList");
		currentPosition = intent.getIntExtra("currentPosition", 0);

		initPlayer();
		registerReceiver();
		if (mPlayer != null) {
			getTime();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void getTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (mPlayer.isPlaying()) {
					while (true) {
						Intent intent = new Intent(
								PlayActivity.ACTION_MUSIC_TITLE_TIME);
						intent.putExtra("musicTime",
								mPlayer.getCurrentPosition());
						intent.putExtra("musicTotalTime", mPlayer.getDuration());
						intent.putExtra("musicName",
								mListData.get(currentPosition).getName());
						sendBroadcast(intent);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		}).start();

	}

	
	
	
	
	
	
	
	public class MusicReciever extends BroadcastReceiver {
		public static final String ACTION_PLAY = "com.com.scxh.android.service.musicreceiver.play";
		public static final String ACTION_CHANGEPROGRESS = "com.com.scxh.android.service.musicreceiver.progress";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (ACTION_PLAY.equals(action)) {
				switch (intent.getIntExtra("play", 0)) {
				case 0://播放or
					if (mPlayer!=null) {
						if (mPlayer.isPlaying()) {
							mPlayer.pause();
						}else {
							mPlayer.start();
						}
						
						
					}
					
					break;
				case 1://暂停
					if (mPlayer!=null) {
						
					}
					
					break;
				case 2://上一首
					if (--currentPosition < 0) {
						currentPosition = 0;
					}
					mPlayer.reset();
					initPlayer();
					
					break;

				case 3://下一首
					if (++currentPosition > mListData.size() - 1) {
						currentPosition = mListData.size() - 1;
					}
					mPlayer.reset();
					initPlayer();
					break;
				default:
					break;
				}

			}  else if (action.equals(ACTION_CHANGEPROGRESS)) {
				int currentProgress = intent.getIntExtra("currentProgress", 0);
				if (mPlayer != null) {
					mPlayer.seekTo(currentProgress);
				}
			}

		}
	}

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter(MusicReciever.ACTION_PLAY);
		receiver = new MusicReciever();
		registerReceiver(receiver, filter);
		filter = new IntentFilter(MusicReciever.ACTION_CHANGEPROGRESS);
		registerReceiver(receiver, filter);

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			unregisterReceiver(receiver);
		}
	}
}
