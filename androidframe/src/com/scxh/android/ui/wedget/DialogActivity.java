package com.scxh.android.ui.wedget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.scxh.android.frame.R;

public class DialogActivity extends Activity {
	private Button mProgressBtn, mDateBtn, mAlertBtn, mAlertSingleBtn,
			mAlertMultiBtn;
	private Button mAlertBtn1, mAlertBtn2, mToastBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_layout);
		initView();
		mProgressBtn.setOnClickListener(onClick);
		mDateBtn.setOnClickListener(onClick);
		mAlertBtn.setOnClickListener(onClick);
		mAlertMultiBtn.setOnClickListener(onClick);
		mAlertSingleBtn.setOnClickListener(onClick);
		mAlertBtn1.setOnClickListener(onClick);
		mAlertBtn2.setOnClickListener(onClick);
		mToastBtn.setOnClickListener(onClick);
	}

	/*
	 * �ڶ��ַ�ʽ����activity��ʵ�ֹ���dialog oncratedialog + ������ť ʵ��
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		return new DefinedAlertDialog(this);
	}

	/*
	 * public void testOnCreateDialog(View v) { showDialog(1); }
	 */

	View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog_alert_btn:
				setAlterListenerBtn();
				break;
			case R.id.dialog_datepicker_btn:
				setDataListenerBtn();
				break;
			case R.id.dialog_progress_btn:
				setProgressListenerBtn();
				break;

			case R.id.dialog_alertMultiOpt_btn:
				setAlterMultiOptListenerBtn();
				break;
			case R.id.dialog_alertSingleOpt_btn:
				setAlterSingleOptListenerBtn();
				break;
			case R.id.dialog_alert_d_btn:
				setAlterDListener();
				break;
			case R.id.dialog_alertdefined_btn:
				/*DefinedAlertDialog dia = new DefinedAlertDialog(
						DialogActivity.this);
				dia.show();*/
				 new DefinedAlertDialog(DialogActivity.this).show();
				break;
			case R.id.dialog_toast_btn:
				Toast toast = new Toast(DialogActivity.this);
				toast.setView(LayoutInflater.from(DialogActivity.this).inflate(
						R.layout.dialog_alert_d_layout, null));
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;
			}

		}
	};

	/*
	 * ����alterDialog ��ʾ�Ի���
	 */
	private void setAlterListenerBtn() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("alert:����");
		builder.setMessage("are you confirm to quite");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setPositiveButton("confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, " " + which,
								Toast.LENGTH_SHORT).show();

					}
				});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "" + which,
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.setNeutralButton("contitue",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "" + which,
								Toast.LENGTH_SHORT).show();
					}
				});

		builder.create().show();
	}

	/*
	 * �������ڶԻ���
	 */
	private void setDataListenerBtn() {
		DatePickerDialog dataDialog = new DatePickerDialog(this,
				new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Toast.makeText(
								DialogActivity.this,
								"current time: " + year + " " + monthOfYear
										+ " " + dayOfMonth, Toast.LENGTH_SHORT)
								.show();
					}
				}, 2015, 10, 15);

		dataDialog.show();
	}

	/*
	 * ���ý������Ի���
	 */
	private void setProgressListenerBtn() {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("refresh.....");
		dialog.setMessage("����");
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setProgressDrawable(getResources().getDrawable(
				R.drawable.line_hori_progressbar_layer_list));
		dialog.setIndeterminate(false);

		dialog.setButton(ProgressDialog.BUTTON_POSITIVE, "confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "" + which,
								Toast.LENGTH_SHORT).show();
					}
				});
		dialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "" + which,
								Toast.LENGTH_SHORT).show();
					}
				});

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <= 100; i++) {
					dialog.setProgress(i);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		dialog.show();
	}

	/*
	 * ���õ�ѡalert�Ի���
	 */
	private void setAlterSingleOptListenerBtn() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("pls select your sex");
		builder.setSingleChoiceItems(new String[] { "man", "woman" }, 1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this,
								which + " " + dialog.toString(),
								Toast.LENGTH_SHORT).show();
					}
				});

		builder.setPositiveButton("confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, " " + which,
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "" + which,
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.create().show();
	}

	/*
	 * ���ö�ѡalert�Ի���
	 */
	private void setAlterMultiOptListenerBtn() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("pls select below information");
		builder.setMultiChoiceItems(
				new String[] { "socer", "footbal", "clim" }, new boolean[] {
						false, false, false },
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						Toast.makeText(DialogActivity.this,
								which + " " + dialog.toString(),
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.setPositiveButton("confirm",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, " " + which,
								Toast.LENGTH_SHORT).show();
					}
				});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(DialogActivity.this, "" + which,
								Toast.LENGTH_SHORT).show();
					}
				});

		builder.create().show();

	}

	/*
	 * �Զ���Ի����һ����ʽ���ֲڰ棩
	 */
	private void setAlterDListener() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_alert_d_layout, null);
		builder.setView(view);
		final AlertDialog dialog = builder.create();
		Button button = (Button) view.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(DialogActivity.this, "cancel",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	/*
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		mProgressBtn = (Button) findViewById(R.id.dialog_progress_btn);
		mDateBtn = (Button) findViewById(R.id.dialog_datepicker_btn);
		mAlertBtn = (Button) findViewById(R.id.dialog_alert_btn);
		mAlertMultiBtn = (Button) findViewById(R.id.dialog_alertMultiOpt_btn);
		mAlertSingleBtn = (Button) findViewById(R.id.dialog_alertSingleOpt_btn);
		mAlertBtn1 = (Button) findViewById(R.id.dialog_alert_d_btn);
		mAlertBtn2 = (Button) findViewById(R.id.dialog_alertdefined_btn);
		mToastBtn = (Button) findViewById(R.id.dialog_toast_btn);
	}

}
