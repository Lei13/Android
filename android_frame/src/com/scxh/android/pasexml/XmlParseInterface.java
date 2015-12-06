package com.scxh.android.pasexml;

import java.io.InputStream;
import java.util.ArrayList;

public interface XmlParseInterface {
	public ArrayList<Book> xmlPullParse(InputStream in);
	public String xmlSerializer(ArrayList<Book> books);

}
