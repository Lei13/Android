package com.scxh.android.view;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class LyricView extends TextView {
	private Paint mPaint;
	private float mX;
	private static Lyric mLyric;

	private Paint mPathPaint;
	public String test = "test";
	public int index = 0;
	private List<Sentence> list;

	public float mTouchHistoryY;

	private int mY;
	private long currentDunringTime; // ��ǰ�и�ʳ�����ʱ�䣬�ø�ʱ����sleep
	private float middleY;// y���м�
	private static final int DY = 50; // ÿһ�еļ��
	public LyricView(Context context) {
		super(context);
		init();
	}

	public LyricView(Context context, AttributeSet attr) {
		super(context, attr);
		init();
	}

	public LyricView(Context context, AttributeSet attr, int i) {
		super(context, attr, i);
		init();
	}

	private void init() {
		setFocusable(true);
		PlayListItem pli = new PlayListItem("������","/mnt/sdcard/music/jinsha.mp3", 0L, true);
		mLyric = new Lyric(new File("/mnt/sdcard/music/jinsha.lrc"), pli);

		list = mLyric.list;
		// �Ǹ�������
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setTextSize(22);
		mPaint.setColor(Color.DKGRAY);
		mPaint.setTypeface(Typeface.SERIF);

		// �������� ��ǰ���
		mPathPaint = new Paint();
		mPathPaint.setAntiAlias(true);
		mPathPaint.setColor(Color.WHITE);
		mPathPaint.setTextSize(22);
		mPathPaint.setTypeface(Typeface.SANS_SERIF);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(0xEFeffff);

		Paint p = mPaint;
		Paint p2 = mPathPaint;

		p.setTextAlign(Paint.Align.CENTER);

		if (index == -1)
			return;
		p2.setTextAlign(Paint.Align.CENTER);
		// �Ȼ���ǰ�У�֮���ٻ�����ǰ��ͺ��棬�����ͱ��ֵ�ǰ�����м��λ��
		canvas.drawText(list.get(index).getContent(), mX, middleY, p2);

		float tempY = middleY;
		// ��������֮ǰ�ľ���
		for (int i = index - 1; i >= 0; i--) {
			// Sentence sen = list.get(i);
			// ��������
			tempY = tempY - DY;
			if (tempY < 0) {
				break;
			}
			canvas.drawText(list.get(i).getContent(), mX, tempY, p);
			// canvas.translate(0, DY);
		}

		tempY = middleY;
		// ��������֮��ľ���
		for (int i = index + 1; i < list.size(); i++) {
			// ��������
			tempY = tempY + DY;
			if (tempY > mY) {
				break;
			}
			canvas.drawText(list.get(i).getContent(), mX, tempY, p);
			// canvas.translate(0, DY);
		}

	}

	protected void onSizeChanged(int w, int h, int ow, int oh) {
		super.onSizeChanged(w, h, ow, oh);
		mX = w * 0.5f; // remember the center of the screen
		mY = h;
		middleY = h * 0.5f;
	}

	//
	/**
	 * @param time
	 *            ��ǰ��ʵ�ʱ����
	 * 
	 * @return currentDunringTime ���ֻ���ʱ��
	 */
	public long updateIndex(long time) {
		// ������
		index = mLyric.getNowSentenceIndex(time);
		if (index == -1)
			return -1;
		Sentence sen = list.get(index);
		// ���ظ�ʳ�����ʱ�䣬�����ʱ����sleep
		return currentDunringTime = sen.getDuring();
	}
}