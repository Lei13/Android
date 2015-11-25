package com.scxh.android.service;

import java.io.IOException;
import java.util.ArrayList;

import Constance.MusicBean;
import Constance.MusicList;
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

public class MusicService extends Service {
	Intent intent;
	MediaPlayer mPlayer;
	// �ӿڵ�ʵ��
	MusicServiceBinder musicBinder = new MusicServiceBinder();

	// ����Դ �͵�ǰ����λ�� �Ǵ� activity��ͨ��startService��������
	ArrayList<MusicBean> mListData = MusicList.mListData;
	int currentPosition = 0;
	boolean playState = true;

	@Override
	public void onCreate() {
		super.onCreate();
/**ע��㲥*/
		MusicReciever musicReceiver = new MusicReciever();
		IntentFilter filter = new IntentFilter();
		filter.addAction(MusicReciever.ACTION_PLAY);
		filter.addAction(MusicReciever.ACTION_LAST);
		filter.addAction(MusicReciever.ACTION_NEXT);
		registerReceiver(musicReceiver, filter);

		mPlayer = new MediaPlayer();
	}

	/*
	 * MediaPlayer �ĳ�ʼ��
	 */
	private void initPlayer() {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();

		}
		try {
			mPlayer.reset();
			mPlayer.setDataSource(mListData.get(currentPosition).getPath());
			mPlayer.prepare();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * һ�׸����������Ժ�ķ���
		 */
		mPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				/** ���͹㲥 */

				musicBinder.nextMusic();
				mp.start();
			}
		});
	}

	/*
	 * ���մ�activity������������Դ��position
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		currentPosition = intent.getIntExtra("currentPosition", 0);
		playState = intent.getBooleanExtra("MUSIC_STATE", false);
		initPlayer();

		if (playState == false) {

		} else {
			mPlayer.start();
			setMusicNotification();
		}
		sendNotify();// �������淢��֪ͨ
		return super.onStartCommand(intent, flags, startId);
	}

	/*
	 * ����һ���ӿڵ�ʵ��
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return musicBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.stop();
			mPlayer.release();
		}
	}

	/*
	 * ���x�Ľӿ�
	 */
	public interface MusicBinder {
		// �õ�player�Ĳ���״̬
		public boolean playMusic();

		// �����һ��
		public int nextMusic();

		// �����һ��
		public int lastMusic();

		// �õ���ǰ���ŵ�ʱ��
		public int currentTime();

		// �õ���ǰ��������ʱ��
		public int TotalTime();

		// ��ǰѡ�еĸ�������
		public String seekTo(int position);

		// �õ����ڲ��Ÿ���������
		public String getCurrentTitle();

		// �϶����������������Ĳ���λ��
		public void setCurrentTime(int progress);

	}

	/*
	 * �ӿ�ʵ���࣬�̳е�binder
	 */
	public class MusicServiceBinder extends Binder implements MusicBinder {
		@Override
		public boolean playMusic() {
			if (mPlayer.isPlaying()) {
				mPlayer.pause();
				setMusicNotification();
				return false;
			} else {
				mPlayer.start();
				setMusicNotification();
				return true;
			}
		}

		@Override
		public int nextMusic() {
			if (++currentPosition > mListData.size() - 1) {
				currentPosition = mListData.size() - 1;
			}
			mPlayer.reset();
			initPlayer();
			playMusic();
			return currentPosition;
		}

		@Override
		public int lastMusic() {
			if (--currentPosition < 0) {
				currentPosition = 0;
			}
			mPlayer.reset();
			initPlayer();
			playMusic();
			return currentPosition;
		}

		@Override
		public int currentTime() {
			if (mPlayer != null) {
				return mPlayer.getCurrentPosition();
			}
			return 0;
		}

		@Override
		public int TotalTime() {
			if (mPlayer != null) {
				return mPlayer.getDuration();
			}
			return 0;
		}

		@Override
		public void setCurrentTime(int progress) {
			if (mPlayer != null) {
				mPlayer.seekTo(progress);
			}

		}

		@Override
		public String seekTo(int position) {
			if (mPlayer != null) {
				mPlayer.reset();
				try {
					mPlayer.setDataSource(mListData.get(position).getPath());
					mPlayer.prepare();
					currentPosition = position;
					mPlayer.start();
					setMusicNotification();
					return mListData.get(position).getName();
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
			return null;
		}

		@Override
		public String getCurrentTitle() {

			return mListData.get(currentPosition).getName();
		}

	}

	/*
	 * ѭ�������淢�㲥 ���²���״̬�͸�����
	 */
	private void sendNotify() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (mPlayer != null) {
					while (true) {
						intent = new Intent(MusicGuideReceiver.ACTION_RECEIVER);
						intent.putExtra(MusicGuideReceiver.UPDATE_TITLE,
								mListData.get(currentPosition).getName() + "");
						intent.putExtra(MusicGuideReceiver.UPDATE_PALY_STATE,
								mPlayer.isPlaying());
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

	/*
	 * ֪ͨ��Ϊreceiver
	 */
	public class MusicReciever extends BroadcastReceiver {
		public static final String ACTION_PLAY = "com.com.scxh.android.service.musicreceiver.play";
		public static final String ACTION_LAST = "com.com.scxh.android.service.musicreceiver.last";
		public static final String ACTION_NEXT = "com.com.scxh.android.service.musicreceiver.next";

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_PLAY.equals(action)) {
				if (mPlayer != null) {
					if (mPlayer.isPlaying()) {
						mPlayer.pause();
					} else {
						mPlayer.start();
					}
					setMusicNotification();
				}
			} else if (ACTION_LAST.equals(action)) {
				if (--currentPosition < 0) {
					currentPosition = 0;
				}
				mPlayer.reset();
				initPlayer();
				mPlayer.start();
				setMusicNotification();

			} else if (ACTION_NEXT.equals(action)) {
				if (++currentPosition > mListData.size() - 1) {
					currentPosition = mListData.size() - 1;
				}
				mPlayer.reset();
				initPlayer();
				mPlayer.start();
				setMusicNotification();
			}

			/*
			 * else if (action.equals(ACTION_CHANGEPROGRESS)) { int
			 * currentProgress = intent.getIntExtra("currentProgress", 0); if
			 * (mPlayer != null) { mPlayer.seekTo(currentProgress); } }
			 */

		}
	}

	RemoteViews views;

	public void setMusicNotification() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.ic_launcher);

		views = new RemoteViews(getPackageName(),
				R.layout.music_notification_layout);
		builder.setContent(views);

		if (mPlayer.isPlaying()) {
			views.setImageViewResource(R.id.paly_notify_btn,
					R.drawable.widget_pause);
		} else {
			views.setImageViewResource(R.id.paly_notify_btn,
					R.drawable.widget_play);
		}
		if (mPlayer != null) {
			views.setTextViewText(R.id.title_notify_txt,
					mListData.get(currentPosition).getName());
		}

		/** ������Ϊ **/
		intent = new Intent(MusicReciever.ACTION_PLAY);
		PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.paly_notify_btn, pIntent);

		intent = new Intent(MusicReciever.ACTION_LAST);
		pIntent = PendingIntent.getBroadcast(this, 1, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.last_notify_btn, pIntent);

		intent = new Intent(MusicReciever.ACTION_NEXT);
		pIntent = PendingIntent.getBroadcast(this, 2, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.next_notify_btn, pIntent);

		NotificationManager manager = (NotificationManager) getSystemService("notification");
		manager.notify(1, builder.build());
	}

}
