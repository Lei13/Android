package com.scxh.android.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

import com.scxh.android.frame.R;
import com.scxh.android.listview.DefinedActivity;

public class NotificationActivity extends Activity implements OnClickListener {
	private NotificationManager manager;
	private NotificationCompat.Builder builder;
	private Button mSendNtfBtn, mGuideNtfBtn, mUpdateNtfBtn, mDefinedNtfBtn;
	private Button mBigNtfBtn, mDownBtn;
	private final int NORMAL_NOTIFY_ID = 0;
	private final int GUIDE_NOTIFY_ID = 1;
	private final int BIGVIEW_NOTIFY_ID = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_layout);

		builder = new NotificationCompat.Builder(NotificationActivity.this);
		manager = (NotificationManager) getSystemService("notification");

		mSendNtfBtn = (Button) findViewById(R.id.send_notification_btn);
		mGuideNtfBtn = (Button) findViewById(R.id.guide_notification_btn);
		mUpdateNtfBtn = (Button) findViewById(R.id.update_notification_btn);
		mDefinedNtfBtn = (Button) findViewById(R.id.defined_notification_btn);
		mBigNtfBtn = (Button) findViewById(R.id.bigview_notification_btn);
		mDownBtn = (Button) findViewById(R.id.download_notification_btn);

		mSendNtfBtn.setOnClickListener(this);
		mGuideNtfBtn.setOnClickListener(this);
		mUpdateNtfBtn.setOnClickListener(this);
		mDefinedNtfBtn.setOnClickListener(this);
		mBigNtfBtn.setOnClickListener(this);
		mDownBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*
		 * 常规通知
		 */
			case R.id.send_notification_btn:
				// NotificationCompat.Builder builder = new
				// NotificationCompat.Builder(
				// NotificationActivity.this);
			/* 通知内容 */
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("你有一条新消息");// 收到通知提示
				builder.setContentTitle("双十一大会场");// 通知的标题
				builder.setContentText("全场5折，欢迎抢购");// 通知内容
				// 通知信息的详细内容，威武text的右边，太长，text会显示不完全
				builder.setContentInfo("content info");
				//builder.setAutoCancel(true);// 响应后自动消失
			/* 通知行为 */
				Intent intent = new Intent(NotificationActivity.this,
						DefinedActivity.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(
						NotificationActivity.this, 0, intent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				builder.setContentIntent(pendingIntent);
				builder.setOngoing(false);
				// 发送通知
				manager.notify(NORMAL_NOTIFY_ID, builder.build());
				break;

		/*
		 * 导航通知 TaskStackBuilder
		 */
			case R.id.guide_notification_btn:

				builder.setSmallIcon(R.drawable.meituan_image8);
				builder.setTicker("你有一条导航通知");
				builder.setContentTitle("导航通知： 双十一大会场");
				builder.setContentText("全场5折，欢迎抢购");
				builder.setContentInfo("content info");
				builder.setAutoCancel(true);
				builder.setSound(Uri
						.parse("file://mnt/sdcard/Adele - Someone Like You.mp3"));

				intent = new Intent(this, DefinedActivity.class);
				TaskStackBuilder sBuilder = TaskStackBuilder.create(this);
				sBuilder.addParentStack(DefinedActivity.class);
				sBuilder.addNextIntent(intent);

				PendingIntent pIntent = sBuilder.getPendingIntent(1,
						PendingIntent.FLAG_UPDATE_CURRENT);
				builder.setContentIntent(pIntent);

				manager = (NotificationManager) getSystemService("notification");
				manager.notify(GUIDE_NOTIFY_ID, builder.build());

				break;

			case R.id.update_notification_btn:
				builder.setSmallIcon(R.drawable.meituan_image1);
				builder.setTicker("你有一条新消息");
				builder.setContentTitle("圣诞欢乐购");
				builder.setContentText("吃喝玩乐");
				builder.setAutoCancel(true);

				manager.notify(NORMAL_NOTIFY_ID, builder.build());
				break;
			case R.id.defined_notification_btn:
				builder.setSmallIcon(R.drawable.meituan_image1);
				builder.setContentTitle("元旦");
				//将自定义布局转换成RemoteView，调用builder.setContent(view);注：通知是具有一定高度的，
				RemoteViews view = new RemoteViews(getPackageName(),
						R.layout.defined_notification_layout);

				//构造要某个view的响应事件
				Intent playIntent = new Intent(this, MusicService.class);
				playIntent.setAction("music_start");
				playIntent.putExtra("music_state", 0);// 0 palying
				PendingIntent pPlayIntent = PendingIntent.getService(this, 1,
						playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				//对自定义通知布局中的一个button进行点击处理，
				view.setOnClickPendingIntent(R.id.palying_btn, pPlayIntent);

				Intent pauseIntent = new Intent(this, MusicService.class);
				pauseIntent.setAction("music_pause");
				pauseIntent.putExtra("music_state", 1);// 1 pause
				PendingIntent pPauseIntent = PendingIntent.getService(this, 1,
						pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				view.setOnClickPendingIntent(R.id.next_song_btn, pPauseIntent);

				builder.setContent(view);
				manager.notify(15, builder.build());
				break;
			case R.id.bigview_notification_btn:
			/*builder.setSmallIcon(R.drawable.meituan_image1);
			builder.setTicker("你有一条big消息");
			builder.setContentTitle("元旦");
			builder.setContentText("新年新气象，又该添新衣了");
			// builder.setAutoCancel(true);
			builder.setStyle(new NotificationCompat.BigTextStyle().bigText("music"));
			//注意一定要给intent设置action，他传的参数才能被接收到
			Intent startIntent = new Intent(this, MusicService.class);
			startIntent.setAction("music_start");
			startIntent.putExtra("music_state", 0);// 0 palying
			PendingIntent pStartIntent = PendingIntent.getService(this, 1,
					startIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			Intent pauseIntent = new Intent(this, MusicService.class);
			pauseIntent.setAction("music_pause");
			pauseIntent.putExtra("music_state", 1);// 1 pause
			PendingIntent pPauseIntent = PendingIntent.getService(this, 1,
					pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			builder.addAction(R.drawable.ic_menu_user_on, "开始", pStartIntent);
			builder.addAction(R.drawable.ic_menu_user_off, "暂停", pPauseIntent);

			manager.notify(BIGVIEW_NOTIFY_ID, builder.build());*/
				break;

			case R.id.download_notification_btn:
				// NotificationCompat.Builder builder = new
				// NotificationCompat.Builder(
				// NotificationActivity.this);
				// 构造内容
				builder.setSmallIcon(R.drawable.meituan_image6);
				builder.setTicker("正在下载...");
				builder.setContentTitle("下载通知");
				builder.setContentText("下载中");
				// 线程模拟进度条的加载
				new Thread(new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < 100; i++) {
							builder.setProgress(100, i, false);
							manager.notify(11, builder.build());
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						// 加载完成后进度天的消失
						builder.setProgress(0, 0, false);
						builder.setContentText("下载完成");// text的更新
						// NotificationManager manager = (NotificationManager)
						// getSystemService(NOTIFICATION_SERVICE);
						manager.notify(11, builder.build());
					}

				}).start();

				break;
		}

	}

}
