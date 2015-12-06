package com.android.page;

public class PageUtil {
	private int totalData;
	private int currentPage;
	private int data_per_page;
	private int pageCount;
	
	public PageUtil(int totalData, int data_per_page, int currentPage){
		this.totalData = totalData;
		this.data_per_page = data_per_page;
		pageCount = getTotalPage();
		this.currentPage = Math.min(currentPage, pageCount);
		
	}
	/*ÔOÖÃ¿‚í“”µ*/
	public int getTotalPage(){
		if (totalData%data_per_page!=0) {
			pageCount = totalData/data_per_page+1;
		}else {
			pageCount = totalData/data_per_page;
		}
		return pageCount;
	}

	public  int getStartIndex(){
		return (currentPage-1)*data_per_page;
	}
	
	public int getEndIndex(){
		return Math.min(totalData, currentPage*data_per_page);
	}
	
}
