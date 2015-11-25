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
		item.put("title", "����������jtc");
		item.put("icon", R.drawable.meituan_image1);
		item.put("content", "��Ʒ6ѡ1���ṩ���WiFi��������ζ����");
		listData.add(item);
		
		item = new HashMap<String, Object>();
		item.put("title", "�����򹬡�ǩ��ζ���������С��");
		item.put("icon", R.drawable.meituan_image2);
		item.put("content", "30Ԫ����ȯ1�ţ�����ˮ�����ؼ۲���ȫ");
		listData.add(item);
		
		item = new HashMap<String, Object>();
		item.put("title", "������վ������������ţ�ź�");
		item.put("icon", R.drawable.meituan_image3);
		item.put("content", "������ʳ�ף�����Ʒ���ṩ���WiFi����");
		listData.add(item);
		
		item = new HashMap<String, Object>();
		item.put("title", "�������塿ţ��������");
		item.put("icon", R.drawable.meituan_image4);
		item.put("content", "�����棯����4ѡ1���ṩ���WiFi����ζ");
		listData.add(item);
		
		item = new HashMap<String, Object>();
		item.put("title", "����Ʒ���������");
		item.put("icon", R.drawable.meituan_image5);
		item.put("content", "�ػ�˫�˿����ײͣ��ṩ���WiFi����ζ");
		listData.add(item);
		
		item = new HashMap<String, Object>();
		item.put("title", "�����㳡����â̩̩������");
		item.put("icon", R.drawable.meituan_image6);
		item.put("content", "100Ԫ����ȯ1�ţ��ɵ���ʹ�ã��ṩ���");
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
