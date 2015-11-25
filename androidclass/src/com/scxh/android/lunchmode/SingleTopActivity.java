package com.scxh.android.lunchmode;

import com.scxh.android.Main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleTopActivity extends Activity {
	TextView mShowMessage;
	Intent intent ;
	Button mExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lunchmode_singletop);
		mShowMessage = (TextView) findViewById(R.id.text_receivemessage);
		intent = getIntent();
		mExit = (Button) findViewById(R.id.button_exit);
		
		int type = intent.getIntExtra(Constance.TYPE_KEY, 1);
		switch (type) {
		case 1:
			baseTypeShow();
			break;
		case 2:
			bundleTypeShow();
			break;
		case 3:
			serializableShow();
			break;
		case 4:
			parcelableShow();
			break;
		default:
			break;
		}
	}

	
	public void exitbtnListener(View v){
		//Intent intent = new Intent(this,SingleTaskActivity.class);
		intent.putExtra("info", "回传值=basedata");
		intent.putExtra("infor", "回传值=bundletype");
		int resultCode = 1;
		setResult(resultCode, intent);
		startActivity(intent);
		finish();
	}
	
	
	
	
	//四种参数的接收
	public void baseTypeShow() {
		String str = intent.getStringExtra(Constance.INFO_KEY);
		mShowMessage.setText(str);
	}

	public void bundleTypeShow() {
		Bundle bundle = intent.getExtras();
		String str = bundle.getString(Constance.INFO_KEY);
		mShowMessage.setText(str);
	}

	public void serializableShow() {
		Student stu = (Student) intent.getSerializableExtra(Constance.INFO_KEY);
		Log.v("tag", "stu"+stu);
		
		mShowMessage.setText("name: " + stu.getName() + " age:" + stu.getAge());
	}

	public void parcelableShow() {
		User user = intent.getParcelableExtra(Constance.INFO_KEY);
		mShowMessage.setText("name: " + user.getName() + "password:"
				+ user.getPassword() + "age: " + user.getAge());

	}
}
