package com.scxh.android.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class LayoutActivity extends ListActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Map<String, Object>> data = getData();
		
		setListAdapter(new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1}));
	

	
}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		
		return null;
	}
}
