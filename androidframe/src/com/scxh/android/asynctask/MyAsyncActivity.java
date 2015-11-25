package com.scxh.android.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class MyAsyncActivity extends Activity implements OnClickListener {
	TextView mShowTxt;
	Button mAsyncBtn, mHandlerBtn;
	SeekBar mSbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myasynctask_activity_layout);
		initView();
	}

	private void initView() {
		mShowTxt = (TextView) findViewById(R.id.my_async_show_txt);
		mAsyncBtn = (Button) findViewById(R.id.my_async_btn);
		mHandlerBtn = (Button) findViewById(R.id.my_hander_btn);
		mSbar = (SeekBar) findViewById(R.id.my_async_sbar);

		mAsyncBtn.setOnClickListener(this);
		mHandlerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_hander_btn:
			new Thread(new Runnable() {
				@Override
				public void run() {

					for (int i = 0; i < 100; i++) {
						mSbar.setProgress(i);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			break;

		case R.id.my_async_btn:
			new MyAsyncTask().execute();
			break;
		}

	}

	/*
	 * <Params,Progress,Result> 三个参数类型Params:传入异步任务的参数类型（通过.excute()传入）
	 * Progress:任务执行中产生的数据类型，通过publishProgress方法传到onProgressUpdate来处理
	 * Result:任务执行完以后返回的数据类型，在onPostExecute中来处理
	 */
	public class MyAsyncTask extends AsyncTask<Void, Integer, String> {
		/*
		 * 最先执行的方法
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/*
		 * 执行异步任务，属于子线程，不能更新UI线程
		 * 返回的值为onPostExecute方法中的参数
		 */
		@Override
		protected String doInBackground(Void... params) {

			for (int i = 0; i < 100; i++) {
				mSbar.setProgress(i);
				publishProgress(i);// i 为onProgressUpdate方法的值参
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String result = "下载完成";
			return result;
		}

		/*
		 * 处理异步任务中通过publishProgress(??)发送的值 控件的刷新，参数values为doInBackground方法中通过
		 * publishProgress(values)传过来的值
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mShowTxt.setText(values[0] + "");
		}

		/*
		 * 处理异步任务完成后的返回值 上面任务执行完以后再执行的代码，参数result 为doInBackground方法中返回的值
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mShowTxt.setText(result);
		}

	}

}
