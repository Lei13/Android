package com.android.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyApp extends HttpServlet {
	/*
	 * �ͻ���ͨ��GET����
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// �������뷽ʽһ
		resp.setContentType("text/html;charset=UTF-8");
		/* ͨ�����key��ǩ�õ��ͻ��˴������Ĳ��� */
		// req.getParameter("key");
		/* �����������Ļ�Ӧ */
		resp.getWriter().write("hello���");

	}

	/*
	 * �ͻ���ͨ��POST����
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);

	}
}