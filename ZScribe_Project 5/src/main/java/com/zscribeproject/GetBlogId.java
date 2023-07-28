package com.zscribeproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class GetBlogId{
	public String blogId;
//	BloggerCredentials bloggerCredentials = new BloggerCredentials();
	public String getBlogId() {
		try {
			URL url = new URL("https://www.googleapis.com/blogger/v3/users/self/blogs");
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
			httpsURLConnection.setRequestMethod("GET");
			httpsURLConnection.setRequestProperty("Authorization", "Bearer " + PostBlog.bloggerCredentials.accessTkn);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
			StringBuffer response = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
			    response.append(line);
			}
			reader.close();
			
			JSONObject jsonObject = new JSONObject(response.toString());
			JSONArray blogArray = jsonObject.getJSONArray("items");
			blogId = blogArray.getJSONObject(0).getString("id");
			System.out.println(blogId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blogId;
	}
}