package com.scxh.layout.frame;


import com.scxh.android.linearlayout.Login;
import com.scxh.android.linearlayout.R;
import com.scxh.android.ui.relativelayout.LinearActivity;
import com.scxh.layout.grid.GridLayout;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FrameLayout extends ListActivity {
	String[] arraylist={"test001","test002","test003","test004"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_layout_frame);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);
		setListAdapter(adapter);
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch(position){
		case 0:startActivity(new Intent(this, Login.class));
			break;
		case 1:startActivity(new Intent(this,LinearActivity.class));
			break;
		case 2:startActivity(new Intent(this,GridLayout.class));
			break;
		case 3:startActivity(new Intent(this,FrameLayout.class));
		break;
		
		}
	}
}
