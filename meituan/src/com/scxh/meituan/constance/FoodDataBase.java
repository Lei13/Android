package com.scxh.meituan.constance;

import java.util.ArrayList;

import com.scxh.meituan.ui.R;

public class FoodDataBase {
	/*
	 * 得到数据源
	 */
	
	public static  ArrayList<ShopBean> getData(){
		ArrayList<ShopBean> listData = new ArrayList<ShopBean>();
		ShopBean shop = new ShopBean();
		
		
		shop.setImg(R.drawable.meituan_image6);
		shop.setTitle("h【合江亭】卡里奥西餐鸡尾餐吧");
		shop.setContent("2人套餐，提供免费WiFi，精致美味，幸福");
		shop.setPrice("129元");
		shop.setPriceOnsale("99元");
		shop.setScore("4.5分（880）");
		listData.add(shop);
		
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image3);
		shop.setTitle("【成都】加太贺烤肉");
		shop.setContent("单人自助，提供免费WiFi");
		shop.setPrice("70元");
		shop.setPriceOnsale("35元");
		shop.setScore("4.5分（880）");
		listData.add(shop);
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image4);
		shop.setTitle("w【武侯区】韩品轩海鲜自助烤肉");
		shop.setContent("预售海鲜烤肉单人，提供免费WiFi");
		shop.setPrice("9.9元");
		shop.setPriceOnsale("30元");
		shop.setScore("4.5分（880）");
		listData.add(shop);
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image1);
		shop.setTitle("b芭菲盛宴");
		shop.setContent("奥克斯广场。单人自助午餐，让人留恋  往返的美食");
		shop.setPrice("215元");
		shop.setPriceOnsale("125元");
		shop.setScore("4.5分（880）");
		listData.add(shop); 
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image3);
		shop.setTitle("l鲁西牛肉");
		shop.setContent("奥克斯广场。单人自助午餐，让人留恋  往返的美食");
		shop.setPrice(" 105");
		shop.setPriceOnsale("59元");
		shop.setScore("4.5分（880）");
		listData.add(shop); 
		
		
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image5);
		shop.setTitle("芭菲盛宴");
		shop.setContent("奥克斯广场。单人自助午餐，让人留恋  往返的美食");
		shop.setPrice("7.9元");
		shop.setPriceOnsale("20元");
		shop.setScore("4.5分（880）");
		listData.add(shop);
		return listData;
	}

}
