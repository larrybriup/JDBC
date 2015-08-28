package com.briup.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import oracle.jdbc.driver.OracleDriver;

public class DriverTest2 {
	public static void main(String[] args) {
		// String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String username = "king";
		String password = "king999";

		try {
			Driver driver = new OracleDriver();
			DriverManager.registerDriver(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("conn=" + conn);
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
