package com.scxh.android.ui_no_need;

import java.io.IOException;
import java.util.ArrayList;

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
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.scxh.android.music.R;

public class ServiceNTY {
	// 通知与播放同步
}

class MusicService extends Service {
	public static final String ACTION_PLAYER_MUSIC = "com.scxh.android1503.ACTION_PLAYER_MUSIC";
	private MediaPlayer mMediaPlayer;
	private ArrayList<MusicBean> musicPathLists;
	private int currentPostion = 0;


	@Override
	public void onCreate() {
		super.onCreate();

		/** 注册广播 */
		IntentFilter filter = new IntentFilter(ACTION_PLAYER_MUSIC);
		registerReceiver(playerReceiver, filter);

		new Thread(new PlayerServiceThread()).start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (musicPathLists == null) {
			musicPathLists = intent.getParcelableArrayListExtra("MUSIC_LIST");
		}
		currentPostion = intent.getIntExtra("CURRENT_POSTION", 0);

		initMusic();

		playerMusic();

		return super.onStartCommand(intent, flags, startId);
	}


	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
		}
		/** 注销广播 */
		if (playerReceiver != null) {
			unregisterReceiver(playerReceiver);
		}
		/** 结束更新线程 */
		playerThreadFlag = false;
	}

	public void initMusic() {
		if (mMediaPlayer == null)
			mMediaPlayer = new MediaPlayer();
		try {

			mMediaPlayer.reset();

			mMediaPlayer.setDataSource(musicPathLists.get(currentPostion)
					.getPath());
			mMediaPlayer.prepare();

			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.start();
				}
			});

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

	public boolean playerMusic() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
			return false;
		} else {
			mMediaPlayer.start();
			notificationMusicPlayer();
			return true;
		}
	}

	/**
	 * 定义广播接收者
	 */
	public BroadcastReceiver playerReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (ACTION_PLAYER_MUSIC.equals(intent.getAction())) {
				boolean playerState = intent.getBooleanExtra("PLAYSER_STATE",
						true);
				if (playerState) {
					mMediaPlayer.start();
					notificationMusicPlayer();
				} else {
					mMediaPlayer.pause();
					notificationMusicPlayer();
				}
			}
		}

	};
	private boolean playerThreadFlag = true;

	/** 更新线程开关 */
	public class PlayerServiceThread implements Runnable {
		@Override
		public void run() {
			while (playerThreadFlag) {
				if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
					int currentTime = mMediaPlayer.getCurrentPosition();
					int totalTime = mMediaPlayer.getDuration();

					Intent intent = new Intent(
							"MusicActivity.ACTION_PROGRESS_RECEIVER");
					intent.putExtra("CURRENT_TIME", currentTime);
					intent.putExtra("TOTALE_TIME", totalTime);
					sendBroadcast(intent);
				}
				/** 每秒更新一次 */
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 音乐播放通知
	 */
	public void notificationMusicPlayer() {
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.background);
		RemoteViews views = null ;
		builder.setContent(views);

		if (mMediaPlayer.isPlaying()) {
			views.setImageViewResource(3, R.drawable.widget_play);
		} else {
			views.setImageViewResource(2, R.drawable.widget_pause);
		}

		/** notification通知行为 */
		Intent intent = new Intent(MusicService.ACTION_PLAYER_MUSIC);
		if (mMediaPlayer.isPlaying()) {
			intent.putExtra("PLAYSER_STATE", false);
		} else {
			intent.putExtra("PLAYSER_STATE", true);
		}
		PendingIntent pIntent = PendingIntent.getBroadcast(this, 1, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(2, pIntent);

		manager.notify(111, builder.build());
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
