package com.scxh.android.music;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Constance.MusicBean;
import Constance.MusicList;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.scxh.android.music.adapter.MusicBaseAdapter;
import com.scxh.android.music.adapter.MusicPagerAdapter;
import com.scxh.android.service.MusicGuideReceiver;
import com.scxh.android.service.MusicService;
import com.scxh.android.service.MusicService.MusicBinder;
import com.scxh.android.view.LyricView;

public class PlayActivity extends Activity implements OnClickListener,
		OnSeekBarChangeListener {
	LyricView itemLyric;
	LayoutInflater inflater;
	ViewPager mPager;
	int oldPostion = 1;// 记录小圆点
	ImageView mDot1, mDot2, mDot3, mListImg;
	ImageButton mLastBtn, mPlayBtn, mNextBtn, mCollection;
	SeekBar mSeekBar;
	TextView mCurrentTxt, mTotalTxt, mTitle;

	ArrayList<MusicBean> mListData = MusicList.mListData;
	int selectPosition;
	boolean playState;

	File file = new File(Environment.getExternalStorageDirectory() + "/music");
	boolean threadFlag = true;
	Handler mHandler = new Handler();
	// 接口对象
	MusicBinder mBinder;
	//建立连接时的两个方法
	ServiceConnection sConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBinder = null;
		}
//建立连接后，能得到onBind中返回的IBinder对象
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder = (MusicBinder) service;

		}
	};

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

		intent.putExtra("currentPosition", selectPosition);
		intent.putExtra("MUSIC_STATE", playState);
		startService(intent);

		mTitle.setText(mListData.get(selectPosition).getName());

		bindService(intent, sConnection, BIND_AUTO_CREATE);

		new Thread(new UIThread()).start();
		
		MusicGuideReceiver receiver = new  MusicGuideReceiver(MusicGuideReceiver.UI_PLAY,mTitle, mPlayBtn);
		IntentFilter filter = new IntentFilter(MusicGuideReceiver.ACTION_RECEIVER);
		registerReceiver(receiver, filter);
	}

	/*
	 * 接收到listActivity传过来的歌曲数据和当前选中的位置
	 */
	private void receiveData() {
		Intent intent = getIntent();
		selectPosition = intent.getIntExtra("selectPosition", 0);
		playState = intent.getBooleanExtra("MUSIC_STATE", false);
		if (playState==false) {
			mPlayBtn.setBackgroundResource(R.drawable.pause_song_selector);
		}
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
/*
 * PagerView添加组件
 */
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
				mTitle.setText(mBinder.seekTo(position));
			}
		});

		View item2 = inflater.inflate(R.layout.pager_item2_image_layout, null);
		img = (ImageView) item2.findViewById(R.id.img_music_playing);

		View item3  = inflater.inflate(R.layout.pager_item3_lyric_layout, null);
		itemLyric = (LyricView) item3;
		data.add(item1);
		data.add(item2);
		data.add(itemLyric);

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//播放/暂停的监听
		case R.id.palying_btn:

			boolean playFlag = mBinder.playMusic();
			if (playFlag == true) {
				mPlayBtn.setBackgroundResource(R.drawable.play_song_selector);
			} else {
				mPlayBtn.setBackgroundResource(R.drawable.pause_song_selector);
			}

			break;
			//点击下一首
		case R.id.next_song_btn:
			if (mBinder != null) {
				int nextPostion = mBinder.nextMusic();
				mTitle.setText(mListData.get(nextPostion).getName());
			}
			mPlayBtn.setBackgroundResource(R.drawable.play_song_selector);

			break;
			//点击上一首
		case R.id.last_song_btn:
			if (mBinder != null) {
				int lastPosition = mBinder.lastMusic();
				mTitle.setText(mListData.get(lastPosition).getName());
			}
			mPlayBtn.setBackgroundResource(R.drawable.play_song_selector);

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

	int mProgress;//记录拖动的进度条进度

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mSeekBar.setProgress(progress);
		mProgress = progress;

	}
/*
 * 刷新界面的（SeekBar 進度及顯示）
 */
	public class UIThread implements Runnable {

		@Override
		public void run() {
			while (threadFlag) {
				if (mBinder != null) {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							int current = mBinder.currentTime();
							int total = mBinder.TotalTime();
							mTitle.setText(mBinder.getCurrentTitle());
							mSeekBar.setMax(total);
							mSeekBar.setProgress(current);
							mCurrentTxt.setText(timeFormat(current));
							mTotalTxt.setText(timeFormat(total));
							itemLyric.updateIndex(current);
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}
/*
 * 拖動进度条的监听，避免因延迟造成播放卡顿，所以在onStopTrackingTouch方法中将进度传到服务器
 */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mBinder.setCurrentTime(mProgress);
	}

	private String timeFormat(int time) {
		SimpleDateFormat df = new SimpleDateFormat("mm:ss");
		String timeStr = df.format(new Date(time));
		return timeStr;
	}

	
}
