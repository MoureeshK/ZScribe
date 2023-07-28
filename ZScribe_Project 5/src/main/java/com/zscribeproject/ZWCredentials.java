package com.zscribeproject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class ZWCredentials extends Timer{
	URL url;
    HttpsURLConnection httpsURLConnection;
    Scanner scanner = null;
    private static String refreshTkn;
    protected static String accessTkn;
    private String clientID="1000.PCCIS5IELKFG27YML0KPFYX0LWG0DC";
    private String clientSecret="b02f7f28d833e283fff07907cc7cabc469a28c7bbb";
    private String code;
    
    protected Boolean isFirstToken = false;
    
    protected String getCode() {
		return code;
	}

	private String getClientSecret(){
		return clientSecret;
	}
	private String getClientID() {
		return clientID;
	}
	public void setAccessTkn(String accessToken){
		this.accessTkn=accessToken;
	}
	private void setRef_token(String refreshToken) {
		this.refreshTkn=refreshToken;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    public String createAccessToken(String code){
    	String urlString = "https://accounts.zoho.com/oauth/v2/token?client_id=%s&grant_type=authorization_code&client_secret=%s&redirect_uri=%s&code=%s";
    	String redirectUrl = "http://localhost:8130/ZScribe_Project/WriterCode";
        try{
        	WriterCode writerCode = new WriterCode();
            URL url = new URL(String.format(urlString, getClientID(), getClientSecret(), redirectUrl, code));
            HttpURLConnection connector = (HttpURLConnection) url.openConnection();
            connector.setRequestMethod("POST");
            Scanner scan = new Scanner(connector.getInputStream());
            String res = scan.nextLine();
            System.out.println("response: " + res);
            JSONObject jsonObj = new JSONObject(res);
            setAccessTkn(jsonObj.getString("access_token"));
            setRef_token(jsonObj.getString("refresh_token"));
            System.out.println("Access Token: " + jsonObj.getString("access_token"));
            System.out.println("Refresh Token: " + jsonObj.getString("refresh_token"));
        }
        catch (Exception e){
            System.out.println("Error while creating access token....");
            e.printStackTrace();
        }
        return accessTkn;
    }
    
    public JSONObject getDocuments() {
    	System.out.println("called getdocuments");
    	JSONObject jsonObj = null;
        try {
            URL url = new URL("https://zohoapis.com/writer/api/v1/documents?limit=100");
            HttpURLConnection connector = (HttpURLConnection) url.openConnection();
            connector.setRequestMethod("GET");
            connector.setRequestProperty("Authorization", "Bearer " + ZWCredentials.accessTkn);

            Scanner scan = new Scanner(connector.getInputStream());

            String response = scan.nextLine();

            jsonObj = new JSONObject(response);
            System.out.println("JSON object: " + jsonObj);
  
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        	return jsonObj;
		}
        
    }
}
