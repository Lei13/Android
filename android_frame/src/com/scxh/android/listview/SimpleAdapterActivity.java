package com.scxh.android.listview;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class SimpleAdapterActivity extends Activity implements OnItemClickListener{
	ArrayList<HashMap<String,Object>> mListData;
	ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleadapter_layout);
		mListView = (ListView) findViewById(R.id.simpleadapter_listview);
		mListData = new ArrayList<HashMap<String,Object>>();
		setData(mListData);

		String[] from = {"icon","title","content"};
		int[] to = {R.id.simpleadapter_item_img,R.id.simpleadapter_item_title,R.id.simpleadapter_item_content};
		SimpleAdapter adapter = new SimpleAdapter(this, mListData, R.layout.simpleadapter_item_layout, from, to);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);

	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		SimpleAdapter adapter = (SimpleAdapter) arg0.getAdapter();
		HashMap data = (HashMap) adapter.getItem(arg2);
		data.get("title");
		data.get("icon");
		data.get("content");
		Toast.makeText(SimpleAdapterActivity.this, data.get("title")+" "+data.get("content"), Toast.LENGTH_SHORT).show();
	}



	public void setData(ArrayList<HashMap<String,Object>> listData){
		HashMap<String,Object> item = new HashMap<String, Object>();
		item.put("title", "【琉璃场】jtc");
		item.put("icon", R.drawable.meituan_image1);
		item.put("content", "饮品6选1，提供免费WiFi，精致美味，幸");
		listData.add(item);

		item = new HashMap<String, Object>();
		item.put("title", "【青羊宫】签百味冷锅串串·小吃");
		item.put("icon", R.drawable.meituan_image2);
		item.put("content", "30元代金券1张，除酒水饮料特价菜外全");
		listData.add(item);

		item = new HashMap<String, Object>();
		item.put("title", "【火车南站】汉釜宫烤肉牛排海");
		item.put("icon", R.drawable.meituan_image3);
		item.put("content", "单人主食套，有赠品，提供免费WiFi，美");
		listData.add(item);

		item = new HashMap<String, Object>();
		item.put("title", "【柳浪湾】牛蹄筋老面馆");
		item.put("icon", R.drawable.meituan_image4);
		item.put("content", "招牌面／米线4选1，提供免费WiFi，美味");
		listData.add(item);

		item = new HashMap<String, Object>();
		item.put("title", "【优品道】渔夫烤鱼");
		item.put("icon", R.drawable.meituan_image5);
		item.put("content", "特惠双人烤鱼套餐，提供免费WiFi，美味");
		listData.add(item);

		item = new HashMap<String, Object>();
		item.put("title", "【万达广场】榴芒泰泰国餐厅");
		item.put("icon", R.drawable.meituan_image6);
		item.put("content", "100元代金券1张，可叠加使用，提供免费");
		listData.add(item);
	}



	class Adapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}}
}
