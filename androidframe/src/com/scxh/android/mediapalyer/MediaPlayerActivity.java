package com.scxh.android.mediapalyer;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.scxh.android.frame.R;

public class MediaPlayerActivity extends Activity {
	Button mLastBtn, mPlayBtn, mNextBtn;
	static MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediapalyer_layout);
		initView();
		mPlayer = MediaPlayer.create(MediaPlayerActivity.this,
				R.raw.lie);
		mPlayBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (mPlayer.isPlaying()) {
					mPlayer.pause();
				} else {
					mPlayer.start();
				}

				/*
				 * Uri uri = Uri.parse("file://mnt/sdcard/lie.mp3"); mPlayer =
				 * new MediaPlayer(); Log.i("taa", "<<<<<<<"); try {
				 * mPlayer.setDataSource("file://mnt/sdcard/lie.mp3");
				 * mPlayer.prepare(); mPlayer.start(); } catch
				 * (IllegalArgumentException e) { e.printStackTrace(); } catch
				 * (SecurityException e) { e.printStackTrace(); } catch
				 * (IllegalStateException e) { e.printStackTrace(); } catch
				 * (IOException e) { e.printStackTrace(); }
				 */
			}
		});
	}

	private void initView() {
		mLastBtn = (Button) findViewById(R.id.media_last_btn);
		mPlayBtn = (Button) findViewById(R.id.media_play_btn);
		mNextBtn = (Button) findViewById(R.id.media_next_btn);

	}

}
