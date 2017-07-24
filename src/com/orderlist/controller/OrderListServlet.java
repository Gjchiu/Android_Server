package com.orderlist.controller;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.order.model.Store_OrderDAO;
import com.order.model.Store_OrderVO;
import com.orderlist.model.OrderlistDAO;
import com.orderlist.model.OrderlistService;
import com.orderlist.model.OrderlistVO;

@SuppressWarnings("serial")
@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = request.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(),
				JsonObject.class);
		OrderlistDAO orderlistDAO = new OrderlistDAO();
		String action = jsonObject.get("action").getAsString();
		
		System.out.println("action: " + action);

		if (action.equals("getdetail")) {
			String orderid = jsonObject.get("orderid").getAsString();
			List<OrderlistVO> orderList = orderlistDAO.getDetailOrder(orderid);
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

