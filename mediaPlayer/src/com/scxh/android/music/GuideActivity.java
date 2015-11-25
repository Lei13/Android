package com.scxh.android.music;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.scxh.android.service.MusicGuideReceiver;
import com.scxh.android.service.MusicService;

public class GuideActivity extends Activity {
	TextView mNotifyTitleTxt,mNotifyArtistTxt;
	ImageView mNotifyPlayBtn;
	
	
	MusicGuideReceiver receiver;
	
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_activity_layout);
		initNotificationView();
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();

			}
		}, 3000);
	}
	
	
	private void initNotificationView(){
		View v = LayoutInflater.from(this).inflate(R.layout.music_notification_layout, null);
		mNotifyTitleTxt = (TextView) v.findViewById(R.id.title_notify_txt);
		mNotifyArtistTxt = (TextView) v.findViewById(R.id.artist_notify_txt);
		mNotifyPlayBtn = (ImageView) v.findViewById(R.id.paly_notify_btn);
	}

	
}
