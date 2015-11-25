package com.scxh.android.ui.wedget;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scxh.android.frame.R;
import com.scxh.android.ui.wedget.ViewPagerActivity.MyPagerAdapter;

public class ViewPagerActivity extends Activity {
	ViewPager mViewPager;// viewpager ��ʵ��
	ArrayList<View> mData;// ����Դ��item
	ArrayList<View> mPage = new ArrayList<View>();// СԲ��ؼ�list,
	int oldPosition = 0;// ��¼��һҳ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		initView();
		setAdapter();

		mPage.add(findViewById(R.id.dot_1));
		mPage.add(findViewById(R.id.dot_2));
		mPage.add(findViewById(R.id.dot_3));
		setListener();
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
	}

	private void setAdapter() {
		MyPagerAdapter adapter = new MyPagerAdapter();
		adapter.setData(getData());
		mViewPager.setAdapter(adapter);
	}

	private void setListener() {
		mPage.get(0).setBackgroundResource(R.drawable.press_press_viewpager);// Ĭ��״̬��һ�ν�ȥ��һҳ����ѡ��״̬

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				mPage.get(oldPosition).setBackgroundResource(
						R.drawable.initstate_viewpager);
				mPage.get(arg0).setBackgroundResource(
						R.drawable.press_press_viewpager);

				oldPosition = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	/*
	 * ��ȡ����ԴItem--->View
	 */
	private ArrayList<View> getData() {
		ArrayList<View> data = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(this);
		View v1 = inflater.inflate(R.layout.viewpager_item1_layout, null);
		data.add(v1);
		View v2 = inflater.inflate(R.layout.viewpager_item2_layout, null);
		data.add(v2);
		View v3 = inflater.inflate(R.layout.viewpager_item3_layout, null);
		data.add(v3);
		return data;

	}

	private void getPageData() {

	}

	/*
	 * �Զ��������� getCount() boolean isViewFromObject destroyItem
	 */
	public class MyPagerAdapter extends PagerAdapter {
		ArrayList<View> data = new ArrayList<View>();// ����Դ����ʼ��--��ʵ���У�������Դ�����磬�����ָ��

		public void setData(ArrayList<View> data) {
			this.data = data;
			notifyDataSetChanged();// ��������
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = data.get(position);
			container.addView(v);
			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(data.get(position));
		}
	}
}