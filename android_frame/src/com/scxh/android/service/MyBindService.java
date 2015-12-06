package com.scxh.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/*
 * 实现服务Service与组件Activity之间交互
 * 启动服务方式 使用 BindService
 *
 * 实现步骤：
 * 1.服务类
 *   1>定义交互接口
 *   2>定义交互类继承Binder实现交互接口
 *   3>实现交互类 作为 onBinder方法返回值 ,返回给交互组件Activity。
 */
public class MyBindService extends Service {
	ServiceIBinder serviceBinder = new ServiceIBinder();
	private int number;

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					number++;
				}
			}
		}).start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	public class ServiceIBinder extends Binder implements IBcount {

		@Override
		public void setCount(int count) {
			number = count;
		}

		@Override
		public int getCount() {
			return number;
		}
	}

	public interface IBcount {
		public void setCount(int count);

		public int getCount();
	}
}
