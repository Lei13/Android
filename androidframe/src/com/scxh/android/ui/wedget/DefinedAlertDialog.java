package com.scxh.android.ui.wedget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.scxh.android.frame.R;

public class DefinedAlertDialog extends AlertDialog {
	private Button confirmBtn, cancelBtn;
	Context context;

	protected DefinedAlertDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_alert_d_layout);
		initButton(); //³õÊ¼»¯View
		setListener(); //¼Ó¼àÌý
	}

	private void initButton() {
		confirmBtn = (Button) findViewById(R.id.button2);
		cancelBtn = (Button) findViewById(R.id.button1);
	}

	private void setListener() {
		confirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
}