package com.android.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		int data_per_page = Integer.parseInt(req.getParameter("dataPerPage"));
		int current_page = Integer.parseInt(req.getParameter("currentPage"));
		
		
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 1; i < 101; i++) {
			list.add(i + "  information");
		}
		PageUtil pageUtil = new PageUtil(list.size(), data_per_page, current_page);
		List<String> data = list.subList(pageUtil.getStartIndex(), pageUtil.getEndIndex());
		
		JSONArray array = new JSONArray();
		array.addAll(data);
		JSONObject object = new JSONObject();
		object.put("content", array);
		object.put("totalpage", pageUtil.getTotalPage());
		resp.getWriter().print(object);
		

	}

}
