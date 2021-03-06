package com.scxh.android.pasexml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class XmlParseUtil implements XmlParseInterface {

	@Override
	public ArrayList<Book> xmlPullParse(InputStream in) {
		ArrayList<Book> books = null;
		Book book = null;
		XmlPullParser pull = Xml.newPullParser();
		try {
			pull.setInput(in, "UTF-8");
			int type = pull.getEventType();
			while (XmlPullParser.END_DOCUMENT != type) {
				switch (type) {
				case XmlPullParser.START_DOCUMENT:
					books = new ArrayList<Book>();
					break;

				case XmlPullParser.START_TAG:
					if ("book".equals(pull.getName())) {
						book = new Book();
					} else if ("id".equals(pull.getName())) {
						type = pull.next();
						book.setId(Integer.parseInt(pull.getText()));
					} else if ("name".equals(pull.getName())) {
						type = pull.next();
						book.setName(pull.getText());

					} else if ("price".equals(pull.getName())) {
						type = pull.next();
						book.setPrice(Double.parseDouble(pull.getText()));

					}else if ("author".equals(pull.getName())) {
						type = pull.next();
						book.setAuthor(pull.getText());

					}
					break;
				case XmlPullParser.END_TAG:
					if ("book".equals(pull.getName())) {
						books.add(book);
					}
					break;
				}
				type = pull.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return books;
	}

	@Override
	public String xmlSerializer(ArrayList<Book> books) {
		XmlSerializer seri = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			seri.setOutput(writer);
			seri.startDocument("UTF-8", true);
			seri.startTag("", "books");
			for (Book book : books) {
				seri.startTag("", "book");

				seri.startTag("", "id");
				seri.text(book.getId() + "");
				seri.endTag("", "id");

				seri.startTag("", "name");
				seri.text(book.getName());
				seri.endTag("", "name");

				seri.startTag("", "price");
				seri.text(String.valueOf(book.getPrice()));
				seri.endTag("", "price");

				seri.endTag("", "book");
			}

			seri.endTag("", "books");
			seri.endDocument();
			return writer.toString();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
