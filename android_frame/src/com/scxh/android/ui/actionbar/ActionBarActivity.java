package com.scxh.android.ui.actionbar;

import com.scxh.android.frame.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ActionBarActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionbar_layout);
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_bar_menu, menu);
		
		
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_add:
			Toast.makeText(this, "add scussess", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_delete:
			Toast.makeText(this, "delete scussess", Toast.LENGTH_SHORT).show();
			break;
		case android.R.id.home:
			finish();
			break;
		
		}
		return super.onOptionsItemSelected(item);
	}
}
