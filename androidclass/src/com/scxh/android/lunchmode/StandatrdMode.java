package com.scxh.android.lunchmode;

import com.scxh.android.Main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StandatrdMode extends Activity{
	TextView mText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lunchmode_standard);
		mText = (TextView) findViewById(R.id.text_page1);
		mText.setText("taskid: "+getTaskId());
	}

	public void changeToTwoPage(View v){
		startActivity(new Intent(this,SingleInstanceActivity.class));
	}
}
