package com.zscribeproject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

@WebServlet("/postBlog")
public class PostBlog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static ZWCredentials zwCredentials = null;
	protected static BloggerCredentials bloggerCredentials = null;
	static String writerLink;
	static String bloggerLink;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("reached service from api");
		PostBlog obj = new PostBlog();
		Smtp smtp = new Smtp();
		Register register = new Register();
		String option = request.getParameter("option");
		if(option.equals("writer")){
			
			obj.writer(request, response);
			
			HttpSession sess = request.getSession();
			sess.setAttribute("writerLink", writerLink);
			if(writerLink!=null) {
				smtp.sendMail(register.recipient, "Writer Link: "+writerLink);
				response.sendRedirect("WriterLink.jsp");
			} else {
				PrintWriter printWriter = response.getWriter();
				printWriter.println("Oops! Unable to post the content.");
			}
			
		}
		else if (option.equals("blogger") ){
			
			obj.blogger(request,response);
			
			System.out.println(bloggerLink);
			
			HttpSession sess = request.getSession();
			sess.setAttribute("bloggerLink", bloggerLink);
			if(bloggerLink!=null) {
				smtp.sendMail(register.recipient,"Blogger Link: "+ bloggerLink);
				response.sendRedirect("BloggerLink.jsp");
			} else {
				PrintWriter printWriter = response.getWriter();
				printWriter.println("Oops! Unable to post the content.");
			}
			
			
		}
		else if(option.equals("both")) {
			
			obj.writer(request, response);
			obj.blogger(request,response);
			
			HttpSession sess = request.getSession();
			sess.setAttribute("writerLink", writerLink);
			sess.setAttribute("bloggerLink", bloggerLink);
			if(writerLink!=null && bloggerLink!=null) {
				smtp.sendMail(register.recipient,"Writer Link: "+ writerLink+"\n"+"Blogger Link: "+ bloggerLink);
				response.sendRedirect("Both.jsp");
			} else {
				PrintWriter printWriter = response.getWriter();
				printWriter.println("Oops! Unable to post the content.");
			}
			
		}	
	} 
	public void writer(HttpServletRequest request, HttpServletResponse response) {
		
        System.out.println("reached service from api");
		
		String title = request.getParameter("title");
		String content =request.getParameter("content");
		String data = "filename="+title+"&text="+content;
		try 
		{

			URL url = new URL("https://writer.zoho.com/api/v1/documents");
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			System.out.println("Posting access token: " + zwCredentials.accessTkn);
			httpsURLConnection.setRequestProperty("Authorization", "Zoho-oauthtoken "+ zwCredentials.accessTkn);
			httpsURLConnection.setDoOutput(true);
			httpsURLConnection.setRequestProperty("filename", title);
			httpsURLConnection.setRequestProperty("text", content);
			DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream());
			out.writeBytes(data);
			out.flush();
			out.close();
			
			System.out.println("Response code: " + httpsURLConnection.getResponseCode());
			System.out.println("Response message: " + httpsURLConnection.getResponseMessage());
			
			InputStream stream = httpsURLConnection.getErrorStream();
	        if(stream == null) {
	        	stream = httpsURLConnection.getInputStream();
	        }
	        BufferedReader input = new BufferedReader(new InputStreamReader(stream));
	          
	        String line = null;
	        StringBuilder output = new StringBuilder();           
			while((line = input.readLine()) != null) {
				output.append(line);
			}
			
			JSONObject jsonOutput = new JSONObject(output.toString());
			String documentId = (String) jsonOutput.get("document_id");
			writerLink = "https:writer.zoho.com/writer/open/"+documentId;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public void blogger(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("Code: " + bloggerCredentials.getCode());
		
		String title = request.getParameter("title");
		String content =request.getParameter("content");
		String data = "filename="+title+"&text="+content;
	    try {
	    		    	
	    	bloggerCredentials.createAccessToken();
	    	GetBlogId blog = new GetBlogId();
	    	String blogId = blog.getBlogId();
	    	
	    	 URL url = new URL("https://www.googleapis.com/blogger/v3/blogs/"+blogId+"/posts");
		        HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
		        httpsURLConnection.setRequestMethod("POST");
		        httpsURLConnection.setRequestProperty("Authorization", "Bearer " + BloggerCredentials.accessTkn);
		        httpsURLConnection.setRequestProperty("Content-Type", "application/json");
		        httpsURLConnection.setDoOutput(true);
		        JSONObject post = new JSONObject();
		        post.put("kind", "blogger#post");
		        post.put("title", title);
		        post.put("content", content);
		        DataOutputStream out = new DataOutputStream(httpsURLConnection.getOutputStream());
			    out.writeBytes(post.toString());
			    out.flush();
			    out.close();
		        System.out.println("\nSending 'POST' request to URL : " + url);
		        System.out.println("Response Code : " + httpsURLConnection.getResponseCode());
		        Scanner in = new Scanner(httpsURLConnection.getInputStream());
		        
		        String output = "";
		        while (in.hasNext()) {
		          output += in.nextLine();
		        }
		        in.close();
		        
		        System.out.println(output);
		        
		        JSONObject obj = new JSONObject(output);
		        
		        String blogId1 = obj.getJSONObject("blog").getString("id");
		        String postId1 = obj.getString("id");
		        bloggerLink = "https:www.blogger.com/blog/post/edit/"+blogId1+"/"+postId1;
	        
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
}