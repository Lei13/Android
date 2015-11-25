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
		User item = new User("����������jtc",R.drawable.meituan_image1,"��Ʒ6ѡ1���ṩ���WiFi��������ζ����");
		
		listData.add(item);
		
		item = new User("�����򹬡�ǩ��ζ���������С��",R.drawable.meituan_image2,"30Ԫ����ȯ1�ţ�����ˮ�����ؼ۲���ȫ");
		listData.add(item);
		
		item = new User("������վ������������ţ�ź�",R.drawable.meituan_image3,"������ʳ�ף�����Ʒ���ṩ���WiFi����");
		listData.add(item);
		
		item = new User("�������塿ţ��������",R.drawable.meituan_image4,"�����棯����4ѡ1���ṩ���WiFi����ζ");
		listData.add(item);
		
		item = new User("����Ʒ���������",R.drawable.meituan_image5,"�ػ�˫�˿����ײͣ��ṩ���WiFi����ζ");
		listData.add(item);
		
		item = new User("�����㳡����â̩̩������",R.drawable.meituan_image6,"100Ԫ����ȯ1�ţ��ɵ���ʹ�ã��ṩ���");
		listData.add(item);
	}
	
}
