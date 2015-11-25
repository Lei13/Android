package com.scxh.meituan.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.scxh.meituan.adapter.GridAdapter;
import com.scxh.meituan.adapter.GridCategoryAdapter;
import com.scxh.meituan.adapter.ListViewAdapter;
import com.scxh.meituan.constance.FoodDataBase;

public class MainActivity extends Activity {
	ViewPager mCategoryPager;
	View firstPageGridLayout,secondPageGridLayout;//gridview布局
	GridView firstGrid,secondGrid;//grid控件
	int position = 0;//记录viewpager滑动小圆点位置
	GridView mShopGrid;
	Spinner mCity_Spinner;
	ListView mGuessYouLikeList;
	ArrayList<HashMap<String, Object>> mData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();//初始化view
		setCitySpinnerAdapter();//城市spinner
		setCategotyPagerAdapter();
		setCatergoryPagerListener();
		setCategoryFirstPageListener();
		setShopGridAdapter();
		setListViewAdapter();
	}
	
	private void initView(){
		LayoutInflater inflater = LayoutInflater.from(this);
		mCategoryPager = (ViewPager) findViewById(R.id.main_viewpager);
		firstPageGridLayout = inflater.inflate(R.layout.category_item_firstpage_grid, null);
		secondPageGridLayout = inflater.inflate(R.layout.category_item_second_grid, null);
		firstGrid = (GridView) firstPageGridLayout.findViewById(R.id.category_firstpage_grid);
		secondGrid = (GridView) secondPageGridLayout.findViewById(R.id.category_secondpage_grid);
		
		mCity_Spinner = (Spinner) findViewById(R.id.main_city_spinner);
		mShopGrid = (GridView) findViewById(R.id.main_deal_grid);
		mGuessYouLikeList = (ListView) findViewById(R.id.main_like_list);
	}
	
	@SuppressWarnings("deprecation")
	private  void setCatergoryPagerListener(){
		final ArrayList<View> dot = new ArrayList<View>();
		ImageView dotImg1 = (ImageView) findViewById(R.id.main_category_dot_first_img);
		ImageView dotImg2 = (ImageView) findViewById(R.id.main_category_dot_second_img);
		dot.add(dotImg1);
		dot.add(dotImg2);
		mCategoryPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				dot.get(position).setBackgroundResource(R.drawable.welcome_center_dot_default);
				dot.get(arg0).setBackgroundResource(R.drawable.welcome_center_dot_pressed);
				position = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
	}
	private void setCategoryFirstPageListener(){
		firstGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					startActivity(new Intent(MainActivity.this,FoodActivity.class));
					break;

				default:
					break;
				}
			}
		});
	}
	private void setCitySpinnerAdapter(){
		String [] city = {"成都","重庆","资阳","南充","眉山"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.main_city_spinner_item_text_laylout,city);
		mCity_Spinner.setAdapter(adapter);
		mCity_Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				TextView option = (TextView) view;
				option.setTextColor(Color.WHITE);
				option.setBackgroundResource(Color.TRANSPARENT);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}
	
	private void setCategotyPagerAdapter(){
		GridCategoryAdapter fisrtAdapter = new GridCategoryAdapter(this);
		fisrtAdapter.setData(getCategoryPageFirstData());
		firstGrid.setAdapter(fisrtAdapter);
		
		GridCategoryAdapter secondAdapter = new GridCategoryAdapter(this);
		secondAdapter.setData(getCategoryPageSecondData());
		secondGrid.setAdapter(secondAdapter);
		
		ArrayList<View> viewData = new ArrayList<View>();
		viewData.add(firstPageGridLayout);
		viewData.add(secondPageGridLayout);
		MyPagerAdapter pagerAdapter = new MyPagerAdapter();
		pagerAdapter.setData(viewData);
		mCategoryPager.setAdapter(pagerAdapter);
	}
	
	
	
	private void setShopGridAdapter(){
		GridAdapter adapter = new GridAdapter(this);
		adapter.setData(getShopData());
		mShopGrid.setAdapter(adapter);
	}
	private void setListViewAdapter(){
		ListViewAdapter adapter = new ListViewAdapter(this);
		adapter.setData(FoodDataBase.getData());
		mGuessYouLikeList.setAdapter(adapter);
	}
	/*
	 * 获得category数据源
	 */
	private ArrayList<HashMap<String, Object>> getCategoryPageFirstData(){
		 mData = new ArrayList<HashMap<String, Object>>();
		 HashMap<String,Object> item = new HashMap<String, Object>();
		 item.put("key", "美食");
		 item.put("img", R.drawable.ic_category_0);
		 mData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "电影");
		 item.put("img", R.drawable.ic_category_1);
		 mData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "酒店");
		 item.put("img", R.drawable.ic_category_2);
		 mData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "KTV");
		 item.put("img", R.drawable.ic_category_3);
		 mData.add(item);
		 
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "优惠买单");
		 item.put("img", R.drawable.ic_category_5);
		 mData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "周边游");
		 item.put("img", R.drawable.ic_category_12);
		 mData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "飞机票");
		 item.put("img", R.drawable.ic_category_13);
		 mData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "外卖");
		 item.put("img", R.drawable.ic_category_7);
		 mData.add(item);
		 
		return mData;
	}
	
	private ArrayList<HashMap<String, Object>> getCategoryPageSecondData(){
		ArrayList<HashMap<String, Object>>	SecondPageData = new ArrayList<HashMap<String, Object>>();
		 HashMap<String,Object> item = new HashMap<String, Object>();
		 item.put("key", "丽人");
		 item.put("img", R.drawable.ic_category_11);
		 SecondPageData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "休闲娱乐");
		 item.put("img", R.drawable.ic_category_10);
		 SecondPageData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "今日新单");
		 item.put("img", R.drawable.ic_category_4);
		 SecondPageData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "购物");
		 item.put("img", R.drawable.ic_category_14);
		 SecondPageData.add(item);
		 
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "生活服务");
		 item.put("img", R.drawable.ic_category_9);
		 SecondPageData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "旅游");
		 item.put("img", R.drawable.ic_category_13);
		 SecondPageData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "自助餐");
		 item.put("img", R.drawable.ic_category_7);
		 SecondPageData.add(item);
		 
		 item = new HashMap<String, Object>();
		 item.put("key", "更多分类");
		 item.put("img", R.drawable.ic_category_15);
		 SecondPageData.add(item);
		 
		return SecondPageData;
		
	}
	/*
	 * 获得名店抢购数据源
	 */
	private ArrayList<HashMap<String, Object>> getShopData(){
		ArrayList<HashMap<String, Object>> shopData = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("image", R.drawable.brand_1);
		map.put("name", "加太贺");
		map.put("discount", "49.0");
		map.put("allPrice", "79.9");
		shopData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("image", R.drawable.brand_2);
		map.put("name", "北海道");
		map.put("discount", "79.9");
		map.put("allPrice", "120.0");
		shopData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("image", R.drawable.brand_3);
		map.put("name", "釜山料理");
		map.put("discount", "49.5");
		map.put("allPrice", "69.9");
		shopData.add(map);
		return shopData;
	}
	
	
	
	public class MyPagerAdapter extends android.support.v4.view.PagerAdapter
	{
		ArrayList<View> viewData = new ArrayList<View>();

		private void setData(ArrayList<View> data){
			this.viewData = data;
		}
		
		
		@Override
		public int getCount() {
			return viewData.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v  = viewData.get(position);
			container.addView(v);
			
			return v;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View v = viewData.get(position);
			container.removeView(v);
		}
	}
	
}
