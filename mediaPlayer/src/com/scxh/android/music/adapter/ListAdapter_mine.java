package com.scxh.android.music.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.android.music.R;

public class ListAdapter_mine extends BaseAdapter {
	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	LayoutInflater inflater;

	public ListAdapter_mine(Context context) {
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
			convertView = inflater.inflate(
					R.layout.my_music_list_item_layout, null);
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
