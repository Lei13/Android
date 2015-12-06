package com.scxh.android.asynctask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.scxh.android.frame.R;

public class AyncDownLoadActivity extends Activity {
	String url1 = "http://img5.imgtn.bdimg.com/it/u=4196838442,126622247&fm=23&gp=0.jpg";
	String url2 = "http://img2.imgtn.bdimg.com/it/u=45596500,397231528&fm=23&gp=0.jpg";
	String url3 = "http://img1.imgtn.bdimg.com/it/u=2111648732,3690624201&fm=23&gp=0.jpg";
	String url4 = "http://img0.imgtn.bdimg.com/it/u=2081529157,1978722497&fm=23&gp=0.jpg";
	String url5 = "http://img5.imgtn.bdimg.com/it/u=2531484665,1615000788&fm=23&gp=0.jpg";
	String url6 = "http://img0.imgtn.bdimg.com/it/u=3135582527,842621827&fm=23&gp=0.jpg";
	String url7 = "http://img4.imgtn.bdimg.com/it/u=3756685971,1441134629&fm=23&gp=0.jpg";
	String url8 = "http://img0.imgtn.bdimg.com/it/u=3472918996,2339968434&fm=23&gp=0.jpg";
	String url9 = "http://img3.imgtn.bdimg.com/it/u=1050285812,2225196415&fm=23&gp=0.jpg";
	String[] mImageDta = { url1, url2, url3, url4, url5, url6, url7, url8, url9 };
	// ArrayList<Bitmap> mData;
	// GridAdapter adapter;
	// TextView mIsDown;
	Executor mExecutor;
	GridView mGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aync_download_activity_layout);
		mGrid = (GridView) findViewById(R.id.show_pic_down_grid);
		// mIsDown = (TextView) findViewById(R.id.isdown_txt);
		// adapter = new GridAdapter(AyncDownLoadActivity.this);

		mExecutor = new ThreadPoolExecutor(10, 150, 10, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		/*
		 * mExecutor = new ThreadPoolExecutor(50, 250, 10, TimeUnit.SECONDS, new
		 * LinkedBlockingDeque<Runnable>());
		 */
		Executor mExecutor;

		//10 150 1 0 ��Ӧ�� ͬ�rִ�е��߳���/ ���߳���/ ���Еr�g���r�gһ�������̕����ݚ�
		mExecutor = new ThreadPoolExecutor(10, 150, 10, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		GridViewAdapter adapter = new GridViewAdapter(this);
		mGrid.setAdapter(adapter);
		 adapter.setData(mImageDta);

	}

	public class MyAdapter extends BaseAdapter {
		String[] imgData = {};
		LayoutInflater inflater;

		public void setData(String[] imgData) {
			this.imgData = imgData;
			notifyDataSetChanged();
		}

		MyAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return imgData.length;
		}

		@Override
		public Object getItem(int position) {
			return imgData[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView img;
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.imgview_child_gridview_layout, null);
				img = (ImageView) convertView.findViewById(R.id.img_child_grid);
				convertView.setTag(img);
			} else {
				img = (ImageView) convertView.getTag();
			}

			String url = (String) getItem(position);
			new AsyncTask<String, Void, Bitmap>() {

				@Override
				protected Bitmap doInBackground(String... params) {

					try {
						URL ur = new URL(params[0]);
						HttpURLConnection con = (HttpURLConnection) ur
								.openConnection();
						InputStream is = con.getInputStream();
						Bitmap bm = BitmapFactory.decodeStream(is);
						return bm;

					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					return null;
				}

				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						img.setImageBitmap(result);
					}
				};
			}.executeOnExecutor(mExecutor, url);

			return convertView;

		}
	}

	public class MyGridViewAdapter extends BaseAdapter {
		private ArrayList<Bitmap> list = new ArrayList<Bitmap>();
		private LayoutInflater infalter;

		public void addDate(Bitmap bm) {
			list.add(bm);
			notifyDataSetChanged();
		}

		public MyGridViewAdapter(Context context) {
			infalter = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageview;
			if (convertView == null) {
				convertView = infalter.inflate(
						R.layout.imgview_child_gridview_layout, null);
				imageview = (ImageView) convertView
						.findViewById(R.id.img_child_grid);
				convertView.setTag(imageview);
			} else {
				imageview = (ImageView) convertView.getTag();
			}

			Bitmap bitmap = (Bitmap) getItem(position);
			imageview.setImageBitmap(bitmap);

			return convertView;
		}

	}

	public class GridViewAdapter extends BaseAdapter {
		String[] mImageDta = {};
		private LayoutInflater infalter;

		public void setData(String[] imageDta) {
			this.mImageDta = imageDta;
			notifyDataSetChanged();//ˢ������Դ
		}

		public GridViewAdapter(Context context) {
			infalter = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return mImageDta.length;
		}

		@Override
		public Object getItem(int position) {
			return mImageDta[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ImageView imageview;
			if (convertView == null) {
				convertView = infalter.inflate(
						R.layout.imgview_child_gridview_layout, null);
				imageview = (ImageView) convertView
						.findViewById(R.id.img_child_grid);
				convertView.setTag(imageview);
			} else {
				imageview = (ImageView) convertView.getTag();
			}

			String httpUrl = (String) getItem(position);

			new AsyncTask<String, Void, Bitmap>() {

				@Override
				protected Bitmap doInBackground(String... params) {
					return downLoadPicture(params[0]);
				}

				protected void onPostExecute(Bitmap result) {
					if (result != null) {
						imageview.setImageBitmap(result);
					}
				}
			}.execute(httpUrl); // ������һ��һ��ִ��

			// }.executeOnExecutor(mExecutor,httpUrl); //������ͬʱִ��
			return convertView;
		}

	}

	/**
	 * ��������ͼƬ
	 */
	public Bitmap downLoadPicture(String httpUrl) {
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();

			Bitmap bitmap = BitmapFactory.decodeStream(is);

			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * ֱ��ͨ���첽���� ���Ƽ���ûˢ��һ������Դ��Ҫ����Ŷһ��getview
	 */
	/*
	 * private void downPicByAsyn() { new AsyncTask<String, Bitmap, String>() {
	 * protected void onPreExecute() { mData = new ArrayList<Bitmap>(); };
	 *
	 * @Override protected String doInBackground(String... params) { for (int i
	 * = 0; i < params.length; i++) { try { url = new URL(params[i]);
	 * HttpURLConnection con = (HttpURLConnection) url .openConnection();
	 * InputStream is = con.getInputStream();
	 *
	 * publishProgress(BitmapFactory.decodeStream(is));
	 *
	 * } catch (IOException e) { e.printStackTrace(); } } String result =
	 * "�������"; return result; };
	 *
	 * @Override protected void onProgressUpdate(Bitmap... values) {
	 * mIsDown.setText("������..."); mData.add(values[0]); adapter.setData(mData);
	 * };
	 *
	 * protected void onPostExecute(String result) { mIsDown.setText(""); };
	 *
	 * }.execute(url1, url2, url3, url4, url9, url6, url7, url8, url5);
	 *
	 * }
	 *
	 * public class GridAdapter extends BaseAdapter { ArrayList<Bitmap> data =
	 * new ArrayList<Bitmap>(); LayoutInflater inflater;
	 *
	 * public GridAdapter(Context context) { inflater =
	 * LayoutInflater.from(context); }
	 *
	 * public void setData(ArrayList<Bitmap> data) { this.data = data;
	 * notifyDataSetChanged(); }
	 *
	 * @Override public int getCount() { return data.size(); }
	 *
	 * @Override public Object getItem(int position) { return
	 * data.get(position); }
	 *
	 * @Override public long getItemId(int position) { return position; }
	 *
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { ImageView img; if (convertView == null) { convertView =
	 * inflater.inflate( R.layout.imgview_child_gridview_layout, null); img =
	 * (ImageView) convertView.findViewById(R.id.img_child_grid);
	 * convertView.setTag(img); } else { img = (ImageView) convertView.getTag();
	 * }
	 *
	 * img.setImageBitmap(data.get(position));
	 *
	 * return convertView; }
	 *
	 * }
	 */
}