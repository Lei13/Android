package com.scxh.android.music;

import java.io.File;
import java.util.ArrayList;

import Constance.MusicBean;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.scxh.android.db.DbHelper;
import com.scxh.android.music.R;
import com.scxh.android.music.adapter.MusicBaseAdapter;

public class ListActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	private ListView mList;
	private ArrayList<MusicBean> mListData;
	public File FILE_PATH = Environment.getExternalStorageDirectory();
	private ImageView mBackimg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listactivity_layout);
		initView();

		setListAdapter();// Ϊlistview����������
		
		registerListener();//ע�����
	}

	private void initView() {
		mList = (ListView) findViewById(R.id.songlist_layout_view);
		mBackimg = (ImageView) findViewById(R.id.back_list_img);
	}
	
	/*
	 * ע�����
	 */
	private void registerListener(){
		mBackimg.setOnClickListener(this);
	}

	/*
	 * ListView
	 * ����Դ+ ������+ע�����
	 */
	private void setListAdapter() {
		scanFile(FILE_PATH);
		MusicBaseAdapter adapter = new MusicBaseAdapter(this);
		adapter.setData(mListData);
		mList.setAdapter(adapter);
		mList.setOnItemClickListener(this);
	}
	/*
	 * ���listviewĳһ��ʱ����ת�����Ž��棬�򲥷Ž��洫����Դ����λ����Ϣ
	 */

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(ListActivity.this, PlayActivity.class);
		intent.putParcelableArrayListExtra("musicList", mListData);
		intent.putExtra("selectPosition", position);
		startActivity(intent);

	}

	/*
	 * ����Դ
	 */
	public void scanFile(File file) {
		mListData = new ArrayList<MusicBean>();
		File[] listFile = file.listFiles();

		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				File item = listFile[i];
				if (item.isDirectory()) {
					scanFile(item);
				} else {
					if (item.getName().endsWith("mp3")) {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_list_img:
			startActivity(new Intent(this, MainActivity.class));
			finish();
			break;

		default:
			break;
		}

	}

}
