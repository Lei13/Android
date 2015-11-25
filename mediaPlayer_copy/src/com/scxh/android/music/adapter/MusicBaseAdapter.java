package com.scxh.android.music.adapter;

import java.io.File;
import java.util.ArrayList;

import Constance.MusicBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.android.music.R;

public class MusicBaseAdapter extends BaseAdapter {
	ArrayList<MusicBean> data = new ArrayList<MusicBean>();
	LayoutInflater inflater;

	public MusicBaseAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setData(ArrayList<MusicBean> data) {
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
		viewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.play_list_item_music_layout,
					null);
			holder = new viewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name_music);
			holder.person = (TextView) convertView
					.findViewById(R.id.composer_music);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		MusicBean file = data.get(position);
		holder.name.setText(file.getName());
		holder.person.setText("δ֪");

		return convertView;
	}

	public class viewHolder {
		TextView name;
		TextView person;
	}

}
