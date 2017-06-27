package com.mem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mem.model.MemberDAO;
import com.mem.model.MemberVO;

@SuppressWarnings("serial")
@WebServlet("/MemberServlet1")
public class MemberServlet extends HttpServlet {

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
		MemberDAO memberDao = new MemberDAO();
		String action = jsonObject.get("action").getAsString();
		String account = jsonObject.get("account").getAsString();
		System.out.println("action: " + action);

		if (action.equals("getMember")) {
			MemberVO memberVO = memberDao.findByPrimaryKey(account);
			writeText(response, gson.toJson(memberVO));
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