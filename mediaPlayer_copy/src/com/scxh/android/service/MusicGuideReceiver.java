package com.scxh.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.android.music.R;

public class MusicGuideReceiver extends BroadcastReceiver {
	
	public static final String ACTION_RECEIVER = "com.music.service.MusicGuideReceiver";

	// 接收更新的内容(intent 传送标签)
	public static final String UPDATE_TITLE = "updateview";
	public static final String UPDATE_ARTIST = "updateview";
	public static final String UPDATE_PALY_STATE = "updateview";


	public static final String RECEIVER_MAIN= "main";
	public static final String RECEIVER_NOTIFICATION= "notificatoin";
	
	TextView mTitle, mArtist;
	ImageView mPlay;
	String mWho;

	public MusicGuideReceiver(String who, TextView title, TextView artist,
			ImageView play) {
		this.mTitle = title;
		this.mArtist = artist;
		this.mPlay = play;
		this.mWho = who;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (ACTION_RECEIVER.equals(action)) {
			mTitle.setText(intent.getStringExtra(UPDATE_TITLE));
			//mArtist.setText(intent.getStringExtra(UPDATE_ARTIST));

			boolean state = intent.getBooleanExtra(UPDATE_PALY_STATE, false);
			if (mWho.equals(RECEIVER_NOTIFICATION)) {
				if (state == true) {
					mPlay.setImageResource(R.drawable.notify_pause_n);
				} else {
					mPlay.setImageResource(R.drawable.notify_play_n);
				}

			}
			if (mWho.equals(RECEIVER_MAIN)) {
				if (state == true) {
					mPlay.setImageResource(R.drawable.little_bt_play);
				} else {
					mPlay.setImageResource(R.drawable.little_bt_pause);
				}
			}
		}

	}
}
