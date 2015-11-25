package com.scxh.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/*
 * ʵ�ַ���Service�����Activity֮�佻��
 * ��������ʽ ʹ�� BindService
 * 
 * ʵ�ֲ��裺
 * 1.������
 *   1>���彻���ӿ�
 *   2>���彻����̳�Binderʵ�ֽ����ӿ�
 *   3>ʵ�ֽ����� ��Ϊ onBinder��������ֵ ,���ظ��������Activity��
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
