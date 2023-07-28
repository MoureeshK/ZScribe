package com.zscribeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Register {
	
	String url="jdbc:mysql://10.191.254.59:3306/UserDetails";
//	String url="jdbc:mysql://192.168.0.108:3306/UserDetails";
	String userName="subha";
	String password ="Mahi@7781";
	String query = "select * from people";
	PasswordHashing encrypt = new PasswordHashing();
	static String recipient;
	public boolean registerUser(User user) {
		boolean registerFlag = false;
		String queryInsert= "insert into people values(?,?,?)";

		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url,userName,password);
			PreparedStatement statement = connection.prepareStatement(queryInsert);
//			statement.setString(1, null);
			statement.setString(1, user.getUserName());
			statement.setString(2, encrypt.hashPassword(user.getPassword()));
			statement.setString(3,user.getEmail());
			int change=statement.executeUpdate();
			
			if(change < 1) {
				
				System.out.println("Username already exsist !");
				registerFlag = false;
				
			}
			else {
				System.out.println("Posted successfully !");
				recipient = user.getEmail();
				System.out.println("Mail id: "+recipient);
				registerFlag = true;
				
			}
			
			statement.close();
			connection.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
		return registerFlag;
	}
	
	public boolean validateLogin(User user) {
		boolean validateFlag = false;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url,userName,password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			
			while(resultSet.next()) {
				String databaseName = resultSet.getString(1);
				String databasePassword = resultSet.getString(2);
				String databaseMail = resultSet.getString(3);
				if(user.getUserName().equals(databaseName) && encrypt.hashPassword(user.getPassword()).equals(databasePassword)) {
					validateFlag = true;
					recipient = databaseMail;
					System.out.println("Mail id: "+recipient);
					break;
				}
			}
			connection.close();
			statement.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return validateFlag;
		
	}
}