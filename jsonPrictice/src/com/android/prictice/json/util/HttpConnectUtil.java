package com.android.prictice.json.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;

import com.loopj.android.http.HttpGet;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/*
 * apache网络请求工具类
 */
public class HttpConnectUtil {
	HttpConnectionResponse httpResponse;// 定義的接口

	public enum Method {
		GET, POST;
	}

	/*
	 * public static final String GET = "GET"; public static final String POST
	 * ="POST";
	 */

	/** 定义的接口，用于回调 */
	public interface HttpConnectionResponse {
		public void handleHttpResponse(String response);
	}

	/*
	 * 不用传数据的请求
	 */
	public void httpConnect(final Method requestMethod, String httpUrl,

	HttpConnectionResponse response) {
		httpResponse = response;
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
				HttpUriRequest request;
				/* get请求方式 */
				if (Method.GET.equals(requestMethod)) {
					request = getRequest(params[0], null);

				}
				/* post请求方式 */
				else if (requestMethod.equals(Method.POST)) {
					request = postRequest(params[0], null);
				} else {
					throw new NullPointerException("请求方式只能为get或者post");
				}
				return connectSuccessResponse(request, client);
			}

			/* 对返回结果的一个处理，回调方式实现* */
			@Override
			protected void onPostExecute(String result) {
				if (result != null) {
					httpResponse.handleHttpResponse(result);
				}

			}
		}.execute(httpUrl);

	}

	public void httpConnect(final Method requestMethod, String httpUrl,
			final HashMap<String, Object> values,
			HttpConnectionResponse response) {
		httpResponse = response;
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
				HttpUriRequest request;
				/* get请求方式 */
				if (requestMethod.equals(Method.GET)) {
					request = getRequest(params[0], values);

				}
				/* post请求方式 */
				else if (requestMethod.equals(Method.POST)) {
					request = postRequest(params[0], values);
				} else {
					throw new NullPointerException("请求方式只能为get或者post");
				}
				return connectSuccessResponse(request, client);
			}

			/* 对返回结果的一个处理，回调方式实现* */
			@Override
			protected void onPostExecute(String result) {
				httpResponse.handleHttpResponse(result);
			}
		}.execute(httpUrl);

	}

	/*
	 * 不同连接状态的不同处理方式
	 */
	private String connectSuccessResponse(HttpUriRequest request,
			HttpClient client) {
		try {
			HttpResponse response = client.execute(request);
			/* code：返回的响应状态码 */
			/* 200：成功，4开头：客户端错误，5：服务器错误 */
			int code = response.getStatusLine().getStatusCode();

			switch (code) {

			case HttpURLConnection.HTTP_OK:// 200
				return EntityUtils.toString(response.getEntity());

			case 404:
				return "文件不能被找到";

			default:
				return "处理异常";

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * get方式请求连接
	 */

	private HttpUriRequest getRequest(String httpUrl,
			HashMap<String, Object> values) {

		if (values != null) {
			httpUrl = httpUrl + "?";
			for (String key : values.keySet()) {
				httpUrl = httpUrl + key + "=" + values.get(key) + "&";
			}
		}
		HttpGet request = new HttpGet(httpUrl);

		return request;
	}

	/*
	 * post方式请求连接
	 */
	private HttpUriRequest postRequest(String httpUrl,
			HashMap<String, Object> values) {

		HttpPost request = new HttpPost(httpUrl);
		if (values != null) {
			sendParamsByPost(request, values);
		}
		return request;

	}

	/*
	 * 通过post请求方式传参数
	 */
	private void sendParamsByPost(HttpPost request,
			HashMap<String, Object> values) {
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		/* 遍历hashmap，將鍵值對存入BasicNameValuePair中 */
		for (String key : values.keySet()) {
			BasicNameValuePair value = new BasicNameValuePair(key,
					(String) values.get(key));
			pairs.add(value);
		}
		try {
			request.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

}
