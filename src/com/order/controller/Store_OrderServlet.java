package com.order.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.order.model.Store_OrderDAO;
import com.order.model.Store_OrderVO;
import com.orderlist.model.OrderlistVO;

@SuppressWarnings("serial")
@WebServlet("/Store_OrderServlet")
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
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
		
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			String memid = jsonObject.get("memid").getAsString();
			System.out.println("memid: ");
			List<Store_OrderVO> orderList = orderDao.getAll(memid);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("getByState")) {
			String memid = jsonObject.get("memid").getAsString();
			String state = jsonObject.get("state").getAsString();
			List<Store_OrderVO> orderList = orderDao.getByState(memid,state);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("getBycomState")) {
			String memid = jsonObject.get("memid").getAsString();
			List<Store_OrderVO> orderList = orderDao.getBycomState(memid);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("getBytake")) {
			String memid = jsonObject.get("memid").getAsString();
			List<Store_OrderVO> orderList = orderDao.getBytake(memid);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("insertorder")) {
			String order = jsonObject.get("order").getAsString();
			Store_OrderVO store_OrderVO = gson.fromJson(order, Store_OrderVO.class);
			String orderlist = jsonObject.get("orderlist").getAsString();
			Type listType = new TypeToken<List<OrderlistVO>>() {}.getType();
			List<OrderlistVO> orderList = gson.fromJson(orderlist, listType);
			
			orderDao.insertWithOrderlist(store_OrderVO, orderList);
			boolean bool =true;
			writeText(response, gson.toJson(bool));
		}if (action.equals("update")) {
			String orderid = jsonObject.get("orderid").getAsString();
			String storeid = jsonObject.get("storeid").getAsString();
			orderDao.updatestate(orderid,storeid);
			boolean bool =true;
			writeText(response, gson.toJson(bool));
		}
		
		if (action.equals("storegetByState")) {
			String storeid = jsonObject.get("storeid").getAsString();
			String state = jsonObject.get("state").getAsString();
			List<Store_OrderVO> orderList = orderDao.storegetByState(storeid,state);
			writeText(response, gson.toJson(orderList));
		}
		if (action.equals("getByorderid")) {
			String orderid = jsonObject.get("orderid").getAsString();
			Store_OrderVO order = orderDao.findByPrimaryKey(orderid);
			writeText(response, gson.toJson(order));
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
