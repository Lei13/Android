package com.android.prictice.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.prictice.json.R.drawable;
import com.android.prictice.json.util.AsyncMemoryFileCacheImageLoader;
import com.android.prictice.json.util.HttpConnectUtil;
import com.android.prictice.json.util.HttpConnectUtil.HttpConnectionResponse;
import com.android.prictice.json.util.HttpConnectUtil.Method;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainClientActivity extends Activity {
	ListView mList;

	
	// ArrayList<JSONObject> mdata ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mList = (ListView) findViewById(R.id.list);
		getData();

	}

	private void getData() {

		new HttpConnectUtil().httpConnect(Method.GET,
				"http://192.168.1.111:8080/servlet/jpson",
				new HttpConnectionResponse() {

					@Override
					public void handleHttpResponse(String response) {
						if (response != null) {
							ArrayList<JSONObject> jObjList = new ArrayList<JSONObject>();
							try {

								JSONObject object = new JSONObject(response);
								JSONObject objec = object.getJSONObject("info");
								JSONArray array = objec
										.getJSONArray("merchantKey");
								for (int i = 0; i < array.length(); i++) {
									JSONObject obj = (JSONObject) array.get(i);
									jObjList.add(obj);
								}

								ListAdapter adapter = new ListAdapter(
										MainClientActivity.this);
								adapter.setData(jObjList);
								mList.setAdapter(adapter);

							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}
				});

	}

	public class ListAdapter extends BaseAdapter {
		ArrayList<JSONObject> data = new ArrayList<JSONObject>();
		LayoutInflater inflater;
		Context context;

		public ListAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		public void setData(ArrayList<JSONObject> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_list_layout, null);
				holder = new ViewHolder();
				holder.cardImg = (ImageView) convertView
						.findViewById(R.id.pic_card_item);
				holder.groupImg = (ImageView) convertView
						.findViewById(R.id.pic_group_item);
				holder.ticketImg = (ImageView) convertView
						.findViewById(R.id.pic_ticket_item);
				holder.img = (ImageView) convertView
						.findViewById(R.id.pic_item);

				holder.title = (TextView) convertView
						.findViewById(R.id.title_item);
				holder.content = (TextView) convertView
						.findViewById(R.id.cotent_item);
				holder.distance = (TextView) convertView
						.findViewById(R.id.distance_item);
				holder.location = (TextView) convertView
						.findViewById(R.id.location_item);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			JSONObject json = data.get(position);
			String YES = "YES";
			String url = "";
			try {
				url = json.getString("picUrl");

				holder.title.setText(json.getString("name"));
				holder.content.setText(json.getString("coupon"));
				holder.location.setText(json.getString("location"));
				holder.distance.setText(json.getString("distance"));

				if (YES.equals(json.getString("couponType"))) {
					holder.ticketImg.setImageResource(R.drawable.near_ticket);
					// holder.ticketImg.setVisibility(View.GONE);
				}
				if (YES.equals(json.getString("cardType"))) {
					holder.cardImg.setImageResource(R.drawable.near_card);
				}
				if (YES.equals(json.getString("groupType"))) {
					holder.groupImg.setImageResource(R.drawable.near_group);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			AsyncHttpClient client = new AsyncHttpClient();
		/*	client.get(url, new JsonHttpResponseHandler() {
				public void onSuccess(int statusCode,
						cz.msebera.android.httpclient.Header[] headers,
						JSONArray response) {
				};
			});*/
			return convertView;
		}

		class ViewHolder {
			TextView title, content, location, distance;
			ImageView img, cardImg, groupImg, ticketImg;
		}

	}
}
