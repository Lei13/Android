package com.scxh.android.Main;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.scxh.android.layout.FrameActivity;
import com.scxh.android.lunchmode.StandatrdMode;
import com.scxh.android.wedget.CheckBoxActivity;
import com.scxh.android.wedget.WeChatActivity;

public class MainActivity extends ListActivity {
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	//String[] title = {"info","intent"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = getData();
	SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_expandable_list_item_1, new String[]{"title"}, new int[] {android.R.id.text1});
	setListAdapter(adapter);	
	}

	public ArrayList<HashMap<String,Object>> getData(){
		//HashMap<String,Object> item = new HashMap<String, Object>();
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		addItem(list, "布局管理", new Intent(this,FrameActivity.class));
		addItem(list, "启动模式luchmode", new Intent(this,StandatrdMode.class));
		addItem(list, "控件", new Intent(this,CheckBoxActivity.class));
		addItem(list, "wechat", new Intent(this,WeChatActivity.class));
	return list;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String title =  (String) list.get(position).get("title");
		Intent intent = (Intent) list.get(position).get("intent");
		startActivity(intent);
	}
	
	
	
	
	protected void addItem(ArrayList<HashMap<String, Object>> list, String name,
			Intent intent) {
		HashMap<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		list.add(temp);
	}
	
	
	

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Map<String, Object>> data = getData();
		setListAdapter(new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 }));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Map<String, Object> map = (Map<String, Object>) l .getItemAtPosition(position);

		Intent intent = (Intent) map.get("intent");
		startActivity(intent);
	}

	public List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		addItem(list, "单帧布局", new Intent(this, FrameActivity.class));
		addItem(list, "线性布局", new Intent(this, LinearActivity.class));
		addItem(list, "相对布局", new Intent(this, RelativeActivity.class));

		return list;
	}

	protected void addItem(List<Map<String, Object>> list, String name,
			Intent intent) {
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("title", name);
		temp.put("intent", intent);
		list.add(temp);
	}
*/
}
