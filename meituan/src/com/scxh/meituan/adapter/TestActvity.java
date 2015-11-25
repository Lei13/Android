package com.scxh.meituan.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.scxh.meituan.ui.R;

public class TestActvity extends Activity {
	private Button mAddBtn;
	private LinearLayout mContainLayout;
	private Context context;
	private int count = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.test_layout);
		context = this;
		mAddBtn = (Button) findViewById(R.id.add_btn);
		mContainLayout = (LinearLayout) findViewById(R.id.container_layout);
		
		mAddBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Button newBtn = new Button(context);
				newBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				newBtn.setText("<Ŀ¼"+count++);
				mContainLayout.addView(newBtn);
			}
		});
		
	}
}
