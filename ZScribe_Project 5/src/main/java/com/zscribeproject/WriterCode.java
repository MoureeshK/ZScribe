package com.zscribeproject;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WriterCode")
public class WriterCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String code;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		code = (String) request.getParameter("code");
		ZWCredentials zwCredentials = new ZWCredentials();
		zwCredentials.setCode(code);
		System.out.println("Code: " + zwCredentials.getCode());
		zwCredentials.createAccessToken(zwCredentials.getCode());
		response.sendRedirect("GoogleAuth.html");
	}
}

/*
@WebServlet("/WriterCode")
public class WriterCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String code;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		code = (String) request.getParameter("code");
		ZWCredentials zwCredentials = new ZWCredentials();
		zwCredentials.setCode(code);
		System.out.println("Code: " + zwCredentials.getCode());
//		zwCredentials.createAccessToken(zwCredentials.getCode());
		response.sendRedirect("GoogleAuth.html");
	}
}

*/