package com.prictice.filemanager;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener {
	ListView mList;
	TextView mPathTxt;
	TextView mFirstPathTxt;// 动态添加路径的第一条root目录
	TextView mPath;
	MyAdapter adapter;
	LinearLayout mPathLiear;
	File rootFile = Environment.getExternalStorageDirectory();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setAdapter();
		mList.setOnItemClickListener(this);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void initView() {
		mList = (ListView) findViewById(R.id.file_list);
		mPathTxt = (TextView) findViewById(R.id.file_path_txt);
		mPathLiear = (LinearLayout) findViewById(R.id.path_liear);
		mFirstPathTxt = (TextView) findViewById(R.id.file_first_path_txt);
		mFirstPathTxt.setText(rootFile.getAbsolutePath().substring(1) + "/");
	}

	private void setAdapter() {
		MyAdapter adapter = new MyAdapter(this);
		adapter.setData(scanFile(rootFile));
		mList.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		adapter = (MyAdapter) parent.getAdapter();
		File file = (File) adapter.getItem(position);

		if (file.isDirectory()) {
			getRelativePath(file);
			mPathTxt.setText(file.getAbsolutePath());
			adapter.setData(scanFile(file));
		} else if (file.isFile()) {
			fileOpenType(file);
		}

	}

	/*
	 * 动态添加textview 记录路径
	 */
	private void getRelativePath(File file) {
		mPath = new TextView(this);
		mPath.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mPath.setText(file.getName() + "/");
		mPath.setPadding(1, 10, 0, 10);
		mPath.setClickable(true);
		mPath.setGravity(Gravity.CENTER);
		mPath.setMaxEms(4);
		mPath.setEllipsize(TruncateAt.MIDDLE);
		mPath.setSingleLine();
		mPathLiear.addView(mPath);
		setListener();
	}

	/*
	 * 监听点击动态路径
	 */
	private void setListener() {
		mPath.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String currentPath = mPathTxt.getText().toString();// 当前路径
				TextView click = (TextView) v;
				String clickStr = click.getText().toString(); // 动态的当前文件名+“/”
				String repeat = clickStr.substring(0, clickStr.length() - 1);// 动态的当前文件名
				String path = currentPath.substring(0,
						currentPath.indexOf(repeat) + repeat.length());// 需要跳转的路径
				adapter.setData(scanFile(new File(path)));
				String remove = currentPath.substring(currentPath
						.indexOf(repeat) - 1 + repeat.length());

				String[] countStr = remove.split("/");
				int count = countStr.length;
				mPathLiear.removeViews(mPathLiear.getChildCount() - count + 1,
						count - 1);

				mPathTxt.setText(path + "/");
			}
		});

		mFirstPathTxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.setData(scanFile(rootFile));
				mPathLiear.removeViews(1, mPathLiear.getChildCount() - 1);

			}
		});
	}

	/*
	 * 不同的文件调用不同方式打开；eg JPG/txt,mp3
	 */
	private void fileOpenType(File file) {
		if (file.getName().endsWith("png") || file.getName().endsWith("jpg")) {
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri = Uri.fromFile(file);
			intent.setDataAndType(uri, "image/*");
			startActivity(intent);
		} else if (file.getName().endsWith("mp3")) {
			/*
			 * Intent intent = new Intent();
			 * intent.setAction(Intent.ACTION_VIEW); intent.setDataAndType(
			 * Uri.parse("file:// " + file.getAbsolutePath() + ""), "audio/*");
			 * startActivity(intent);
			 */
			MediaPlayer player = MediaPlayer.create(this,
					Uri.parse("file:// " + file.getAbsolutePath() + ""));
			player.start();
		} else if (file.getName().endsWith("txt")) {
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.setDataAndType(
					Uri.parse("file:// " + file.getAbsolutePath() + ""),
					"text/*");
			startActivity(intent);
		}
	}

	/*
	 * 遍历文件夹，获得他的子文件或子文件夹
	 */
	public ArrayList<File> scanFile(File root) {
		ArrayList<File> mData = new ArrayList<File>();
		if (root != null) {
			mPathTxt.setText(root.getAbsolutePath());
			File[] list = root.listFiles();
			if (list != null) {
				for (int i = 0; i < list.length; i++) {
					File item = list[i];
					mData.add(item);
				}
			}
		} else {
			mData.add(root);
		}
		return mData;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			String current = mPathTxt.getText().toString();

			if (current.equals(rootFile.getAbsolutePath())) {
				finish();
			} else {
				String parent = new File(current).getParent();
				adapter.setData(scanFile(new File(parent)));
				mPathLiear.removeViewAt(mPathLiear.getChildCount() - 1);
			}

		}

		return super.onOptionsItemSelected(item);
	}

	// *****************************************************
	public class MyAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ArrayList<File> data = new ArrayList<File>();

		public MyAdapter(Context context) {
			inflater = LayoutInflater.from(context);

		}

		public void setData(ArrayList<File> data) {
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
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.file_item_layout, null);
				holder = new ViewHolder();
				holder.icon = (ImageView) convertView
						.findViewById(R.id.file_icon_img);
				holder.name = (TextView) convertView
						.findViewById(R.id.file_name_txt);
				holder.content = (TextView) convertView
						.findViewById(R.id.file_content_txt);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			File root = data.get(position);
			if (root.isDirectory()) {
				holder.name.setText(root.getName());
				holder.icon.setImageResource(R.drawable.filesystem_icon_folder);
				holder.content
						.setBackgroundResource(R.drawable.file_folder_indecator);
				holder.content.setText("");
			} else {
				holder.name.setText(root.getName());
				holder.content.setText(getSize(root.length()));
				holder.content.setBackgroundResource(0);
				if (root.getPath().endsWith("txt")) {
					holder.icon
							.setImageResource(R.drawable.filesystem_icon_txt);
				} else if (root.getPath().endsWith("png")
						|| root.getPath().endsWith("jpg")) {
					holder.icon
							.setImageResource(R.drawable.filesystem_icon_photo);
				} else if (root.getPath().endsWith("mp3")) {
					holder.icon
							.setImageResource(R.drawable.filesystem_icon_music);
				} else {
					holder.icon.setImageResource(R.drawable.ic_launcher);
				}
			}
			return convertView;
		}

		private String getSize(long size) {
			NumberFormat num = NumberFormat.getInstance();
			num.setMaximumFractionDigits(2);
			String result = "";
			if (size == 0) {
				return size + " B";
			}
			if (size < 1024) {
				result = num.format((double) size) + " B";
			}
			if (size < 1048576) {
				result = num.format((double) size / 1024) + " KB";
			}
			if (size < 1073741824) {
				result = num.format((double) size / 1048576) + " MB";
			} else {
				result = num.format((double) size / 1073741824) + " GB";
			}
			return result;
		}

		class ViewHolder {
			TextView name;
			TextView content;
			ImageView icon;

		}
	}

}
