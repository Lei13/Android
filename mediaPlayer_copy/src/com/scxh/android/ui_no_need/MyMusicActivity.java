package com.scxh.android.ui_no_need;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scxh.android.music.ListActivity;
import com.scxh.android.music.R;
import com.scxh.android.music.R.drawable;
import com.scxh.android.music.R.id;
import com.scxh.android.music.R.layout;

public class MyMusicActivity extends Activity {
	ListView mList;
	TextView mUserTxt, mSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mymusic_activity_layout);
		initView();

		ListAdapter_Main adapter = new ListAdapter_Main(this);
		adapter.setData(getData());
		mList.setAdapter(adapter);
		setListListener();
	}

	private void setListListener() {
		mList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(MyMusicActivity.this,ListActivity.class));
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
	
	

	private void initView() {
		mList = (ListView) findViewById(R.id.list_main_lv);
		mUserTxt = (TextView) findViewById(R.id.user_main_txt);
		mSetting = (TextView) findViewById(R.id.setting_main_txt);
	}

	private ArrayList<HashMap<String, Object>> getData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("icon", R.drawable.ic_menu_song_recognition_press);
		map.put("title", "全部歌曲");
		map.put("num", "首歌曲");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("icon", R.drawable.download);
		map.put("title", "本地歌曲");
		map.put("num", "首歌曲");
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

	public class ListAdapter_Main extends BaseAdapter {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		LayoutInflater inflater;

		public ListAdapter_Main(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void setData(ArrayList<HashMap<String, Object>> data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.my_music_list_item_layout,
						null);
				holder = new ViewHolder();
				holder.img = (ImageView) convertView
						.findViewById(R.id.img_list_item);
				holder.title = (TextView) convertView
						.findViewById(R.id.song_txt_list_item);
				holder.num = (TextView) convertView
						.findViewById(R.id.song_total_list_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			HashMap<String, Object> map = data.get(position);
			if (position < data.size() - 1) {
				holder.num.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.bt_sceneplay_mode_hide, 0);
			}
			holder.img.setImageResource((Integer) map.get("icon"));
			holder.title.setText(map.get("title").toString());
			holder.num.setText(map.get("num").toString());

			return convertView;
		}

		public class ViewHolder {
			ImageView img;
			TextView title, num;
		}
	}
}
