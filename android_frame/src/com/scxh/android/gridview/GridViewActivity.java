package com.scxh.android.gridview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.scxh.android.frame.R;

public class GridViewActivity extends Activity{
	List<MyFile> mData;
	GridView mGrid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_layout);
		mData = getData();
		mGrid = (GridView) findViewById(R.id.gridview_test);
		GridAdapter adapter = new GridAdapter(this);
		adapter.setData(mData);
		mGrid.setAdapter(adapter);

	}


	public List<MyFile> getData(){
		List<MyFile> data = new ArrayList<MyFile>();
		MyFile file = new MyFile("11", R.drawable.meituan_image1);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image2);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image3);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image4);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image5);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image6);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image7);
		data.add(file);

		file = new MyFile("11", R.drawable.meituan_image8);
		data.add(file);

		return data;
	}












	/*
	 * 自定义适配器
	 */

	class GridAdapter extends BaseAdapter{
		LayoutInflater inflater;
		Context context;
		List<MyFile> data = new ArrayList<MyFile>();


		GridAdapter(Context context){
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		public void setData(List<MyFile> data){
			this.data = data;
			notifyDataSetChanged();
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
			ImageView image;
			//	View view;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_gridview_test_layout, null);
				image = (ImageView) convertView.findViewById(R.id.grid_img);
				convertView.setTag(image);
			}else {
				//view = convertView;
				image = (ImageView) convertView.getTag();
			}

			MyFile file = data.get(position);
			image.setBackgroundResource(file.getPicture());
			//view.setiImageResource(file.getName());

			return convertView;
		}

	}


	class MyFile{
		private String name;
		private int picture;

		MyFile(String name,int picture){
			this.name = name;
			this.picture = picture;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPicture() {
			return picture;
		}
		public void setPicture(int picture) {
			this.picture = picture;
		}

	}
}
