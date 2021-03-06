package com.scxh.android.frame;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.scxh.android.asynctask.AyncDownLoadActivity;
import com.scxh.android.asynctask.MyAsyncActivity;
import com.scxh.android.broadcastreceiver.MyBroadcastActivity;
import com.scxh.android.broadcastreceiver.MyReceiverActivity;
import com.scxh.android.fragment.FragmentActivity;
import com.scxh.android.gridview.GridViewActivity;
import com.scxh.android.handler.HandlerActivity;
import com.scxh.android.httpconnect.HttpConnectActivity;
import com.scxh.android.listview.ArrayAdapterActivity;
import com.scxh.android.listview.DefinedActivity;
import com.scxh.android.listview.SimpleAdapterActivity;
import com.scxh.android.mediapalyer.MediaPlayerActivity;
import com.scxh.android.notification.NotificationActivity;
import com.scxh.android.pasexml.BookListActivity;
import com.scxh.android.service.MyBindServerActivity;
import com.scxh.android.service.MyStartServiceActivity;
import com.scxh.android.sql.curosradapter.CursorActivity;
import com.scxh.android.store.file.FileActivity;
import com.scxh.android.store.sql.MyDatabaseActivity;
import com.scxh.android.ui.actionbar.ActionBarActivity;
import com.scxh.android.ui.menu.OptionMenuActivity;
import com.scxh.android.ui.shape.ShapeActivity;
import com.scxh.android.ui.tabhost.MyTabHostActivity;
import com.scxh.android.ui.wedget.DialogActivity;
import com.scxh.android.ui.wedget.MyWebView;
import com.scxh.android.ui.wedget.ViewPagerActivity;

public class MainActivity extends Activity {
	ListView mLlist;
	ArrayList<HashMap<String, Object>> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLlist = (ListView) findViewById(R.id.main_list);
		mData = getData();
		SimpleAdapter adapter = new SimpleAdapter(this, mData,
				android.R.layout.simple_list_item_1, new String[] { "title" },
				new int[] { android.R.id.text1 });
		mLlist.setAdapter(adapter);

		mLlist.setSelection(mData.size() - 1);

		mLlist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				HashMap<String, Object> map = mData.get(arg2);
				Intent intent = (Intent) map.get("intent");
				startActivity(intent);
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getData() {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		addItem(list, "arrayAdapter", new Intent(MainActivity.this,
				ArrayAdapterActivity.class));
		addItem(list, "simpleAdapter", new Intent(MainActivity.this,
				SimpleAdapterActivity.class));
		addItem(list, "自定义baseadapter", new Intent(MainActivity.this,
				DefinedActivity.class));
		/*addItem(list, "spinner下拉列表", new Intent(MainActivity.this,
				ooSpinnerActivity.class));*/
		addItem(list, "gridview", new Intent(MainActivity.this,
				GridViewActivity.class));
		addItem(list, "viewpager滑屏", new Intent(MainActivity.this,
				ViewPagerActivity.class));
		addItem(list, "Dialog对话�", new Intent(MainActivity.this,
				DialogActivity.class));
		addItem(list, "tabHost", new Intent(MainActivity.this,
				MyTabHostActivity.class));
		addItem(list, "shape画图优化", new Intent(MainActivity.this,
				ShapeActivity.class));
		addItem(list, "menu", new Intent(MainActivity.this,
				OptionMenuActivity.class));
		addItem(list, "actionbar", new Intent(MainActivity.this,
				ActionBarActivity.class));
		addItem(list, "handler", new Intent(MainActivity.this,
				HandlerActivity.class));
		addItem(list, "数据库基本操�", new Intent(MainActivity.this,
				MyDatabaseActivity.class));
		addItem(list, "cursoradapter", new Intent(MainActivity.this,
				CursorActivity.class));
		addItem(list, "File读写", new Intent(MainActivity.this,
				FileActivity.class));
		addItem(list, "mediapalyer", new Intent(MainActivity.this,
				MediaPlayerActivity.class));
		addItem(list, "bindserver", new Intent(MainActivity.this,
				MyBindServerActivity.class));
		addItem(list, "startserver", new Intent(MainActivity.this,
				MyStartServiceActivity.class));
		addItem(list, "广播", new Intent(MainActivity.this,
				MyReceiverActivity.class));
		addItem(list, "广播", new Intent(MainActivity.this,
				MyBroadcastActivity.class));
		addItem(list, "Notification", new Intent(MainActivity.this,
				NotificationActivity.class));
		addItem(list, "异步任务", new Intent(MainActivity.this,
				MyAsyncActivity.class));
		addItem(list, "异步下载", new Intent(MainActivity.this,
				AyncDownLoadActivity.class));
		addItem(list, "webView", new Intent(MainActivity.this, MyWebView.class));
		addItem(list, "HttpConnectActivity", new Intent(MainActivity.this,
				HttpConnectActivity.class));
		addItem(list, "BookListActivity", new Intent(MainActivity.this,
				BookListActivity.class));
		addItem(list, "FragmentActivity", new Intent(MainActivity.this,
				FragmentActivity.class));
		return list;
	}

	private void addItem(ArrayList<HashMap<String, Object>> list, String text,
						 Intent intent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", text);
		map.put("intent", intent);
		list.add(map);
	}

}