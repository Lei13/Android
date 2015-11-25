package com.scxh.meituan.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class MoreActivity extends Activity {
	ListView mMoreTopList;
	ListView mMoreDownList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.moreactivity_layout);
		mMoreTopList = (ListView) findViewById(R.id.more_list_top);
		setAdapter();
		
	}

	private void setAdapter() {
		MoreAdapter adapter = new MoreAdapter(this);
		adapter.setData(getTopData());
		mMoreTopList.setAdapter(adapter);
	
	}

	private ArrayList<HashMap<String, Object>> getTopData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("text", "字号大小");
		map.put("view", "中号字(默认)");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("text", "清空缓存");
		map.put("view", "202K");
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("text", "意见反馈");
		map.put("view", "");
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("text", "支付帮助");
		map.put("view", "");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("text", "检查更新");
		map.put("view", "");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("text", "关于美团");
		map.put("view", "");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("text", "加入我们");
		map.put("view", "");
		data.add(map);
		map = new HashMap<String, Object>();
		map.put("text", "诊断网络");
		map.put("view", "");
		data.add(map);
		return data;
	}


	
	public class MoreAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		LayoutInflater inflater;

		public	MoreAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		private void setData(ArrayList<HashMap<String, Object>> data) {
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
			Widget widget;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.more_item_layout_1,
						null);
				widget = new Widget();
				widget.txt = (TextView) convertView
						.findViewById(R.id.more_item_txt);
				widget.view = (TextView) convertView
						.findViewById(R.id.more_item_img);
				convertView.setTag(widget);
			} else {
				widget = (Widget) convertView.getTag();
			}

			HashMap<String, Object> map = data.get(position);

			widget.txt.setText(map.get("text").toString());
			widget.view.setText(map.get("view").toString());
			if (position != data.size() - 1) {
				widget.view.setCompoundDrawablesWithIntrinsicBounds(
						null,
						null,
						getResources().getDrawable(
								R.drawable.group_myhomepage_arrow_right), null);
			widget.view.setCompoundDrawablePadding(10);
			}

			return convertView;
		}

		class Widget {
			TextView txt;
			TextView view;
		}
	}
}
