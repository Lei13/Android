package com.scxh.android.music;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import Constance.DB_Constance;
import Constance.MusicBean;
import Constance.MusicInfoBean;
import Constance.MusicList;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.scxh.android.db.Provider;
import com.scxh.android.db.TestProvider.Helper;
import com.scxh.android.music.MainActivity.MyThread;
import com.scxh.android.music.adapter.GridAdapter;
import com.scxh.android.music.adapter.ListAdapter_mine;
import com.scxh.android.music.adapter.MusicPagerAdapter;
import com.scxh.android.service.MusicGuideReceiver;
import com.scxh.android.service.MusicService;
import com.scxh.android.service.MusicService.MusicBinder;

/*
 * ������ һ�� viewpager
 * 1.��������	FondMusicActivity
 * 2.�ҵ�����  MyMusicActivity
 */
public class MainActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	LayoutInflater inflater;
	ContentResolver resolver;
	RelativeLayout mLittlePlay;// �ײ�mini���ſ��Ʋ���
	Intent intent;
	MusicGuideReceiver receiver;
	MusicBinder mBinder;// �ӿڶ���
	ServiceConnection sConnection;
	ScrollView mScroll;
	ImageView mIconImg, mSearchImg, mIsPlay;
	TextView mFoundTxt, mMyMusicTxt, mMusicNameTxt;
	ViewPager mPager;

	ArrayList<View> mData;// ������pager����Դ
	ArrayList<TextView> mTxtData;// ������pager ��С԰��
	View mMine, mFond;// �����棨Pager���ăɂ���item

	Handler handler;// item1 �������� ��pager����ѭ������
	int time = 0;
	int oldPosition;// ��¼������pager
	int findoldPosition = 0;// ��¼item���ֽ���pager

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainactivity_layout);
		initView();

		
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
	 * �����������Դ���������
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
		resolver = getContentResolver();

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
	 * ��ӵ�һ�����棬�������� pager+grid �ؼ���ʼ�� pager ���item������������������ handlerʵ�� pager�Զ�ѭ������
	 * pager���ü���
	 * 
	 * grid ������Դ+������
	 */
	ViewPager findPager;

	@SuppressWarnings("deprecation")
	private void addFindItem() {
		mScroll = (ScrollView) mFond
				.findViewById(R.id.main_find_scroll_layout);
		// ScrollView scroll = (ScrollView) mFond;
		mScroll.smoothScrollTo(0, 0);
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
	 * �ڶ��������� �ҵ����� title+ listview ��ʼ�� listview ������+ �Ӽ���
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
					Intent intent = new Intent(MainActivity.this,ListActivity.class);
					startActivity(intent);
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
	 * �����----�ҵ����� item2 list��������Դ
	 */
	private ArrayList<HashMap<String, Object>> getListData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_menu_song_recognition_press);
		map.put("title", "ȫ������");
		map.put("num", MusicList.mListData.size() + "�׸���");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.download);
		map.put("title", "���ظ���");
		map.put("num", MusicList.mListData.size() + "�׸���");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_radiopage_icon_like);
		map.put("title", "���ղصĵ���");
		map.put("num", "0�׸���");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_list_dropdown_plus_normal);
		map.put("title", "��Ӹ赥");
		map.put("num", "");
		data.add(map);
		return data;
	}

	/*
	 * ����һ---�������� item1 Grid ����Դ
	 */
	private ArrayList<HashMap<String, Object>> getGridData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("grid_name", "������·��");
		map.put("grid_img", R.drawable.pc9);
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("grid_name", "����");
		map.put("grid_img", R.drawable.pc10);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "����ϲ��");
		map.put("grid_img", R.drawable.pc4);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "���Գ�");
		map.put("grid_img", R.drawable.pc5);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "���������");
		map.put("grid_img", R.drawable.pc6);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "�ٶ��ȸ��");
		map.put("grid_img", R.drawable.pc7);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "�ٶ��¸��");
		map.put("grid_img", R.drawable.pc8);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "���������");
		map.put("grid_img", R.drawable.pc9);
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("grid_name", "�����ϸ��");
		map.put("grid_img", R.drawable.cat);
		data.add(map);

		return data;
	}

	/*
	 * �������ı�������pager ���� ��СԲ�㣬�õ���textview���������viewpager ����
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
			mScroll.smoothScrollTo(0, 0);
			break;

		case R.id.my_music_main_txt:
			mMyMusicTxt.setTextColor(Color.WHITE);
			mFoundTxt.setTextColor(Color.GRAY);
			mPager.setCurrentItem(1);
			mIconImg.setVisibility(View.INVISIBLE);
			break;
		case R.id.little_paly_layout:
			intent = new Intent(this, PlayActivity.class);
			if (mBinder == null) {
				intent.putExtra("selectPosition", 0);
				intent.putExtra("MUSIC_STATE", false);
				startActivity(intent);
			}else {
				mBinder.seekTo(mBinder.currentTime());
				startActivity(intent);
			}
			
			break;

		case R.id.play_main:
			if (mBinder != null) {
				boolean playFlag = mBinder.playMusic();
				if (playFlag == true) {
					mIsPlay.setImageResource(R.drawable.little_bt_play);
				} else {
					mIsPlay.setImageResource(R.drawable.little_bt_pause);
				}
			} else {
				mIsPlay.setImageResource(R.drawable.widget_pause);
				intent = new Intent(this, MusicService.class);
				intent.putExtra("selectPosition", 0);
				intent.putExtra("MUSIC_STATE", true);
				startService(intent);
				
			}

			break;
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
	 * ��pager һ�� find pagerѭ������֪ͨ�߳�
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

	private void bind() {
		Intent intent = new Intent(this, MusicService.class);

		sConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mBinder = null;
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mBinder = (MusicBinder) service;

			}
		};

		bindService(intent, sConnection, BIND_AUTO_CREATE);
	}


	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter filter = new IntentFilter(
				MusicGuideReceiver.ACTION_RECEIVER);
		receiver = new MusicGuideReceiver(MusicGuideReceiver.UI_MAIN,mMusicNameTxt, mIsPlay);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(receiver);
	}

}
