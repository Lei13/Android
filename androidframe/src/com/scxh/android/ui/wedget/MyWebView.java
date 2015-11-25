package com.scxh.android.ui.wedget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.scxh.android.frame.R;

@SuppressLint("JavascriptInterface")
public class MyWebView extends Activity {
	WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mywebactivity_layout);
		mWebView = (WebView) findViewById(R.id.my_web);

		mWebView.loadUrl("http://192.168.1.111:8080/marry.html");// д�õ�html���ص�webview��
		WebSettings setting = mWebView.getSettings();
		setting.setJavaScriptEnabled(true);// ����script����
		mWebView.addJavascriptInterface(new MyWebClick(), "htmlListener");
	}

	/*
	 * �����࣬����html�еļ����¼�
	 */
	public class MyWebClick {
		@JavascriptInterface
		public void playMusics() {
			Log.v("WebClick", "playMusic....");
			Toast.makeText(MyWebView.this, "welcome~", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
