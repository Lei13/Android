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
	 * <Params,Progress,Result> ������������Params:�����첽����Ĳ������ͣ�ͨ��.excute()���룩
	 * Progress:����ִ���в������������ͣ�ͨ��publishProgress��������onProgressUpdate������
	 * Result:����ִ�����Ժ󷵻ص��������ͣ���onPostExecute��������
	 */
	public class MyAsyncTask extends AsyncTask<Void, Integer, String> {
		/*
		 * ����ִ�еķ���
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		/*
		 * ִ���첽�����������̣߳����ܸ���UI�߳�
		 * ���ص�ֵΪonPostExecute�����еĲ���
		 */
		@Override
		protected String doInBackground(Void... params) {

			for (int i = 0; i < 100; i++) {
				mSbar.setProgress(i);
				publishProgress(i);// i ΪonProgressUpdate������ֵ��
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String result = "�������";
			return result;
		}

		/*
		 * �����첽������ͨ��publishProgress(??)���͵�ֵ �ؼ���ˢ�£�����valuesΪdoInBackground������ͨ��
		 * publishProgress(values)��������ֵ
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			mShowTxt.setText(values[0] + "");
		}

		/*
		 * �����첽������ɺ�ķ���ֵ ��������ִ�����Ժ���ִ�еĴ��룬����result ΪdoInBackground�����з��ص�ֵ
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mShowTxt.setText(result);
		}

	}

}
