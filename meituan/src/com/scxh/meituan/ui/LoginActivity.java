package com.scxh.meituan.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.scxh.meituan.constance.ColumnName;

public class LoginActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {
	SQLiteDatabase mDb;
	EditText mUserEdt, mPasswordEdt;
	CheckBox mRememberCbx;
	ImageButton userClearBtn, passwordClearBtn;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);

		initView();
		registerListener();
		SharedPreferences preference = getSharedPreferences("com.meituan.login_prefrence_file_key",MODE_PRIVATE);
		if (preference != null) {
			mUserEdt.setText(preference.getString("user", ""));
			mPasswordEdt.setText(preference.getString("password", ""));
			mRememberCbx.setChecked(true);
		}
	}

	/*
	 * 登录按钮的安卓监听方式
	 */
	public void loginButtonListener(View v) {
		if (isLogin(mUserEdt.getText().toString(), mPasswordEdt.getText()
				.toString()) == true) {
			Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, GuideActivity.class));
			finish();
		} else {
			Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
		}
	}

	private void initView() {
		mUserEdt = (EditText) findViewById(R.id.login_user_edt);
		mPasswordEdt = (EditText) findViewById(R.id.login_password_edt);
		userClearBtn = (ImageButton) findViewById(R.id.login_user_clear);
		passwordClearBtn = (ImageButton) findViewById(R.id.login_password_clear);
		mRememberCbx = (CheckBox) findViewById(R.id.login_remember_user);
		MyDbHelpler helper = new MyDbHelpler(this);
		mDb = helper.getReadableDatabase();

		
	}

	/*
	 * 为控件注册监听
	 */
	private void registerListener() {
		mUserEdt.setOnFocusChangeListener(this);
		mPasswordEdt.setOnFocusChangeListener(this);

		userClearBtn.setOnClickListener(this);
		passwordClearBtn.setOnClickListener(this);

		mRememberCbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					preferences = getSharedPreferences(
							"com.meituan.login_prefrence_file_key", MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("user", mUserEdt.getText().toString());
					editor.putString("password", mPasswordEdt.getText()
							.toString());
					editor.commit();

				} else {
					preferences.edit().clear().commit();

				}

			}
		});
	}

	/*
	 * 实现接口的方式为ImageButton 清除键设置焦点监听
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_user_clear:
			if (!mUserEdt.getText().toString().isEmpty()) {
				mUserEdt.setText("");
			}
			break;

		case R.id.login_password_clear:
			if (!mUserEdt.getText().toString().isEmpty()) {
				mPasswordEdt.setText("");
			}
			break;

		}

	}

	/*
	 * 实现接口的方式为EditText设置焦点监听
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.login_user_edt:
			if (hasFocus && !mUserEdt.getText().toString().isEmpty()) {
				userClearBtn.setVisibility(View.VISIBLE);
			} else if (!hasFocus) {
				userClearBtn.setVisibility(View.INVISIBLE);
			}
			break;

		case R.id.login_password_edt:

			if (hasFocus && !mPasswordEdt.getText().toString().isEmpty()) {
				passwordClearBtn.setVisibility(View.VISIBLE);
			} else if (!hasFocus) {
				passwordClearBtn.setVisibility(View.INVISIBLE);
			}
			break;
		}
	}

	private boolean isLogin(String name, String password) {
		String selection = ColumnName.UserTable.COLUMN_USERNAME + "=?";
		String[] columns = { name, };

		/*
		 * Cursor cursor = mDb.query(ColumnName.UserTable.TABLE_NAME, null,
		 * selection, columns, null, null, null);
		 */

		Cursor cursor = mDb.rawQuery("select * from user where username='"
				+ name + "' and password = '" + password + "'", null);

		if (cursor.getCount() > 0) {
			return true;
		}
		/*
		 * if (cursor.getCount() > 0) { while (cursor.moveToNext()) { String
		 * username = cursor.getString(cursor
		 * .getColumnIndex(ColumnName.UserTable.COLUMN_PASSWORD)); Log.v("tag",
		 * "username: " + username);
		 * 
		 * return username.equals(password); } }
		 */
		return false;

	}

	public void freeRegisterClickListener(View view) {
		startActivity(new Intent(this, RegisterActivity.class));
	}

	private void setCheckBoxListener() {
		mRememberCbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {
					SharedPreferences msPreferences = getSharedPreferences(
							"com.scxh.meituan.ui.PREFERENCES_FILE_KEY",
							MODE_PRIVATE);
					SharedPreferences.Editor editor = msPreferences.edit();
					editor.putString("user", mUserEdt.getText().toString());
					editor.putString("password", mPasswordEdt.getText()
							.toString());
					editor.commit();
				}
			}
		});
	}
}
