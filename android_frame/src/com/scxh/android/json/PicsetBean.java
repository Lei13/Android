package com.scxh.android.json;

import java.util.ArrayList;

public class PicsetBean {
	private String albumsName;
	private ArrayList<PicBean> picUrlSet;
	
	public String getAlbumsName() {
		return albumsName;
	}
	public void setAlbumsName(String albumsName) {
		this.albumsName = albumsName;
	}
	public ArrayList<PicBean> getPicUrlSet() {
		return picUrlSet;
	}
	public void setPicUrlSet(ArrayList<PicBean> picUrlSet) {
		this.picUrlSet = picUrlSet;
	}
	

}
