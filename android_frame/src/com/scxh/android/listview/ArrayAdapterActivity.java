package com.scxh.android.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class ArrayAdapterActivity extends Activity{
	ListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arrayadapter_layout);
		String[] array = {"first","second","third","fourth"};
		mListView = (ListView) findViewById(R.id.listview_first);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.arrayadapter_item_layout, array);
		mListView.setAdapter(adapter);
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView text = (TextView)arg1;
				Toast.makeText(ArrayAdapterActivity.this, text.getText()+","+arg2+arg3, Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
}
