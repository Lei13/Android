package com.scxh.android.wedget;

import com.scxh.android.Main.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class WeChatActivity extends Activity {
	EditText user;
	EditText password;
	ImageButton clearuser, clearPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wechat);
		getView();//实例化对象
		userListener();//对用户名文本框进行监听
		passwordListener();//对密码文本框进行监听
		
		if (savedInstanceState!=null) {
			String uses = savedInstanceState.getString("content");
			user.setText(uses);
		}
	}
//实例化对象
	public void getView(){
		user = (EditText) findViewById(R.id.user_edit);
		password = (EditText) findViewById(R.id.password_edit);
		clearuser = (ImageButton) findViewById(R.id.clear_user_img);
		clearPassword = (ImageButton) findViewById(R.id.clear_password_img);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		String  content = user.getText().toString();
		outState.putString("content", content);
	}
	
	
	
	
	
	
	
	
	
	
	//对用户输入框焦点/光标监听+clear按钮
	public void userListener() {
		user.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					if (!user.getText().toString().isEmpty()) {
						clearuser.setVisibility(View.VISIBLE);
					}
				}else clearuser.setVisibility(View.INVISIBLE);
			}
		});
		
		user.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s != null) {

					clearuser.setVisibility(BIND_AUTO_CREATE);
				} else
					clearuser.setVisibility(TRIM_MEMORY_UI_HIDDEN);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (user.getText().toString().isEmpty()) {
					clearuser.setVisibility(TRIM_MEMORY_UI_HIDDEN);
				}
				
			}
		});

	}
//对密码监听+
	public void passwordListener() {
		password.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					if (!(password.getText().toString().isEmpty())) {
						clearPassword.setVisibility(View.VISIBLE);
					}
					
				}else {
					clearPassword.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (s != null) {

					clearPassword.setVisibility(BIND_AUTO_CREATE);
				} else
					clearPassword.setVisibility(TRIM_MEMORY_UI_HIDDEN);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (password.getText().toString().isEmpty()) {
					clearPassword.setVisibility(TRIM_MEMORY_UI_HIDDEN);
				}
			}
		});

	}

	public void userEditClear(View v) {
		user.setText("");
		clearuser.setVisibility(TRIM_MEMORY_UI_HIDDEN);
	}

	public void passwordEditClear(View v) {
		password.setText("");
		clearPassword.setVisibility(TRIM_MEMORY_UI_HIDDEN);
	}

}