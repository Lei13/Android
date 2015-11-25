package com.scxh.meituan.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scxh.meituan.constance.ColumnName;

public class RegisterActivity extends Activity {
	SQLiteDatabase mDb;
	TextView mUserTxt;
	TextView mPasswordTxt;
	Button mRegisterBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		initView();
		setRegisterClickListener();
	}

	private void initView() {
		mUserTxt = (TextView) findViewById(R.id.user_input_txt);
		mPasswordTxt = (TextView) findViewById(R.id.password_input_txt);
		mRegisterBtn = (Button) findViewById(R.id.register_btn);
		MyDbHelpler helper = new MyDbHelpler(this);
		mDb = helper.getReadableDatabase();
	}

	private void setRegisterClickListener() {
		mRegisterBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isRegister(mUserTxt.getText().toString()) == true) {
					Log.v("tag","onClick: ");
					String name = mUserTxt.getText().toString();
					String password = mPasswordTxt.getText().toString();
//					mDb.execSQL("insert into "
//							+ ColumnName.UserTable.TABLE_NAME + " values("+name+"," + password + ")");
					ContentValues values = new ContentValues();
					values.put(ColumnName.UserTable.COLUMN_USERNAME, name);
					values.put(ColumnName.UserTable.COLUMN_PASSWORD, password);
					mDb.insert(ColumnName.UserTable.TABLE_NAME, null, values);
					
					
					
					
					Toast.makeText(RegisterActivity.this, "注册成功",
							Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(RegisterActivity.this, "用户已存在",
							Toast.LENGTH_SHORT).show();
			}
		});
	}

	private boolean isRegister(String userName) {
		
		if (userName != null) {
			Log.v("tag","userName: "+ userName);
			Cursor cursor = mDb.query(ColumnName.UserTable.TABLE_NAME,
					new String[] { ColumnName.UserTable.COLUMN_USERNAME }, ""
							+ ColumnName.UserTable.COLUMN_USERNAME + " = ?",
					new String[] { userName }, null, null, null);
			Log.v("tag","cursor: ");
			if (cursor.getCount() > 0) {
				return false;
			} else {
				return true;
			}
		}else {
			Toast.makeText(this, "信息不能为空", Toast.LENGTH_SHORT);
			return false;
		}
		

	}
}
