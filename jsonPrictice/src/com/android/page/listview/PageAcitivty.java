package com.android.page.listview;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.prictice.json.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.warmtel.android.xlistview.XListView;
import com.warmtel.android.xlistview.XListView.IXListViewListener;

import cz.msebera.android.httpclient.Header;

public class PageAcitivty extends Activity implements IXListViewListener,
		OnScrollListener {
	XListView mPageList;
	ProgressBar mProgress;
	
	ListAdapter adapter;
	int mCurrentPage = 1;
	int mDataPerPage = 20;
	int mTotalPage=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_activity_layout);
		mPageList = (XListView) findViewById(R.id.page_listview);
		mProgress = (ProgressBar) findViewById(R.id.progress_list);
		adapter = new ListAdapter(this);
		mPageList.setAdapter(adapter);
		getListData(mCurrentPage);

		mPageList.setEmptyView(mProgress);
		mPageList.setXListViewListener(this);
		mPageList.setPullLoadEnable(true);
		

	}

	private void getListData(final int currentPage) {
		final ArrayList<String> mData = new ArrayList<String>();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("dataPerPage", mDataPerPage);
		params.put("currentPage", currentPage);
		client.get("http://192.168.1.111:8080/servlet/page", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							org.json.JSONObject response) {
						super.onSuccess(statusCode, headers, response);
						try {
							org.json.JSONArray array = response
									.getJSONArray("content");
							mTotalPage = response.getInt("totalpage");
							for (int i = 0; i < array.length(); i++) {
								String s = (String) array.get(i);
								mData.add(s);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (currentPage==1) {
							adapter.setData(mData);
						}else {
							adapter.addData(mData);
						}
						
						mPageList.stopLoadMore();
						mPageList.stopRefresh();
					}
				});
	}

	boolean scrollFlag;

	@Override
	public void onRefresh() {
		mCurrentPage = 1;
		mPageList.setPullRefreshEnable(true);
		getListData(mCurrentPage);
	}

	@Override
	public void onLoadMore() {
		if (++mCurrentPage > mTotalPage) {
			mCurrentPage = mTotalPage;
			mPageList.setPullLoadEnable(false);
		} else {
			getListData(mCurrentPage);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollFlag && scrollState == SCROLL_STATE_IDLE) {
			scrollFlag = false;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
			getListData(++mCurrentPage);
		}

	}

	public class ListAdapter extends BaseAdapter {
		ArrayList<String> data = new ArrayList<String>();
		LayoutInflater inflater;
		Context context;

		public ListAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		public void setData(ArrayList<String> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		public void addData(ArrayList<String> data) {
			this.data.addAll(data);
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
			TextView text = null;
			if (convertView == null) {
				convertView = inflater.inflate(
						android.R.layout.simple_list_item_1, null);
			}
			text = (TextView) convertView;

			text.setText(data.get(position) + "");
			return convertView;
		}

	}

}
