package com.scxh.meituan.constance;

import java.util.ArrayList;

import com.scxh.meituan.ui.R;

public class FoodDataBase {
	/*
	 * �õ�����Դ
	 */
	
	public static  ArrayList<ShopBean> getData(){
		ArrayList<ShopBean> listData = new ArrayList<ShopBean>();
		ShopBean shop = new ShopBean();
		
		
		shop.setImg(R.drawable.meituan_image6);
		shop.setTitle("h���Ͻ�ͤ����������ͼ�β�Ͱ�");
		shop.setContent("2���ײͣ��ṩ���WiFi��������ζ���Ҹ�");
		shop.setPrice("129Ԫ");
		shop.setPriceOnsale("99Ԫ");
		shop.setScore("4.5�֣�880��");
		listData.add(shop);
		
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image3);
		shop.setTitle("���ɶ�����̫�ؿ���");
		shop.setContent("�����������ṩ���WiFi");
		shop.setPrice("70Ԫ");
		shop.setPriceOnsale("35Ԫ");
		shop.setScore("4.5�֣�880��");
		listData.add(shop);
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image4);
		shop.setTitle("w�����������Ʒ��������������");
		shop.setContent("Ԥ�ۺ��ʿ��ⵥ�ˣ��ṩ���WiFi");
		shop.setPrice("9.9Ԫ");
		shop.setPriceOnsale("30Ԫ");
		shop.setScore("4.5�֣�880��");
		listData.add(shop);
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image1);
		shop.setTitle("b�ŷ�ʢ��");
		shop.setContent("�¿�˹�㳡������������ͣ���������  ��������ʳ");
		shop.setPrice("215Ԫ");
		shop.setPriceOnsale("125Ԫ");
		shop.setScore("4.5�֣�880��");
		listData.add(shop); 
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image3);
		shop.setTitle("l³��ţ��");
		shop.setContent("�¿�˹�㳡������������ͣ���������  ��������ʳ");
		shop.setPrice(" 105");
		shop.setPriceOnsale("59Ԫ");
		shop.setScore("4.5�֣�880��");
		listData.add(shop); 
		
		
		
		shop = new ShopBean();
		shop.setImg(R.drawable.meituan_image5);
		shop.setTitle("�ŷ�ʢ��");
		shop.setContent("�¿�˹�㳡������������ͣ���������  ��������ʳ");
		shop.setPrice("7.9Ԫ");
		shop.setPriceOnsale("20Ԫ");
		shop.setScore("4.5�֣�880��");
		listData.add(shop);
		return listData;
	}

}
