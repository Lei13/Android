package com.scxh.android.lunchmode;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private String name;
	private String password;
	private int age;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(password);
		dest.writeInt(age);
	}

	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}

		@Override
		public User createFromParcel(Parcel source) {
			User user = new User();
			String name = source.readString();
			String password = source.readString();
			int age = source.readInt();
			user.setName(name);
			user.setPassword(password);
			user.setAge(age);

			return user;
		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
