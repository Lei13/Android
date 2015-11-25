package com.scxh.android.music.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.scxh.android.music.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	LayoutInflater inflater;
	private ArrayList<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();

	public GridAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setData(ArrayList<HashMap<String, Object>> data) {
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_grid_find_layout, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.pic_grid);
			holder.name = (TextView) convertView.findViewById(R.id.name_grid);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		HashMap<String, Object> map = mData.get(position);
		holder.name.setText(map.get("grid_name").toString());
		holder.img.setImageResource((Integer)map.get("grid_img"));
		return convertView;
	}
	public class ViewHolder{
		TextView name;
		ImageView img;
		
	}

}
