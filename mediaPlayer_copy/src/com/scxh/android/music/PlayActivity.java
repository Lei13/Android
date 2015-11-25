package com.scxh.android.music;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Constance.MusicBean;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.scxh.android.music.adapter.MusicBaseAdapter;
import com.scxh.android.music.adapter.MusicPagerAdapter;
import com.scxh.android.service.MusicService;
import com.scxh.android.service.MusicService.MusicReciever;

public class PlayActivity extends Activity implements OnClickListener,
		OnSeekBarChangeListener {
	LayoutInflater inflater;
	ViewPager mPager;
	int oldPostion = 1;// 记录小圆点
	ImageView mDot1, mDot2, mDot3, mListImg;
	ImageButton mLastBtn, mPlayBtn, mNextBtn, mCollection;
	SeekBar mSeekBar;
	TextView mCurrentTxt, mTotalTxt, mTitle;

	ArrayList<MusicBean> mListData;
	int selectPosition;

	File file = new File(Environment.getExternalStorageDirectory() + "/music");
	boolean threadFlag = true;
	Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.playactivity_layout);
		initView();// 初始化view
		receiveData();// listActivity传过来的歌曲数据和当前选中的位置
		setPagerItem();// 为ViewPager添加item布局
		setPagerListener();
		registerListener();// view注册监听

		Intent intent = new Intent(this, MusicService.class);

		intent.putParcelableArrayListExtra("musicList", mListData);
		intent.putExtra("currentPosition", selectPosition);
		startService(intent);

		mTitle.setText(mListData.get(selectPosition).getName());

		registerReceiver();
	}

	/*
	 * 接收到listActivity传过来的歌曲数据和当前选中的位置
	 */
	private void receiveData() {
		Intent intent = getIntent();
		mListData = intent.getParcelableArrayListExtra("musicList");
		selectPosition = intent.getIntExtra("selectPosition", 0);
	}

	private void registerListener() {
		mLastBtn.setOnClickListener(this);
		mPlayBtn.setOnClickListener(this);
		mNextBtn.setOnClickListener(this);
		mSeekBar.setOnSeekBarChangeListener(this);
		mListImg.setOnClickListener(this);
	}

	private void initView() {
		inflater = LayoutInflater.from(this);
		mPager = (ViewPager) findViewById(R.id.pager_music);
		mDot1 = (ImageView) findViewById(R.id.dot1_img);
		mDot2 = (ImageView) findViewById(R.id.dot2_img);
		mDot3 = (ImageView) findViewById(R.id.dot3_img);

		mListData = new ArrayList<MusicBean>();

		mLastBtn = (ImageButton) findViewById(R.id.last_song_btn);
		mPlayBtn = (ImageButton) findViewById(R.id.palying_btn);
		mNextBtn = (ImageButton) findViewById(R.id.next_song_btn);
		mSeekBar = (SeekBar) findViewById(R.id.progressbar);
		mCurrentTxt = (TextView) findViewById(R.id.current_progress_txt);
		mTotalTxt = (TextView) findViewById(R.id.total_progress_txt);

		mListImg = (ImageView) findViewById(R.id.list_img);
		mCollection = (ImageButton) findViewById(R.id.collection_ibtn);
		mTitle = (TextView) findViewById(R.id.tile_play_txt);

	}

	ImageView img;

	private void setPagerItem() {
		ArrayList<View> data = new ArrayList<View>();

		View item1 = inflater.inflate(R.layout.pager_item1_list_layout, null);
		MusicBaseAdapter baseAdapter = new MusicBaseAdapter(this);
		baseAdapter.setData(mListData);
		final ListView listView = (ListView) item1
				.findViewById(R.id.list_item1_pager);
		listView.setAdapter(baseAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectPosition = position;
			}
		});

		View item2 = inflater.inflate(R.layout.pager_item2_image_layout, null);
		img = (ImageView) item2.findViewById(R.id.img_music_playing);

		View item3 = inflater.inflate(R.layout.pager_item3_lyric_layout, null);

		data.add(item1);
		data.add(item2);
		data.add(item3);

		MusicPagerAdapter adapter = new MusicPagerAdapter();
		adapter.setData(data);
		mPager.setAdapter(adapter);

	}

	@SuppressWarnings("deprecation")
	private void setPagerListener() {
		final ArrayList<View> dotData = new ArrayList<View>();
		dotData.add(mDot1);
		dotData.add(mDot2);
		dotData.add(mDot3);
		mPager.setCurrentItem(1);
		dotData.get(1).setBackgroundResource(R.drawable.dot_pressed);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				dotData.get(oldPostion).setBackgroundResource(
						R.drawable.dot_default);
				dotData.get(arg0).setBackgroundResource(R.drawable.dot_pressed);
				oldPostion = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	boolean playFlag = true;
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(MusicReciever.ACTION_PLAY);
		switch (v.getId()) {
		case R.id.palying_btn:
			if (playFlag) {
				intent.putExtra("play", 0);
				sendBroadcast(intent);
				mPlayBtn.setBackgroundResource(R.drawable.pause_song_selector);
				playFlag =false;
			}else {
				mPlayBtn.setBackgroundResource(R.drawable.play_song_selector);
				playFlag =true;
			}

			break;

		case R.id.next_song_btn:
			intent.putExtra("play", 3);
			sendBroadcast(intent);
			
			mPlayBtn.setBackgroundResource(R.drawable.play_song_selector);

			break;
		case R.id.last_song_btn:
			mPlayBtn.setBackgroundResource(R.drawable.play_song_selector);
			intent.putExtra("play", 2);
			sendBroadcast(intent);
			
			break;
		case R.id.list_img:
			startActivity(new Intent(this, ListActivity.class));
			finish();
			break;
			
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	int mProgress;

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mSeekBar.setProgress(progress);
		mProgress = progress;
		mCurrentTxt.setText(timeFormat(progress));

	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		Intent intent = new Intent(MusicReciever.ACTION_CHANGEPROGRESS);
		intent.putExtra("currentProgress", mProgress);
		sendBroadcast(intent);
	}

	private String timeFormat(int time) {
		SimpleDateFormat df = new SimpleDateFormat("mm:ss");
		String timeStr = df.format(new Date(time));
		return timeStr;
	}

	/**
	 * 注册广播
	 */
	PlayReceiver receiver;

	public void registerReceiver() {
		receiver = new PlayReceiver();
		IntentFilter filter = new IntentFilter(ACTION_MUSIC_TITLE_TIME);
		registerReceiver(receiver, filter);

	}

	/**
	 * 定义广播
	 * 
	 */
	public static final String ACTION_MUSIC_TITLE_TIME = "com.com.scxh.android.play.musicreceiver.musicname";
	Handler han = new Handler();

	public class PlayReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, final Intent intent) {
			final String action = intent.getAction();

			if (action.equals(ACTION_MUSIC_TITLE_TIME)) {
				mProgress = intent.getIntExtra("musicTime", 0);
				int total = intent.getIntExtra("musicTotalTime", 0);
				String text = intent.getStringExtra("musicName");
				
				mSeekBar.setProgress(mProgress);
				mSeekBar.setMax(total);
				mCurrentTxt.setText(timeFormat(mProgress));
				mTotalTxt.setText(timeFormat(total));
				mTitle.setText(text);
			}

		}

	}

}
