package com.scxh.android.pasexml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.test.AndroidTestCase;
import android.util.Log;
import android.util.Xml;

public class XMLpaseTest extends AndroidTestCase {
	
	
	public void testXmlUtils() throws IOException{
		XmlParseUtil util = new XmlParseUtil();
		ArrayList<Book> books = util.xmlPullParse(getContext().getAssets().open("book.xml"));
		for (Book book : books) {
			Log.v("tag", "id: "+book.getId()+"  name: "+book.getName()+"  price:  "+book.getPrice());
		}
		
		Log.v("TAG", ""+util.xmlSerializer(books));
	}

	public void paseXml() throws IOException, XmlPullParserException {
		InputStream is = getContext().getAssets().open("book.xml");
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "UTF-8");

		ArrayList<Book> books = null;
		Book book = null;
		int type = parser.getEventType();

		while (XmlPullParser.END_DOCUMENT != type) {
			switch (type) {
			case XmlPullParser.START_DOCUMENT:
				books = new ArrayList<Book>();
				break;

			case XmlPullParser.START_TAG:
				if (parser.getName().equals("book")) {
					book = new Book();
				} else if (parser.getName().equals("id")) {
					type = parser.next();
					book.setId(Integer.parseInt(parser.getText()));

				} else if (parser.getName().equals("name")) {
					type = parser.next();
					book.setName(parser.getText());

				} else if (parser.getName().equals("price")) {
					type = parser.next();
					book.setPrice(Double.parseDouble(parser.getText()));
				}
				break;
			case XmlPullParser.END_TAG:
				if (parser.getName().equals("book")) {
					books.add(book);
				}
				break;
			}
			type = parser.next();
		}
		for (Book b : books) {
			Log.v("tag", "  id:" + b.getId() + "  name:  " + b.getName()
					+ "  price:  " + b.getPrice());
		}

	}

	public void xmlSerializer() throws IllegalArgumentException, IllegalStateException, IOException {
		ArrayList<Book> books = new ArrayList<Book>();
		Book book = new Book();
		book.setId(001);
		book.setName("android");
		book.setPrice(26.3);
		books.add(book);

		book = new Book();
		book.setId(002);
		book.setName("ui");
		book.setPrice(26.3);
		books.add(book);

		book = new Book();
		book.setId(003);
		book.setName("javascript");
		book.setPrice(26.3);
		books.add(book);
		
		XmlSerializer seri = Xml.newSerializer();
		StringWriter writer  = new StringWriter();
		seri.setOutput(writer);
		seri.startDocument("UTF-8", true);
		seri.startTag("", "books");
		for (Book book2 : books) {
			seri.startTag("", "book");
			
			seri.startTag("", "id");
			seri.text(book2.getId()+"");
			seri.endTag("", "id");
			
			seri.startTag("", "name");
			seri.text(book2.getName());
			seri.endTag("", "name");
			
			seri.startTag("", "price");
			seri.text(book2.getPrice()+"");
			seri.endTag("", "price");
			
			seri.endTag("", "book");
		}
		seri.endTag("", "books");
		seri.endDocument();
		Log.v("tag", ""+writer.toString());
	}

	public void readFile() throws IOException {
		InputStream is = getContext().getAssets().open("book.xml");
		BufferedReader read = new BufferedReader(new InputStreamReader(is));
		String len = "";
		String str = "";
		while ((len = read.readLine()) != null) {
			str += len;
		}
		Log.v("tag", "str:  " + str);
	}
}
