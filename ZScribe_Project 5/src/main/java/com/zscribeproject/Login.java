package com.zscribeproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Login extends HttpServlet{
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = "";
		HttpSession loginSession = req.getSession();
		HttpSession loginMessageSession = req.getSession();
		
		loginMessageSession.setMaxInactiveInterval(1);
		
		loginMessageSession.setAttribute("username", username);
		loginMessageSession.setAttribute("message", " ");
		
		User user = new User(username,password,email);
		
		Register reg = new Register();
		boolean isSuccess = reg.validateLogin(user);
		
		if(isSuccess) {
			loginSession.setMaxInactiveInterval(86400); // sets the session to exist for 1 day. (after user have to login again)
			loginSession.setAttribute("username", username);
			res.sendRedirect("ZohoAuth.html");
		}
		else {
			loginMessageSession.setAttribute("message", "Login Failed, Invalid username or password. Try again!");
			res.sendRedirect("SignIn.html");
			System.out.println("User login failed.");
		}
	}
}