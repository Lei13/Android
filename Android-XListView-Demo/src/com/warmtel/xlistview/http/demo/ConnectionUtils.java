package com.warmtel.xlistview.http.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;

public class ConnectionUtils {

	public enum Method {
		GET, POST
	}

	// 实现接口回调 第一步定义接口
	public interface CallConnectionInterface {
		public void executeResult(String result);
	}

	// 实现接口回调 第二步 声明接口，定义注册接口入口
	public CallConnectionInterface mCallConnectionInterface;

	public void setonCallConnectionInterface(
			CallConnectionInterface callConnectionInterface) {
		mCallConnectionInterface = callConnectionInterface;
	}

	public ConnectionUtils() {
	}
	
	public ConnectionUtils(CallConnectionInterface callConnectionInterface) {
		mCallConnectionInterface = callConnectionInterface;
	}

	public void asyncTaskConnection(final String url,
			final HashMap<String, String> parameterMap, final Method method,
			CallConnectionInterface callConnectionInterface) {
		
		mCallConnectionInterface = callConnectionInterface;
		new AsyncTask<Void, Void, String>() {
			protected String doInBackground(Void... params) {
				String result = "";
				try {
//					try {
//						Thread.sleep(2000); //网速太快了加一个效果
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
					result = getHttpConnection(url, parameterMap, method);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return result;

			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				// ====第三步 调用接口方法
				mCallConnectionInterface.executeResult(result);
			}
		}.execute();

	}

	public String getHttpConnection(String url,
			HashMap<String, String> parameterMap, Method httpMethod)
			throws ClientProtocolException, IOException {

		HttpClient httpClient = new DefaultHttpClient();
		HttpUriRequest request = getHttpUrlRequest(url, parameterMap,httpMethod);
		HttpResponse response = httpClient.execute(request);

		int statusCode = response.getStatusLine().getStatusCode();
		Logs.v("getHttpConnection  statusCode  :"+statusCode);
		if (statusCode == HttpStatus.SC_OK) {

//			String content = EntityUtils.toString(response.getEntity());
			
			String content = readIt(response.getEntity().getContent());

			return content;
		} else {
			return "请服务端出错";
		}
	}

	public HttpUriRequest getHttpUrlRequest(String url,
			HashMap<String, String> parameterMap, Method method)
			throws ClientProtocolException, IOException {
		if (method == Method.POST) {
			
			HttpPost httpPost = new HttpPost(url);
			// =========================组装Post参数====================
			if(parameterMap!=null){
				ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
	
				Set<String> set = parameterMap.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String name = parameterMap.get(key);
					System.out.println("key " + key + "name :" + name);
	
					BasicNameValuePair namePair = new BasicNameValuePair(key, name);
					parameters.add(namePair);
				}
	
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
						HTTP.UTF_8);
	
				httpPost.setEntity(entity);
			}
			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded; charset=utf-8");

			return httpPost;

		} else {
			if (parameterMap != null) {
				// ================组装Get请求参数===============================
				// String url =
				// "http://192.168.1.203/web1502/login_servle?userName=张三&passWord=123";

				if (url.indexOf("?") < 0) {
					url = url + "?";
				}

				Set<String> set = parameterMap.keySet();
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					String name = parameterMap.get(key);
					System.out.println("key " + key + "name :" + name);

					url = url + "&" + key + "=" + name;
				}

				Logs.v("URL :" + url);
				// ================组装Get请求参数===============================
			}

			HttpGet httpGet = new HttpGet(url);

			return httpGet;
		}
	}
	
	
	/**
	 * 将InputStream转换成String返回
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public String readIt(InputStream stream) throws IOException,
			UnsupportedEncodingException {
		Reader reader = new InputStreamReader(stream, "UTF-8");
		// 创建包装流
		BufferedReader br = new BufferedReader(reader);
		// 定义String类型用于储存单行数据
		String line = null;
		// 创建StringBuffer对象用于存储所有数据
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		return sb.toString();

	}
}
