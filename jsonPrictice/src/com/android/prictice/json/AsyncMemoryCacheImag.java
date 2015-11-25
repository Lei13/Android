package com.android.prictice.json;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class AsyncMemoryCacheImag {
	private	LruCache<String, Bitmap> mMemoryCache;

	public AsyncMemoryCacheImag(){
		initLruCache();
	}
	
	/**
	 * ��ʼ��LRUCache
	 */
	public void initLruCache(){
		// ��ȡӦ�ó����������ڴ�
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		// ����ͼƬ�����СΪ�����������ڴ��1/8
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}
	/**
	 * ���ͼƬ���ڴ滺��
	 */
	public void addBitmapToCache(String url,Bitmap bitmap){
		if(getCacheBitmap(url) == null){
			mMemoryCache.put(url, bitmap);
		}
	}
	/**
	 * �ӻ����л�ȡͼƬ
	 * @param url
	 * @return
	 */
	public Bitmap getCacheBitmap(String url){
		Bitmap bitmap = mMemoryCache.get(url);
		return bitmap;
	}
	
	/**
	 * ͨ������ ListView �� GridView ����ͼ�����ʹ��������ʾ��AsyncTask ����ʱ��ͬʱ��������һ�����⡣
	 * Ϊ�˸���Ч�Ĵ����ڴ棬��Щ��ͼ������������û�������Ļʱ��ѭ��ʹ�á����ÿһ������ͼ������һ��AsyncTask ��
	 * ��ô���޷�ȷ����ǰ��ͼ�ڽ���taskʱ���������ͼ�Ѿ�����ѭ�������и�����һ������ͼ�������á� ����, �޷�ȷ�����е�
	 * �첽�����ܹ���˳��ִ����ϡ�
	 * 
	 * @param imageUrl
	 * @param imageView
	 * @param resId
	 *            Ĭ��ͼƬ��Դ
	 */
	public void loadBitmap(Resources res, String imageUrl, ImageView imageView,int resId) {
		// ��һ��������ͼƬ��ַ���ж�ͼƬ�Ƿ񱻻������ڴ�
		Bitmap bitmap = getCacheBitmap(imageUrl);
		if(bitmap != null){
			imageView.setImageBitmap(bitmap);
			return;
		}

		if (cancelPotentialWork(imageUrl, imageView)) {
			BitmapWorkerTask task = new BitmapWorkerTask(imageView);

			AsyncDrawable asyncDrawable = new AsyncDrawable(res,
					BitmapFactory.decodeResource(res, resId), task);
			imageView.setImageDrawable(asyncDrawable);

			task.execute(imageUrl);
		}
	}

	public static boolean cancelPotentialWork(String imageUrl,
			ImageView imageView) {
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final String bitmapData = bitmapWorkerTask.data;
			if (!bitmapData.equals(imageUrl)) {
				// Cancel previous task
				bitmapWorkerTask.cancel(true);
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was
		// cancelled
		return true;
	}

	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			final Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
				return asyncDrawable.getBitmapWorkerTask();
			}
		}
		return null;
	}

	/**
	 * ����һ��ר�õ�Drawable�����������淵�ع�����������á�����������£����������ʱBitmapDrawable�ᱻʹ��
	 * 
	 */
	static class AsyncDrawable extends BitmapDrawable {
		private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

		public AsyncDrawable(Resources res, Bitmap bitmap,
				BitmapWorkerTask bitmapWorkerTask) {
			super(res, bitmap);
			bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
		}

		public BitmapWorkerTask getBitmapWorkerTask() {
			return (BitmapWorkerTask) bitmapWorkerTaskReference.get();
		}
	}

	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
		private final WeakReference<ImageView> imageViewReference;// ������
																	// ���ص㵱�ڴ治��ʱ���Զ�����
		private String data = "";

		public BitmapWorkerTask(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(String... params) {
			data = params[0];
			return downLoadPicture(data);
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}

			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = (ImageView) imageViewReference
						.get();
				final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
				if (this == bitmapWorkerTask && imageView != null) {
					imageView.setImageBitmap(bitmap);
					
					addBitmapToCache(data, bitmap);
					
				}
			}
		}
	}

	
	/**
	 * ��������ͼƬ
	 * 
	 * @param url
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
}
