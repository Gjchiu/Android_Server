package com.store_class.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.store_class.model.*;

@SuppressWarnings("serial")
@WebServlet("/StoreClassServlet")
public class StoreClassServlet extends HttpServlet{

	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		StoreClassDAO storeClassDAO = new StoreClassDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getAll")) {
			List<StoreClassVO> storeClasslist = storeClassDAO.getAll();
			writeText(response, gson.toJson(storeClasslist));	
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