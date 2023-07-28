package com.zscribeproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetBloggerCode")
public class GetBloggerCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String code;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		code = (String) request.getParameter("code");
		PostBlog.bloggerCredentials= new BloggerCredentials();
		PostBlog.bloggerCredentials.setCode(code);
//		System.out.println("Code: " + PostBlog.bloggerCredentials.getCode());
		response.sendRedirect("Option.html");
	}
	
}
