package com.scxh.meituan.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.meituan.ui.R;

/*
 * 自定义Adapter-GridView-----category
 * mainActivity
 */
public class GridCategoryAdapter extends BaseAdapter{
	//数据源，定义时就进行初始化操作，避免报空指针
	private ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	private LayoutInflater inflater;
	
	/*
	 *构造方法，将inflater进行初始化
	 */
	public GridCategoryAdapter(Context context){
		inflater = LayoutInflater.from(context);
	}
	/*
	 * 为他设置数据源，避免应网络延迟加载数据
	 */
	public void setData(ArrayList<HashMap<String,Object>> data){
		this.data = data;
		//数据更新
		notifyDataSetChanged();
	}
	/*
	 * data 里存储对象个数
	 */
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
		GridItem wedget;
		//优化，避免资源重复生成
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.main_gridview_item_layout, null);
			wedget = new GridItem();
			wedget.img = (ImageView) convertView.findViewById(R.id.main_grid_item_img);
			wedget.txt = (TextView) convertView.findViewById(R.id.main_grid_item_txt);
			convertView.setTag(wedget);
		}else {
			wedget = (GridItem) convertView.getTag();
		}
		//得到数据源里的对象HashMap对象
		HashMap<String,Object> map = data.get(position);
		//将HashMap里值根据标签赋给相应的控件View
		wedget.txt.setText( map.get("key").toString());
		wedget.img.setImageResource((Integer) map.get("img"));
		//返回布局View
		return convertView;
	}
	class GridItem{
		ImageView img;
		TextView txt;
	}
	
	
		
	
}
