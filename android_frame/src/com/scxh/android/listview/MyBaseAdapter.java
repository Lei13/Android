package com.scxh.android.listview;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.scxh.android.frame.R;

public class MyBaseAdapter extends BaseAdapter {
	private ArrayList<User> list = new ArrayList<User>();// 初始化目的避免网络延迟造成的空指针
	private LayoutInflater inflater;

	public MyBaseAdapter(Context context) {
		inflater = LayoutInflater.from(context);
	}

	public void setData(ArrayList<User> list) {// 设置数据源
		this.list = list;
		notifyDataSetChanged();// 数据更新
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
		ItemWidget widget;// 内部类，里面是自定义布局的控件

		if (convertView == null) {// 一优化，避免重复加载，浪费资源
			convertView = inflater.inflate(R.layout.simpleadapter_item_layout,
					null);
			widget = new ItemWidget();
			widget.icon = (ImageView) convertView
					.findViewById(R.id.simpleadapter_item_img);
			widget.title = (TextView) convertView
					.findViewById(R.id.simpleadapter_item_title);
			widget.content = (TextView) convertView
					.findViewById(R.id.simpleadapter_item_content);
			convertView.setTag(widget);

		} else {
			widget = (ItemWidget) convertView.getTag();
		}

		//将User里的值取出来
		User user = (User) getItem(position);
		int id = user.getmId();
		String userName = user.getmUserName();
		String introduce = user.getmIntroduce();
		//赋给控件
		widget.icon.setImageResource(id);
		widget.title.setText(userName);
		widget.content.setText(introduce);

		return convertView;
	}

	public class ItemWidget {
		ImageView icon;
		TextView title;
		TextView content;
	}

}
