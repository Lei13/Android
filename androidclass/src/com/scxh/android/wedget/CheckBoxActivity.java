package com.scxh.android.wedget;

import com.scxh.android.Main.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CheckBoxActivity extends Activity {
	private CheckBox mRead;
	private CheckBox mListen;
	private CheckBox mSport;
	private CheckBox mTour;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkbox);

		mRead = (CheckBox) findViewById(R.id.read);
		mListen = (CheckBox) findViewById(R.id.listenmusic);
		mSport = (CheckBox) findViewById(R.id.sport);
		mTour = (CheckBox) findViewById(R.id.tour);
		result = (TextView) findViewById(R.id.result);

		listen();
		/*mRead.setOnCheckedChangeListener(check);
		mListen.setOnCheckedChangeListener(check);
		mSport.setOnCheckedChangeListener(check);
		mTour.setOnCheckedChangeListener(check);*/
	}

	OnCheckedChangeListener check = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			setText();
		}
	};

	private void setText() {
		// TODO Auto-generated method stub
		String str;
		result.setText(" ");
		if (mRead.isChecked()) {
			str = result.getText().toString() + mRead.getText().toString()
					+ ",";
			result.setText(str);
		}
		if (mListen.isChecked()) {
			str = result.getText() + mListen.getText().toString() + ",";
			result.setText(str);
		}
		if (mSport.isChecked()) {
			str = result.getText() + mSport.getText().toString() + ",";
			result.setText(str);
		}
		if (mTour.isChecked()) {
			str = result.getText() + mTour.getText().toString() + ",";
			result.setText(str);
		}
	}

	/*
	 * 
	 */
	String str;

	public void listen() {

		mRead.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					str = result.getText().toString() + mRead.getText() + ",";
					result.setText(str);
				}
			}
		});
		mListen.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					str = result.getText().toString() + mListen.getText() + ",";
					result.setText(str);
				}
			}
		});
		mSport.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					str = result.getText().toString() + mSport.getText() + ",";
					result.setText(str);
				}
			}
		});
		mTour.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					
					str = result.getText().toString() + mTour.getText() + ",";
					result.setText(str);
				}
			}
		});

	}

}
