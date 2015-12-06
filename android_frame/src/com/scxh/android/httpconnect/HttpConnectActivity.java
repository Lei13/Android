package com.scxh.android.httpconnect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.scxh.android.frame.R;
import com.scxh.android.httpconnect.HttpConnectUtil.HttpConnectionResponse;
import com.scxh.android.httpconnect.HttpConnectUtil.Method;

public class HttpConnectActivity extends Activity implements OnClickListener {
	Button mConnectBtn, mClientBtn, mClientPostBtn;
	Button mUtilBtn, mUtilTestBtn;
	TextView mShowTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.http_connect_activity_layout);
		initView();

		mConnectBtn.setOnClickListener(this);
		mClientBtn.setOnClickListener(this);
		mClientPostBtn.setOnClickListener(this);
		mUtilBtn.setOnClickListener(this);
		mUtilTestBtn.setOnClickListener(this);

	}

	private void initView() {
		mShowTxt = (TextView) findViewById(R.id.http_show_txt);
		mConnectBtn = (Button) findViewById(R.id.http_request_btn);
		mClientBtn = (Button) findViewById(R.id.http_client_btn);
		mClientPostBtn = (Button) findViewById(R.id.http_client_post_btn);
		mUtilBtn = (Button) findViewById(R.id.http_util_post_btn);
		mUtilTestBtn = (Button) findViewById(R.id.http_util_test_btn);

	}

	@Override
	public void onClick(View v) {
		String httpUrl = "http://192.168.1.156/app/print";
		switch (v.getId()) {
			case R.id.http_util_test_btn:
				HashMap<String, Object> para = new HashMap<String, Object>();
				para.put("user", "如果你");
				//para.put("p", "1454");//http://192.168.1.112:8080/app/myweb
				new HttpConnectUtil().httpConnect(Method.GET, "http://192.168.1.111:8080/WEB/mine.html",para, new HttpConnectionResponse() {

					@Override
					public void handleHttpResponse(String response) {
						mShowTxt.setText(response);
					}
				});

				break;

			case R.id.http_request_btn:
				new AsyncTask<String, Void, String>() {
					@Override
					protected String doInBackground(String... params) {
						return buildHttpConnect(params[0]);
					}

					/*得到响应数据的处理，赋给textView */
					@Override
					protected void onPostExecute(String result) {
						mShowTxt.setText(result);

					};
				}.execute(httpUrl);
				break;

			case R.id.http_client_btn:
				new AsyncTask<String, Void, String>() {

					@Override
					protected String doInBackground(String... params) {
						HttpClient client = new DefaultHttpClient();
						HttpGet request = new HttpGet(params[0]);
						try {
							HttpResponse reponse = client.execute(request);
							return EntityUtils.toString(reponse.getEntity());

						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

						return null;
					}

					@Override
					protected void onPostExecute(String result) {
						mShowTxt.setText(result);
					}

				}.execute(httpUrl);

				break;
			// httpClient post 方式传参
			case R.id.http_client_post_btn:

				new AsyncTask<String, Void, String>() {

					@Override
					protected String doInBackground(String... params) {
						HttpClient client = new DefaultHttpClient();
						HttpPost request = new HttpPost(params[0]);
						try {
							NameValuePair user = new BasicNameValuePair("user",
									"admin就");
							NameValuePair password = new BasicNameValuePair("psw",
									"ad564");
							ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
							parameters.add(user);
							parameters.add(password);
						/* 传参加解决客户端参数显示乱码 */
							HttpEntity entity = new UrlEncodedFormEntity(
									parameters, "utf-8");
							request.setEntity(entity);

							HttpResponse response = client.execute(request);
							return EntityUtils.toString(response.getEntity());
						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

						return null;
					}

					protected void onPostExecute(String result) {
						mShowTxt.setText(result);
					};

				}.execute("http://192.168.1.156/app/login");

				break;
			case R.id.http_util_post_btn:
				HashMap<String, Object> values = new HashMap<String, Object>();
				values.put("user", "get人口结构");
				values.put("psw", "123");
				urlConnectionUtils("GET", "http://192.168.1.156/app/login", values);
				break;
		}

	}

	private String buildHttpConnect(String url) {
		try {
			/* get请求传参方式url=url +"?user=admin&psw=123" */
			URL httpUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) httpUrl
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(5000);
			connection.connect();

			/* post请求 传参方式 */
			String name = encoding("来看湖南台");
			OutputStream os = connection.getOutputStream();
			PrintWriter writer = new PrintWriter(os);
			writer.print("user=" + name + "&psw=123");
			writer.flush();

			/* 读取服务器响应内容 */
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));

			StringBuilder builder = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				builder.append(str);
			}

			return builder.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String encoding(String str) throws UnsupportedEncodingException {
		return URLEncoder.encode(str, "UTF-8");
	}

	public void urlConnectionUtils(final String requestMehtod, String httpUrl,
								   final HashMap<String, Object> parameters) {

		new AsyncTask<String, Void, String>() {
			HttpUriRequest request;
			HttpResponse response;

			@Override
			protected void onPostExecute(String result) {
				mShowTxt.setText(result + "111");
			}

			@Override
			protected String doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
				if (requestMehtod.equalsIgnoreCase("GET")) {
					try {
						if (parameters != null) {

							params[0] = params[0] + "?";
							for (String key : parameters.keySet()) {
								params[0] = params[0] + key + "="
										+ parameters.get(key) + "&";
							}
						}
						request = new HttpGet(params[0]);
						response = client.execute(request);
						return EntityUtils.toString(response.getEntity());
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						request = new HttpPost(params[0]);
						if (parameters != null) {
							ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
							for (String key : parameters.keySet()) {
								BasicNameValuePair values = new BasicNameValuePair(
										key, (String) parameters.get(key));
								pairs.add(values);
							}
							HttpEntity entity = new UrlEncodedFormEntity(pairs,
									"UTF-8");
							((HttpPost) request).setEntity(entity);
						}
						response = client.execute(request);

						return EntityUtils.toString(response.getEntity());

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				return null;

			}
		}.execute(httpUrl);

	}

}
