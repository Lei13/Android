package com.scxh.android.listview;

public class User {
	private String mUserName;
	private int mId;
	private String mIntroduce;
	
	public int getmId() {
		return mId;
	}


	public void setmId(int mId) {
		this.mId = mId;
	}


	public String getmIntroduce() {
		return mIntroduce;
	}


	public void setmIntroduce(String mIntroduce) {
		this.mIntroduce = mIntroduce;
	}


	public User(String mUserName,int id,String mIntroduce){
		this.mUserName = mUserName;
		this.mId = id;
		this.mIntroduce = mIntroduce;
	}
	
	
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	
	
	

}
