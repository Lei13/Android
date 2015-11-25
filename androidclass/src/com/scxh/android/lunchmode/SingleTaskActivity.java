package com.scxh.android.lunchmode;

import com.scxh.android.Main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SingleTaskActivity extends Activity {
	//Intent mintent;
	TextView mText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lunchmode_singletask);
		mText = (TextView) findViewById(R.id.text_sendmessage);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String info = null;
		switch (requestCode) {
		case 2:
			info = data.getStringExtra("info");
			break;
		case 3:
			info = data.getStringExtra("infor");
			break;
		default:
			break;
		}
		mText.setText(info); 
	}
	
	
	
	
	
	//四种参数的传递
	public void baseTypeClickButton(View v) {
		Intent intent = new Intent(this, SingleTopActivity.class);
		intent.putExtra(Constance.INFO_KEY, "hello~i'am base type");
		intent.putExtra(Constance.TYPE_KEY, Constance.type.BASEDATA);
		//startActivity(intent);
		int requestCode = 2;
		startActivityForResult(intent, requestCode);

	}

	public void bundleTypeClickButton(View v) {
		Intent intent = new Intent(this, SingleTopActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(Constance.INFO_KEY, "hello,it's bundle");
		intent.putExtra(Constance.TYPE_KEY, Constance.type.BUNDLE);
		intent.putExtras(bundle);
		//startActivity(intent);
		int requestCode = 3;
		startActivityForResult(intent, requestCode);
	}

	public void serializableClickButton(View v) {
		Student stu = new Student();
		stu.setName("Nina");
		stu.setAge(23);
		Intent intent = new Intent(this, SingleTopActivity.class);
		intent.putExtra(Constance.TYPE_KEY, Constance.type.SERIALIZABLE);
		intent.putExtra(Constance.INFO_KEY, stu);
		startActivity(intent);
	}

	public void pacelableClickButton(View v) {
		User user = new User();
		user.setName("Tina");
		user.setPassword("111333");
		user.setAge(26);
		Intent intent = new Intent(this, SingleTopActivity.class);
		intent.putExtra(Constance.INFO_KEY, user);
		intent.putExtra(Constance.TYPE_KEY, Constance.type.PARCELABLE);
		startActivity(intent);
	}

}
