package com.scxh.meituan.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.meituan.ui.R;

public class GridAdapter extends BaseAdapter{
	ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	LayoutInflater inflater;
	
	public GridAdapter(Context context){
		inflater = LayoutInflater.from(context);
	}
	public void setData(ArrayList<HashMap<String,Object>> data){
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
		MyWidget widget;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.deal_item_grid_laylout, null);
			widget = new MyWidget();
			widget.img = (ImageView) convertView.findViewById(R.id.main_item_img);
			widget.name = (TextView) convertView.findViewById(R.id.main_item_name);
			widget.discount = (TextView) convertView.findViewById(R.id.main_item_saleprice);
			widget.allPrice = (TextView) convertView.findViewById(R.id.main_item_allprice);
			convertView.setTag(widget);
		}else{
			widget = (MyWidget) convertView.getTag();
		}
		
		HashMap<String, Object> map = data.get(position);
		
		widget.img.setImageResource((Integer)map.get("image"));
		widget.name.setText(map.get("name").toString());
		widget.discount.setText(map.get("discount").toString());
		widget.allPrice.setText(map.get("allPrice").toString());
		widget.allPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		return convertView;
	}
	class MyWidget{
		ImageView img;
		TextView name;
		TextView discount;
		TextView allPrice;
	}	
}