package com.zscribeproject;

import java.math.BigInteger;
import java.security.MessageDigest;
public class PasswordHashing {
	    public String hashPassword(String line){
	        String text = null;
	        try {
	            MessageDigest message;
	            message = MessageDigest.getInstance("SHA-512");
	            byte[] messageDigest = message.digest(line.getBytes());
	            BigInteger r = new BigInteger(1, messageDigest);
	            text = r.toString(16);
	            while (text.length() < 32) {
	                text = "0" + text;
	            }
	        } catch (Exception ee) {
	            System.out.println(ee);
	        }
	        return text;
	    }
	}
