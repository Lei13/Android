package com.scxh.android.listview;

import java.util.ArrayList;
import com.scxh.android.frame.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class DefinedActivity extends Activity {
	ArrayList<User> mListData = new ArrayList<User>();
	ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simpleadapter_layout);
		mListView = (ListView) findViewById(R.id.simpleadapter_listview);
		mListData = new ArrayList<User>();
		setData(mListData);
		
		MyBaseAdapter adapter = new MyBaseAdapter(this);
		adapter.setData(mListData);
		mListView.setAdapter(adapter);
		
	}

	public void setData(ArrayList<User> listData){
		User item = new User("【琉璃场】jtc",R.drawable.meituan_image1,"饮品6选1，提供免费WiFi，精致美味，幸");
		
		listData.add(item);
		
		item = new User("【青羊宫】签百味冷锅串串·小吃",R.drawable.meituan_image2,"30元代金券1张，除酒水饮料特价菜外全");
		listData.add(item);
		
		item = new User("【火车南站】汉釜宫烤肉牛排海",R.drawable.meituan_image3,"单人主食套，有赠品，提供免费WiFi，美");
		listData.add(item);
		
		item = new User("【柳浪湾】牛蹄筋老面馆",R.drawable.meituan_image4,"招牌面／米线4选1，提供免费WiFi，美味");
		listData.add(item);
		
		item = new User("【优品道】渔夫烤鱼",R.drawable.meituan_image5,"特惠双人烤鱼套餐，提供免费WiFi，美味");
		listData.add(item);
		
		item = new User("【万达广场】榴芒泰泰国餐厅",R.drawable.meituan_image6,"100元代金券1张，可叠加使用，提供免费");
		listData.add(item);
	}
	
}
