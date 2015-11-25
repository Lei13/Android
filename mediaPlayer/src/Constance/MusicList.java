package Constance;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;

public class MusicList {
	public static ArrayList<MusicBean> mListData = scanFile(Environment
			.getExternalStorageDirectory());

	private static ArrayList<MusicBean> scanFile(File file) {
		File[] listFile = file.listFiles();
		mListData = new ArrayList<MusicBean>();
		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				File item = listFile[i];
				if (item.isDirectory()) {
					scanFile(item);
				} else {
					if (item.getName().endsWith("mp3")) {

						MusicBean music = new MusicBean();
						String name = item.getName();
						music.setName(name.substring(0,
								name.lastIndexOf(".mp3")));
						music.setComposer("unknown");
						music.setPath(item.getAbsolutePath());
						mListData.add(music);
					}
				}
			}
		}
		return mListData;

	}

}
