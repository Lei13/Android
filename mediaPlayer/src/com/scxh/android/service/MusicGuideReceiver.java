package com.scxh.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.android.music.R;

/*
 * 刷新通知栏和主界面的播放信息
 */
public class MusicGuideReceiver extends BroadcastReceiver {
	public static final String ACTION_RECEIVER = "com.music.service.MusicGuideReceiver";

	// 接收更新的内容(intent 传送标签)
	public static final String UPDATE_TITLE = "update_title";
	public static final String UPDATE_PALY_STATE = "update_state";
	public static final String UPDATE_POSITION = "update_position";
	public static final String UPDATE_PROGRESS = "update_progress";

	public static final String UI_MAIN = "main_activity";
	public static final String UI_PLAY = "play_activity";

	TextView mTitle;
	ImageView mPlay;
	String tag;
	public MusicGuideReceiver(String ui_tag,TextView title, ImageView play) {
		this.mTitle = title;
		this.mPlay = play;
		this.tag = ui_tag;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (ACTION_RECEIVER.equals(action)) {
			String title = intent.getStringExtra(UPDATE_TITLE);
			mTitle.setText(title);
			boolean state = intent.getBooleanExtra(UPDATE_PALY_STATE, false);
			if (state == true) {
				if (tag.equals(UI_MAIN)) {
					mPlay.setImageResource(R.drawable.little_bt_play);
				}else if(tag.equals(UI_PLAY)){
					mPlay.setBackgroundResource(R.drawable.play_song_selector);
				}
				
			} else {
				if (tag.equals(UI_MAIN)) {
					mPlay.setImageResource(R.drawable.little_bt_pause);
				}else if(tag.equals(UI_PLAY)){
					mPlay.setBackgroundResource(R.drawable.pause_song_selector);
				}
				
			}
		}
		
			

	}
}
