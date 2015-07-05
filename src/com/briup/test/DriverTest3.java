package com.briup.test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DriverTest3 {
public static void main(String[] args) {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String username = "king";
	String password = "king999";
	
	try {
//		-Djdbc.drivers="oracle.jdbc.driver.OracleDriver";
//		System.setProperty("jdbc.drivers", driver);
		Class.forName(driver);
		Connection conn = 
			DriverManager.getConnection(url,username,password);
		
		System.out.println("conn="+conn);
		if(conn!=null)conn.close();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}
}
