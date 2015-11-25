package com.scxh.android.db;

import java.io.File;

import Constance.DB_Constance;
import Constance.MusicInfoBean;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.scxh.android.music.R;

public class TestProvider extends AndroidTestCase {
	ContentResolver resolver;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		resolver = getContext().getContentResolver();
	}

	public void testProvider() {
		resolver = getContext().getContentResolver();

	}

	public void scanFile(File file) {
		File[] listFile = file.listFiles();

		if (listFile != null) {
			for (int i = 0; i < listFile.length; i++) {
				File item = listFile[i];
				if (item.isDirectory()) {
					scanFile(item);
				} else {
					if (item.getName().endsWith("mp3")) {
						MusicInfoBean music = new MusicInfoBean();
						String name = item.getName();
						music.setMusicName(name.substring(0,
								name.lastIndexOf(".mp3")));
						music.setMusicArtist("unknown");
						music.setMusicPath(item.getAbsolutePath());
						music.setMusicImage(R.drawable.pc1);
						
						Uri uri = Uri.withAppendedPath(Provider.URI_INFO, DB_Constance.Table_musicInfo.TABLE_NAME);
						new InfoHelper().insert(uri, music);
					}
				}
			}
		}

	}

	class InfoHelper {

		public void insert(Uri url,MusicInfoBean music){
			ContentValues values = new ContentValues();
			values.put(DB_Constance.Table_musicInfo.COLUMN_NAME, music.getMusicName());
			values.put(DB_Constance.Table_musicInfo.COLUMN_PATH, music.getMusicPath());
			values.put(DB_Constance.Table_musicInfo.COLUMN_COMPOSER,music.getMusicArtist());
			values.put(DB_Constance.Table_musicInfo.COLUMN_IMG, music.getMusicImage());
			values.put(DB_Constance.Table_musicInfo.COLUMN_LYRIC, music.getNusicLyric());
			resolver.insert(url, values);
		}
		
		public void query(Uri url,MusicInfoBean music){}
		
	}
}
