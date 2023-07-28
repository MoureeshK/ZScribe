package com.zscribeproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class BloggerCredentials extends Timer{
    private static String refreshTkn;
    protected static String accessTkn;
    private String clientID="755241042832-i1kdg3a4bgsijchfo15f2mpher9g7vpm.apps.googleusercontent.com";
    private String clientSecret="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";
    private String code;
    
    protected Boolean isFirstToken = false;
    
    protected String getCode() {
		return code;
	}

	private String getClientSecret(){
		return clientSecret;
	}
	private String getClientID() {
		// TODO Auto-generated method stub
		return clientID;
	}
	public void setAccessTkn(String accessToken){
		this.accessTkn=accessToken;
	}
	private void setRef_token(String refreshToken) {
		// TODO Auto-generated method stub
		this.refreshTkn=refreshToken;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    public void createAccessToken(){
    	String redirectUrl = "http://localhost:8130/ZScribe_Project/GetBloggerCode";
        try{
            URL url = new URL("https://oauth2.googleapis.com/token?client_id="+getClientID()+"&grant_type=authorization_code&client_secret="+getClientSecret()+"&redirect_uri="+redirectUrl+"&code="+getCode());
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
			httpsURLConnection.setRequestMethod("POST");
			httpsURLConnection.setUseCaches(false);
			httpsURLConnection.setRequestProperty("Content-Length", "0");
			
			httpsURLConnection.setRequestProperty("User-Agent", "PostmanRuntime/7.31.1");
			httpsURLConnection.setRequestProperty("Connection", "keep-alive");

			httpsURLConnection.setDoOutput(true);
			/*
			 * BufferedReader input = new BufferedReader(new
			 * InputStreamReader(httpsURLConnection.getInputStream()));
			 */
			
			Scanner input = new Scanner(new InputStreamReader(httpsURLConnection.getInputStream()));
	          
			/*
			 * String line = null; StringBuilder credentials = new StringBuilder();
			 * while((line = input.readLine()) != null) { credentials.append(line); }
			 */
		    String credentials = "";
		    while(input.hasNext()) {
		    	credentials += input.next().trim();
		    }
			
            System.out.println("Credentials"+credentials);
            JSONObject jsonObj = new JSONObject(credentials);
            System.out.println(jsonObj);
            setAccessTkn(jsonObj.getString("access_token"));
            setRef_token(jsonObj.getString("refresh_token"));
            System.out.println(accessTkn);
            System.out.println(refreshTkn);
        }
        catch (Exception e){
            System.out.println("Error while creating access token....");
            e.printStackTrace();
        }
//        return accessTkn;
    }
}