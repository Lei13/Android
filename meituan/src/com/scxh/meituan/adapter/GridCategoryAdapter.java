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
 * �Զ���Adapter-GridView-----category
 * mainActivity
 */
public class GridCategoryAdapter extends BaseAdapter{
	//����Դ������ʱ�ͽ��г�ʼ�����������ⱨ��ָ��
	private ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	private LayoutInflater inflater;
	
	/*
	 *���췽������inflater���г�ʼ��
	 */
	public GridCategoryAdapter(Context context){
		inflater = LayoutInflater.from(context);
	}
	/*
	 * Ϊ����������Դ������Ӧ�����ӳټ�������
	 */
	public void setData(ArrayList<HashMap<String,Object>> data){
		this.data = data;
		//���ݸ���
		notifyDataSetChanged();
	}
	/*
	 * data ��洢�������
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
		//�Ż���������Դ�ظ�����
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.main_gridview_item_layout, null);
			wedget = new GridItem();
			wedget.img = (ImageView) convertView.findViewById(R.id.main_grid_item_img);
			wedget.txt = (TextView) convertView.findViewById(R.id.main_grid_item_txt);
			convertView.setTag(wedget);
		}else {
			wedget = (GridItem) convertView.getTag();
		}
		//�õ�����Դ��Ķ���HashMap����
		HashMap<String,Object> map = data.get(position);
		//��HashMap��ֵ���ݱ�ǩ������Ӧ�Ŀؼ�View
		wedget.txt.setText( map.get("key").toString());
		wedget.img.setImageResource((Integer) map.get("img"));
		//���ز���View
		return convertView;
	}
	class GridItem{
		ImageView img;
		TextView txt;
	}
	
	
		
	
}
