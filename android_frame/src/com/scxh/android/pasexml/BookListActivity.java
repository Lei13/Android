package com.scxh.android.pasexml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scxh.android.frame.R;

public class BookListActivity extends Activity {
	ListView mList;
	BookListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.booklist_activity_layout);
		mList = (ListView) findViewById(R.id.list_book);
		adapter = new BookListAdapter(this);
		mList.setAdapter(adapter);
		getBookData();
	}

	private void getBookData() {
		try {
			InputStream is = this.getAssets().open("book.xml");
			XmlParseUtil utile = new XmlParseUtil();
			ArrayList<Book> bookData = utile.xmlPullParse(is);
			adapter.setData(bookData);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class BookListAdapter extends BaseAdapter {
		LayoutInflater inflater;
		ArrayList<Book> data = new ArrayList<Book>();

		public BookListAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void setData(ArrayList<Book> data) {
			this.data = data;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_booklist, null);
				holder = new ViewHolder();
				holder.athor = (TextView) convertView.findViewById(R.id.author_book);
				holder.name = (TextView) convertView.findViewById(R.id.name_book);
				holder.price = (TextView) convertView.findViewById(R.id.price_book);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Book book = (Book) getItem(position);
			holder.name.setText(book.getName());
			holder.price.setText("$ "+book.getPrice());
			holder.athor.setText(book.getAuthor());

			return convertView;
		}

		class ViewHolder {
			ImageView img;
			TextView name;
			TextView athor;
			TextView price;

		}

	}

}
