package com.scxh.meituan.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scxh.meituan.constance.ShopBean;
import com.scxh.meituan.ui.R;

public class ListViewAdapter  extends BaseAdapter{
	LayoutInflater inflater;
	List<ShopBean> listData;
	Context context;
	public ListViewAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	public void setData(List<ShopBean> listData){
		this.listData = listData;
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
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		Wedget wedget;
		if (convertView == null) {
			v = inflater.inflate(R.layout.mybaseadapter_layout_1, null);
			wedget = new Wedget();
			wedget.img = (ImageView) v.findViewById(R.id.image_meituan);
			wedget.title = (TextView) v.findViewById(R.id.title_meituan);
			wedget.content = (TextView) v.findViewById(R.id.content_meituan);
			wedget.price = (TextView) v.findViewById(R.id.price_all_meituan);
			wedget.price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//设置中划线
			wedget.priceOnsale = (TextView) v.findViewById(R.id.price_onsale_meituan);
			wedget.score = (TextView) v.findViewById(R.id.score_meituan);
			
			v.setTag(wedget);
		}else {
			v = convertView;
			wedget = (Wedget) v.getTag();
		}
		
		ShopBean shop = listData.get(position);
		int image = shop.getImg();
		String title = shop.getTitle();
		String content = shop.getContent();
		String price = shop.getPrice();
		String priceOnsale = shop.getPriceOnsale();
		String score = shop.getScore();
		
			wedget.img.setBackgroundResource(image);
			wedget.title.setText(title) ;
			wedget.content.setText(content) ;
			SpannableString discountPrice = new SpannableString(priceOnsale);
			discountPrice.setSpan(new AbsoluteSizeSpan(20,true), 0, priceOnsale.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			discountPrice.setSpan(new AbsoluteSizeSpan(13,true), priceOnsale.length()-1, priceOnsale.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			discountPrice.setSpan(new ForegroundColorSpan(context.getResources().getColor(android.R.color.holo_green_light)), 0, priceOnsale.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			wedget.priceOnsale.setText(discountPrice);
			
			wedget.score.setText(score);
		
			if (price.endsWith("元")) {
				wedget.price.setText(price);
			}else {
				wedget.price.setBackgroundResource(R.drawable.movie_discount);}
				wedget.price.setGravity(Gravity.CENTER_HORIZONTAL);
		return v;
	}
	class Wedget{
		ImageView img;
		TextView title;
		TextView content;
		TextView price;
		TextView priceOnsale;
		TextView score;
	}
}