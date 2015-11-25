package com.scxh.android.intent;


import com.scxh.android.frame.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ListActivity extends Activity {
	Button mDetailBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intent_list_layout);
		mDetailBtn = (Button) findViewById(R.id.button_list);
	}
	
	public void detailButtonClickListener(View v){
		Uri data = Uri.parse("15102225364");
		Intent intent = new Intent();
		/*ComponentName component = new ComponentName(this, DetailActivity.class);
		intent.setComponent(component);*/
		intent.setAction(Intent.ACTION_DIAL);
		//intent.setType(TELEPHONY_SERVICE);
		//intent.setData(data);
		startActivity(intent);
	}
	
}
