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

public class MainActivity extends Activity {
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
										MainActivity.this);
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
			//loadPic(holder.img, url);
			//loadPicture(holder.img, url, R.drawable.ic_launcher);
			AsyncMemoryFileCacheImageLoader.getInstance(context).loadBitmap(getResources(), url, holder.img);
			return convertView;
		}

		private void loadPic(final ImageView img, final String url) {
			new AsyncTask<String, Void, Bitmap>() {
				Map<String, Bitmap> cache = new HashMap<String, Bitmap>();

				@Override
				protected Bitmap doInBackground(String... params) {
					Bitmap pic = getPicByUrl(params[0]);
					cache.put(url, pic);
					return pic;
				}

				protected void onPostExecute(Bitmap result) {
					img.setImageBitmap(result);
				};
			}.execute(url);
		}

		class ViewHolder {
			TextView title, content, location, distance;
			ImageView img, cardImg, groupImg, ticketImg;
		}

		public  void loadPicture(ImageView image,String httpUrl,int resId){
			
			
			if (isMatch(httpUrl,image)) {//图片跟下载地址不一致
				//建一個任務，將image保存在弱引用里
				LoadPicAsyncTask asyncTask = new LoadPicAsyncTask(image);
				/*將任務保存在AsyncDrawable中，*/
				AsyncDrawable drawable = new AsyncDrawable(asyncTask,BitmapFactory.decodeResource(getResources(), resId));//異步任務保存在drawable里
				image.setImageDrawable(drawable);
				asyncTask.execute(httpUrl);
			}
			
			
		}
		/*
		 * 判断图片跟下载地址是否一致，ture 不一致，任务取消
		 */
		public boolean isMatch(String url,ImageView image){
			LoadPicAsyncTask task = getAsyncTask(image);
			if (task!=null) {
				if (!url.equals(task.data)) {
					task.cancel(true);
				}else {
					return false;
				}
			}
			return true;
			
		}
		
		/*
		 *判断，imageview是否被加载过，是AsyncDrawable的实例
		 */
		public  LoadPicAsyncTask getAsyncTask(ImageView image){
			if (image!=null) {
				Drawable drawable = image.getDrawable();
				if (drawable instanceof AsyncDrawable) {
					AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
					return asyncDrawable.getAsyncTask();
				}
				
			}
			
			return null;
		}
		
		class AsyncDrawable extends BitmapDrawable {//保存異步任務
			WeakReference<LoadPicAsyncTask> asyncWeakRf;
			AsyncDrawable(LoadPicAsyncTask async,Bitmap bit) {
				super(getResources(), bit);
				asyncWeakRf = new WeakReference<MainActivity.ListAdapter.LoadPicAsyncTask>(async);
			}
			public LoadPicAsyncTask getAsyncTask(){
				return asyncWeakRf.get();
			}
		}
/*
 * 每执行一次异步任务（加载一次图片）就将此imageview保存起来
 */
		class LoadPicAsyncTask extends AsyncTask<String, Void, Bitmap> {
			WeakReference<ImageView> imageViewReference;//弱引用，保存 imageview
			String data = "";
			
			LoadPicAsyncTask(ImageView image) {
				imageViewReference = new WeakReference<ImageView>(image);
			}

			@Override
			protected Bitmap doInBackground(String... params) {
				data = params[0];
				return getPicByUrl(data);
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				if (isCancelled()) {
					result = null;
				}
				
				if (imageViewReference!=null&&result!=null) {
					ImageView img  = imageViewReference.get();
					LoadPicAsyncTask task = getAsyncTask(img);
					if (this==task&&img!=null) {
						img.setImageBitmap(result);
					}
					
				}
				
				
			}
		}

		/*
		 * 从url上获取图片转换成bitmap
		 */
		private Bitmap getPicByUrl(String url) {
			try {
				URL picUrl = new URL(url);
				// HttpURLConnection con = (HttpURLConnection)
				// picUrl.openConnection();
				// InputStream is = con.getInputStream();
				InputStream in = picUrl.openStream();

				return BitmapFactory.decodeStream(in);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

	}
}
