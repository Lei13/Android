package com.android.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyApp extends HttpServlet {
	/*
	 * 客户端通过GET请求
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 处理乱码方式一
		resp.setContentType("text/html;charset=UTF-8");
		/* 通过这个key标签得到客户端传过来的参数 */
		// req.getParameter("key");
		/* 服务器给出的回应 */
		resp.getWriter().write("hello你好");

	}

	/*
	 * 客户端通过POST请求
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);

	}
}
