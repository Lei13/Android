package com.android.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		resp.getWriter().write(readFile("D:\\Program Files (x86)\\eclipse\\sdk+adt\\androidworkspace\\webApp\\WEB\\around"));
	}
	
	
	
	private String readFile(String path) throws UnsupportedEncodingException, IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
		String len = "";
		String str = "";
		while ((len = in.readLine()) != null) {
			str+= len;

		}
		in.close();
		return str;
	}


}
