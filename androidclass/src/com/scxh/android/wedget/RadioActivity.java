package com.scxh.android.wedget;

import com.scxh.android.Main.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RadioActivity extends Activity {
	RadioGroup mRadioGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wedget_radiobutton);
		mRadioGroup = (RadioGroup) findViewById(R.id.radio_normal);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				RadioButton cman = (RadioButton) mRadioGroup.getChildAt(0);
				RadioButton cwoman = (RadioButton) mRadioGroup.getChildAt(1);
				
				if (cman.isChecked()) {
					Toast.makeText(RadioActivity.this, cman.getText(),Toast.LENGTH_SHORT).show();
				}
				if (cwoman.isChecked()) {
					Toast.makeText(RadioActivity.this, cwoman.getText(), Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
	}
}
