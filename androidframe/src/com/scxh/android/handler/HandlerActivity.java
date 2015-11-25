package com.scxh.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class HandlerActivity extends Activity {
	Button mButton1, mButton2,mButton3;
	TextView mTxt;
	Handler handler1;
	Handler handlerMain;
	Handler handlerSon;
	MyThread t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_layout);
		mButton1 = (Button) findViewById(R.id.handler_btn1);
		mButton2 = (Button) findViewById(R.id.handler_btn2);
		mButton3 = (Button) findViewById(R.id.handler_btn3);
		mTxt = (TextView) findViewById(R.id.handler_txt);
		handler1 = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					mTxt.append(msg.obj.toString());
					break;

				
				}
			}
		};
		mainHandler();
		sonHandler();
		 t1 = new MyThread();
		t1.start();
	}

	public void buttonClickListener(View view) {
		switch (view.getId()) {
		case R.id.handler_btn1:
			/*Message msg = handler1.obtainMessage();
			msg.what = 1;
			msg.obj = "hello,i am sent by handler";
			handler1.sendMessage(msg);
*/
			break;

		case R.id.handler_btn2:
			
			Message msg = Message.obtain();
			msg.obj = "t1 send";
			t1.handler.sendMessage(msg);
			
			/*Message msg1 = handlerMain.obtainMessage();
			 msg1.arg1=1;
			handlerMain.sendMessage(msg1);*/
			break;
		case R.id.handler_btn3:
			Message m = Message.obtain();
			m.arg1 = 10;
			handlerSon.sendMessage(m);
			break;
		}
		
	}

	/*
	 * 主线程looper 分配任务handler处理
	 */
	public  void mainHandler(){
		
		handlerMain = new Handler(getMainLooper(),new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				Toast.makeText(HandlerActivity.this, msg.arg1+"", Toast.LENGTH_SHORT).show();
				
				return true;
			}
		});
	}
	
	public void sonHandler(){
		handlerSon = new Handler(Looper.myLooper(), new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				int i = msg.arg1;
				Message test = new Message();
				test.arg1=i;
				
				handlerMain.sendMessage(test);
				return true;
			}
		});
	}
	
	class MyThread extends Thread{
		Handler handler;
		
		@Override
		public void run(){
			Looper.prepare();
			handler = new Handler(getMainLooper(),new Callback() {
				
				@Override
				public boolean handleMessage(Message msg) {
					mTxt.setText(msg.obj.toString());
					return true;
				}
			});
			
			
			Looper.loop();
		}
	}
	
	private void threadSendMessage(){
		MyThread t1 = new MyThread();
		Message msg = new Message();
		msg.obj = "t1 send";
		t1.handler.sendMessage(msg);
	}
}
