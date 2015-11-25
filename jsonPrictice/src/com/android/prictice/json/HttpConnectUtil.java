package com.android.prictice.json;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

/*
 * apache�������󹤾���
 */
public class HttpConnectUtil {
	HttpConnectionResponse httpResponse;// ���x�Ľӿ�

	public enum Method {
		GET, POST;
	}

	/*
	 * public static final String GET = "GET"; 
	 * public static final String POST ="POST";
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

			/* �Է��ؽ����һ���������ص���ʽʵ��* */
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

			/* �Է��ؽ����һ���������ص���ʽʵ��* */
			@Override
			protected void onPostExecute(String result) {
				httpResponse.handleHttpResponse(result);
			}
		}.execute(httpUrl);

	}

	/*
	 * ��ͬ����״̬�Ĳ�ͬ������ʽ
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
		String url = httpUrl + "?";
		if (values != null) {
			for (String key : values.keySet()) {
				url = url + key + "=" + values.get(key) + "&";
			}
		}

		HttpGet request = new HttpGet(url);
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