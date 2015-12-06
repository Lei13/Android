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
 * apache�������󹤾���
 */
public class HttpConnectUtil {
	HttpConnectionResponse httpResponse;// ���x�Ľӿ�

	public enum Method {
		GET, POST;
	}

	/*
	 * public static final String GET = "GET"; public static final String POST
	 * ="POST";
	 */

	/** ����Ľӿڣ����ڻص� */
	public interface HttpConnectionResponse {
		public void handleHttpResponse(String response);
	}

	/*
	 * ���ô����ݵ�����
	 */
	public void httpConnect(final Method requestMethod, String httpUrl,

	HttpConnectionResponse response) {
		httpResponse = response;
		new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				HttpClient client = new DefaultHttpClient();
				HttpUriRequest request;
				/* get����ʽ */
				if (Method.GET.equals(requestMethod)) {
					request = getRequest(params[0], null);

				}
				/* post����ʽ */
				else if (requestMethod.equals(Method.POST)) {
					request = postRequest(params[0], null);
				} else {
					throw new NullPointerException("����ʽֻ��Ϊget����post");
				}
				return connectSuccessResponse(request, client);
			}

			/* �Է��ؽ����һ�������ص���ʽʵ��* */
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
				/* get����ʽ */
				if (requestMethod.equals(Method.GET)) {
					request = getRequest(params[0], values);

				}
				/* post����ʽ */
				else if (requestMethod.equals(Method.POST)) {
					request = postRequest(params[0], values);
				} else {
					throw new NullPointerException("����ʽֻ��Ϊget����post");
				}
				return connectSuccessResponse(request, client);
			}

			/* �Է��ؽ����һ�������ص���ʽʵ��* */
			@Override
			protected void onPostExecute(String result) {
				httpResponse.handleHttpResponse(result);
			}
		}.execute(httpUrl);

	}

	/*
	 * ��ͬ����״̬�Ĳ�ͬ����ʽ
	 */
	private String connectSuccessResponse(HttpUriRequest request,
			HttpClient client) {
		try {
			HttpResponse response = client.execute(request);
			/* code�����ص���Ӧ״̬�� */
			/* 200���ɹ���4��ͷ���ͻ��˴���5������������ */
			int code = response.getStatusLine().getStatusCode();

			switch (code) {

			case HttpURLConnection.HTTP_OK:// 200
				return EntityUtils.toString(response.getEntity());

			case 404:
				return "�ļ����ܱ��ҵ�";

			default:
				return "�����쳣";

			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * get��ʽ��������
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
	 * post��ʽ��������
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
	 * ͨ��post����ʽ������
	 */
	private void sendParamsByPost(HttpPost request,
			HashMap<String, Object> values) {
		ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
		/* ����hashmap�����Iֵ������BasicNameValuePair�� */
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
