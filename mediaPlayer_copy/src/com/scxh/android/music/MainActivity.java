package com.scxh.android.music;

import java.io.File;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;

import Constance.MusicBean;
import Constance.MusicInfoBean;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scxh.android.music.adapter.GridAdapter;
import com.scxh.android.music.adapter.ListAdapter_mine;
import com.scxh.android.music.adapter.MusicPagerAdapter;
import com.scxh.android.service.MusicService;

/*
 * 主界面 一个 viewpager
 * 1.发现音乐	FondMusicActivity
 * 2.我的音乐  MyMusicActivity
 */
public class MainActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	ServiceConnection sConnection;
	
	RelativeLayout mLittlePlay;
	
	ImageView mIconImg, mSearchImg, mIsPlay;
	TextView mFoundTxt, mMyMusicTxt, mMusicNameTxt;
	ViewPager mPager;
	LayoutInflater inflater;
	ArrayList<View> mData;// 主界面pager数据源
	ArrayList<TextView> mTxtData;// 主界面pager 的小园点
	View mMine, mFond;// 主界面（Pager）的兩個大item

	Handler handler;// item1 发现音乐 的pager设置循环滚动
	int time = 0;
	int oldPosition;// 记录主界面pager
	int findoldPosition = 0;// 记录item发现界面pager

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainactivity_layout);
		initView();

		scanFile(Environment.getExternalStorageDirectory());
		mFoundTxt.setOnClickListener(this);
		mFoundTxt.setTextColor(Color.WHITE);
		mMyMusicTxt.setOnClickListener(this);
		mIsPlay.setOnClickListener(this);
		mLittlePlay.setOnClickListener(this);
		
		setPagerItem();
		new Thread(new MyThread()).start();
	}

	private void setPagerItem() {
		MusicPagerAdapter adapter = new MusicPagerAdapter();
		adapter.setData(getData());
		mPager.setAdapter(adapter);
		mPager.setOnPageChangeListener(this);
	}

	/*
	 * 主界面的数据源，两大界面
	 */
	private ArrayList<View> getData() {
		mData = new ArrayList<View>();
		mFond = inflater.inflate(R.layout.fondmusic_activity_layout, null);
		mMine = inflater.inflate(R.layout.mymusic_activity_layout, null);
		addFindItem();
		addMineItem();
		mData.add(mFond);
		mData.add(mMine);

		return mData;
	}

	private void initView() {
		mIconImg = (ImageView) findViewById(R.id.icon_main_img);
		mSearchImg = (ImageView) findViewById(R.id.search_main_img);
		mFoundTxt = (TextView) findViewById(R.id.found_music_main_txt);
		mMyMusicTxt = (TextView) findViewById(R.id.my_music_main_txt);
		mPager = (ViewPager) findViewById(R.id.pager_main);
		inflater = LayoutInflater.from(this);
		mIsPlay = (ImageView) findViewById(R.id.play_main);
		mMusicNameTxt = (TextView) findViewById(R.id.name_main);

		mTxtData = new ArrayList<TextView>();
		mTxtData.add(mFoundTxt);
		mTxtData.add(mMyMusicTxt);
		
		mLittlePlay = (RelativeLayout) findViewById(R.id.little_paly_layout);
	}

	/*
	 * 添加第一个界面，发现音乐 pager+grid 控件初始化 pager 添加item，设置适配器，滚动 handler实现 pager自动循环滚动
	 * pager设置监听
	 * 
	 * grid 传数据源+适配器
	 */
	ViewPager findPager;

	@SuppressWarnings("deprecation")
	private void addFindItem() {
		ScrollView scroll = (ScrollView) mFond
				.findViewById(R.id.main_find_scroll_layout);
		// ScrollView scroll = (ScrollView) mFond;
		scroll.smoothScrollTo(0, 0);
		GridView grid;

		findPager = (ViewPager) mFond.findViewById(R.id.pager_found);
		grid = (GridView) mFond.findViewById(R.id.grid_found);

		ArrayList<View> data = new ArrayList<View>();
		MusicPagerAdapter adapter = new MusicPagerAdapter();
		View item1 = inflater.inflate(R.layout.item1_findpager_layout, null);
		View item2 = inflater.inflate(R.layout.item2_findpager_layout, null);
		View item3 = inflater.inflate(R.layout.item3_findpager_layout, null);
		data.add(item1);
		data.add(item2);
		data.add(item3);

		adapter.setData(data);
		findPager.setAdapter(adapter);

		final ArrayList<ImageView> dotData = new ArrayList<ImageView>();
		ImageView dot1 = (ImageView) mFond.findViewById(R.id.dot1_find_img);
		ImageView dot2 = (ImageView) mFond.findViewById(R.id.dot2_find_img);
		ImageView dot3 = (ImageView) mFond.findViewById(R.id.dot3_find_img);
		dotData.add(dot1);
		dotData.add(dot2);
		dotData.add(dot3);

		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (time > findPager.getChildCount() - 1) {
						findPager.setCurrentItem(0);
						time = 0;
					} else {
						findPager.setCurrentItem(time);
					}
					break;

				}
			};
		};

		findPager.setCurrentItem(0);
		dotData.get(oldPosition).setImageResource(R.drawable.dot_pressed);

		findPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				dotData.get(findoldPosition).setImageResource(
						R.drawable.dot_default);
				dotData.get(arg0).setImageResource(R.drawable.dot_pressed);
				findoldPosition = arg0;

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		GridAdapter gridAdapter = new GridAdapter(this);
		gridAdapter.setData(getGridData());
		grid.setAdapter(gridAdapter);
	}

	/*
	 * 第二个主界面 我的音乐 title+ listview 初始化 listview 适配器+ 加监听
	 */
	private void addMineItem() {
		ListView list;
		TextView userTxt, setting;

		list = (ListView) mMine.findViewById(R.id.list_main_lv);
		userTxt = (TextView) mMine.findViewById(R.id.user_main_txt);
		setting = (TextView) mMine.findViewById(R.id.setting_main_txt);

		ListAdapter_mine adapter = new ListAdapter_mine(this);
		adapter.setData(getListData());
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(MainActivity.this,
							ListActivity.class));
					break;

				case 1:

					break;
				case 2:

					break;
				case 3:
					break;

				}
			}
		});
	}

	/*
	 * 界面二----我的音乐 item2 list设置数据源
	 */
	private ArrayList<HashMap<String, Object>> getListData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_menu_song_recognition_press);
		map.put("title", "全部歌曲");
		map.put("num", num + "首歌曲");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.download);
		map.put("title", "本地歌曲");
		map.put("num", num + "首歌曲");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_radiopage_icon_like);
		map.put("title", "我收藏的单曲");
		map.put("num", "0首歌曲");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_list_dropdown_plus_normal);
		map.put("title", "添加歌单");
		map.put("num", "");
		data.add(map);
		return data;
	}

	/*
	 * 界面一---发现音乐 item1 Grid 数据源
	 */
	private ArrayList<HashMap<String, Object>> getGridData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("grid_name", "出发在路上");
		map.put("grid_img", R.drawable.pc9);
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("grid_name", "工作");
		map.put("grid_img", R.drawable.pc10);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "猜你喜欢");
		map.put("grid_img", R.drawable.pc4);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "情歌对唱");
		map.put("grid_img", R.drawable.pc5);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "香港流行乐");
		map.put("grid_img", R.drawable.pc6);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "百度热歌榜");
		map.put("grid_img", R.drawable.pc7);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "百度新歌榜");
		map.put("grid_img", R.drawable.pc8);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "华语金曲榜");
		map.put("grid_img", R.drawable.pc9);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "经典老歌榜");
		map.put("grid_img", R.drawable.cat);
		data.add(map);

		return data;
	}

	/*
	 * 主界面文本监听（pager 类似 的小圆点，用的是textview）主界面的viewpager 监听
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.found_music_main_txt:
			mFoundTxt.setTextColor(Color.WHITE);
			mMyMusicTxt.setTextColor(Color.GRAY);
			mIconImg.setVisibility(View.VISIBLE);
			mPager.setCurrentItem(0);
			break;

		case R.id.my_music_main_txt:
			mMyMusicTxt.setTextColor(Color.WHITE);
			mFoundTxt.setTextColor(Color.GRAY);
			mPager.setCurrentItem(1);
			mIconImg.setVisibility(View.INVISIBLE);
			break;
		case R.id.little_paly_layout:
			startActivity(new Intent(this,PlayActivity.class));
			break;

	/*	case R.id.play_main:
			if (mBinder != null) {
				boolean playFlag = mBinder.playMusic();
				if (playFlag == true) {
					mIsPlay.setImageResource(R.drawable.widget_pause);
				} else {
					mIsPlay.setImageResource(R.drawable.playlist_detail_play_all_default);
				}

			} else {
				Intent intent = new Intent(this, MusicService.class);
				intent.putExtra("musicList", mListData);
				intent.putExtra("selectPosition", 0);
				startService(intent);
				mIsPlay.setImageResource(R.drawable.widget_pause);
				bind();
			}

			break;*/
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {

		if (arg0 == 0) {
			// mIconImg.setImageResource(R.drawable.information_icon_4);
			mIconImg.setVisibility(View.VISIBLE);
		} else {
			mIconImg.setVisibility(View.INVISIBLE);
		}
		// mIconImg.setImageResource(0);
		mTxtData.get(oldPosition).setTextColor(Color.GRAY);
		mTxtData.get(arg0).setTextColor(Color.WHITE);
		oldPosition = arg0;

	}

	/*
	 * 主pager 一： find pager循环滚动通知线程
	 */

	class MyThread implements Runnable {

		@Override
		public void run() {

			while (true) {
				Message msg = Message.obtain();
				msg.what = 1;
				handler.sendMessage(msg);
				time++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	ArrayList<MusicBean> mListData;




	int num = 0;// 记录歌曲总数

	public void scanFile(File file) {
		File[] listFile = file.listFiles();
		mListData = new ArrayList<MusicBean>();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				File item = listFile[i];
				if (item.isDirectory()) {
					scanFile(item);
				} else {
					if (item.getName().endsWith("mp3")) {
						num++;

						MusicBean music = new MusicBean();
						String name = item.getName();
						music.setName(name.substring(0,
								name.lastIndexOf(".mp3")));
						music.setComposer("unknown");
						music.setPath(item.getAbsolutePath());
						mListData.add(music);
					}
				}
			}
		}

	}
}
