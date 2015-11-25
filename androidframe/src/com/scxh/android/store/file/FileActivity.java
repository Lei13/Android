package com.scxh.android.store.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.scxh.android.frame.R;

public class FileActivity extends Activity {
	ImageView mShowImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_write_layout);
		mShowImg = (ImageView) findViewById(R.id.file_show_img);
	}

	public void setBtnClickListener(View view) {
		switch (view.getId()) {
		case R.id.internal_write_btn://写入内部存储区域
			try {
				FileOutputStream os = openFileOutput("picture.png",
						MODE_PRIVATE);
				Bitmap bit = BitmapFactory.decodeResource(getResources(),
						R.drawable.meituan_image2);
				bit.compress(Bitmap.CompressFormat.PNG, 100, os);
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		case R.id.internal_show_btn:
			if (isExternalExit()) {
				File file = new File(getFilesDir(), "picture.png");
				Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
				mShowImg.setImageBitmap(bit);
			}
			break;
		case R.id.external_public_write_btn:

			if (isExternalExit() == true) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.meituan_image1);
				File file = new File(
						Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"picture1");
				FileOutputStream os;
				try {
					os = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			break;
		case R.id.external_public_show_btn:
			File file = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"picture1");
			Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
			mShowImg.setImageBitmap(bm);

			break;
		case R.id.external_private_write_btn:

			if (isExternalExit() == true) {
				File file1 = new File(
						getExternalFilesDir(Environment.DIRECTORY_PICTURES),
						"private_pic.png");
				try {
					FileOutputStream os = new FileOutputStream(file1);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.meituan_image4);
					bit.compress(CompressFormat.PNG, 100, os);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case R.id.external_private_show_btn:

			if (isExternalExit()) {
				File fil = new File(
						getExternalFilesDir(Environment.DIRECTORY_PICTURES),
						"private_pic.png");
				try {
					FileInputStream is = new FileInputStream(fil);
					Bitmap bmp = BitmapFactory.decodeStream(is);
					mShowImg.setImageBitmap(bmp);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
			break;
		}
	}

	private boolean isExternalExit() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}
}
