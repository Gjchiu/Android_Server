package com.store.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mem.model.MemberDAO;
import com.mem.model.MemberVO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.store.model.StoreDAO;
import com.store.model.StoreService;
import com.store.model.StoreVO;

import idv.ron.server.main.ImageUtil;
@MultipartConfig(fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024
* 1024)
@SuppressWarnings("serial")
@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {

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
		StoreDAO storeDAO = new StoreDAO();
		String action = jsonObject.get("action").getAsString();
		System.out.println("action: " + action);
	
		if (action.equals("getStore")) {
			String area = jsonObject.get("area").getAsString();
			String type = jsonObject.get("type").getAsString();
			System.out.println("area: " + area);
			List<StoreVO> storelist = storeDAO.findtype(area, type);
			for(StoreVO pro : storelist){
				if(pro.getStore_image()==null)
					break;
				pro.setStore_image(ImageUtil.shrink(pro.getStore_image(), 128));
			}
			writeText(response, gson.toJson(storelist));
		}
		if (action.equals("getStoreAcc")) {
			String account = jsonObject.get("account").getAsString();
			System.out.println("account: " + account);
			String password = jsonObject.get("password").getAsString();
			StoreVO store = storeDAO.findByStoreAcc(account);
			if(store.getStore_acc().equals(account)&&store.getStore_pw().equals(password)){
				store.setStore_image(ImageUtil.shrink(store.getStore_image(), 128));
				writeText(response, gson.toJson(store));
			}else{
					store =null;
					writeText(response, gson.toJson(store));
				}
		}
	}
	private void writeText(HttpServletResponse response, String outText)
			throws IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("outText: " + outText);
		out.print(outText);
	}
		
	public static byte[] getPictureByteArrayFromWeb(Part part) throws IOException {
		InputStream is = part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = is.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		is.close();
		return baos.toByteArray();
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // ���ե�
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // ���ե�
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}
