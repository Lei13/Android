package com.warmtel.xlistview.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.warmtel.android.xlistview.XListView;
import com.warmtel.android.xlistview.XListView.IXListViewListener;
import com.warmtel.xlistview.R;
import com.warmtel.xlistview.http.demo.ConnectionUtils;
import com.warmtel.xlistview.http.demo.ConnectionUtils.CallConnectionInterface;
import com.warmtel.xlistview.http.demo.ConnectionUtils.Method;
import com.warmtel.xlistview.http.demo.Logs;

public class PagerActivity extends Activity implements IXListViewListener{
	private static final String PAGE_SIZE = "20"; //第页20条数据
	private int mTotalCount = 0;
	private int mCurrentPager = 1;
	
	private XListView mListView;
	private String mHttpUrl = "http://192.168.1.203/pageWebRoot/pager";
	private PagerMessageAdapter mPagerMessageAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.pager_list_view_layout);
		mListView = (XListView) findViewById(R.id.pager_listview);

		mPagerMessageAdapter = new PagerMessageAdapter(this);
		mListView.setAdapter(mPagerMessageAdapter);
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);
		
		getData(String.valueOf(mCurrentPager),PAGE_SIZE);
	}

	/**
	 * 
	 * @param pageNo  页号
	 * @param pageSize  页大小
	 */
	public void getData(String pageNo, String pageSize) {
		HashMap<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("pageNo", pageNo);
		parameterMap.put("pageSize", pageSize);

		ConnectionUtils connectionUtils = new ConnectionUtils();

		connectionUtils.asyncTaskConnection(mHttpUrl, parameterMap, Method.GET,
				new CallConnectionInterface() {

					@Override
					public void executeResult(String result) {
						Logs.v("result >>> "+result);
						
						Gson gson = new Gson();
						PagerMessage pagerMessage = gson.fromJson(result,PagerMessage.class);
						mTotalCount = pagerMessage.getPageCount();
						List<String> listData = pagerMessage.getListData();
						
						if(mCurrentPager == 1){
							mPagerMessageAdapter.setData(listData);
						}else{
							mPagerMessageAdapter.addData(listData);
						}
						
						mListView.stopRefresh();
						mListView.setRefreshTime("刚刚");
						
						mListView.stopLoadMore();
						
						//当加载到最后一页时，隐藏加载更多
						if(mCurrentPager == mTotalCount){
							mListView.setPullLoadEnable(false);
						}
						
						
					}
				});

	}

	public class PagerMessageAdapter extends BaseAdapter {
		private List<String> listData = new ArrayList<String>();
		private LayoutInflater mLayoutInflater;

		public PagerMessageAdapter(Context context) {
			mLayoutInflater = LayoutInflater.from(context);
		}

		public void setData(List<String> list) {
			listData = list;
			notifyDataSetChanged();
		}

		public void addData(List<String> list){
			listData.addAll(list);
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
			}
			TextView contentTxt = (TextView) convertView;

			contentTxt.setText((String) getItem(position));

			return convertView;
		}

	}

	@Override
	public void onRefresh() {
		mCurrentPager = 1;
		mListView.setPullLoadEnable(true);
		getData(String.valueOf(mCurrentPager),PAGE_SIZE);
	}

	@Override
	public void onLoadMore() {
		if(++mCurrentPager <=mTotalCount){
			getData(String.valueOf(mCurrentPager),PAGE_SIZE);
		}
		
	}

}
