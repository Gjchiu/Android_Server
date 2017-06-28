package com.order.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.order.model.Store_OrderDAO;
import com.order.model.Store_OrderVO;

@SuppressWarnings("serial")
@WebServlet("/Store_OrderServlet1")
public class Store_OrderServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Store_OrderDAO orderDao = new Store_OrderDAO();
		List<Store_OrderVO> orderList = orderDao.getAll();
		writeText(response, new Gson().toJson(orderList));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		Store_OrderDAO orderDao = new Store_OrderDAO();
		String action = jsonObject.get("action").getAsString();
		String memid = jsonObject.get("memid").getAsString();
		System.out.println("action: " + action);
		System.out.println("memid: " + memid);

		if (action.equals("getAll")) {
			List<Store_OrderVO> orderList = orderDao.getAll(memid);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("getByState")) {
			List<Store_OrderVO> orderList = orderDao.getByState(memid);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("getBycomState")) {
			List<Store_OrderVO> orderList = orderDao.getBycomState(memid);
			writeText(response, gson.toJson(orderList));
		}
	}

	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText: " + outText);
		out.print(outText);
	}
	
}
