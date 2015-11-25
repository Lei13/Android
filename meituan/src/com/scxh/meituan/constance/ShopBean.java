package com.scxh.meituan.constance;

public class ShopBean {
	private String title;
	private String content;
	private String priceOnsale;
	private String price;
	private String score;
	private int img;
	
	public ShopBean(){}
	public ShopBean(int img,String title,String content,String priceOnsale,String price,String score){
		
		this.img = img;
		this.title = title;
		this.content =content;
		this.price = price;
		this.priceOnsale = priceOnsale;
		this.score = score;
	}
	
	
	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPriceOnsale() {
		return priceOnsale;
	}

	public void setPriceOnsale(String priceOnsale) {
		this.priceOnsale = priceOnsale;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
