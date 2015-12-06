package com.android.page;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyPagerView extends ViewPager {

	public MyPagerView(Context context) {
		super(context);
	}

	public MyPagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void addView(ImageView img, int rsId) {
		img.setImageResource(rsId);
		this.addView(img);
	}

	public void addView(MyImageView img, Uri uri) {
		img.setImageURI(uri);
		this.addView(img);
	}

}
