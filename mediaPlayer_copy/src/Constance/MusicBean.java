package Constance;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicBean implements Parcelable {
	private String name;
	private String composer;
	private String path;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(composer);
		dest.writeString(path);
	}

	public static final Parcelable.Creator<MusicBean> CREATOR = new Creator<MusicBean>() {

		@Override
		public MusicBean createFromParcel(Parcel source) {
			MusicBean musicList = new MusicBean();
			musicList.setName(source.readString());
			musicList.setComposer(source.readString());
			musicList.setPath(source.readString());
			return musicList;
		}

		@Override
		public MusicBean[] newArray(int size) {

			return new MusicBean[size];
		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
