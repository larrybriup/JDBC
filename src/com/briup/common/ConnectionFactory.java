package com.briup.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionFactory {
	// private static String driver = "oracle.jdbc.driver.OracleDriver";
	// private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	// private static String username = "king";
	// private static String password = "king999";

	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	static {
		try {
			Properties p = new Properties();
//			p.load(new FileInputStream(new File(
//					"src/com/briup/common/db.properties")));
			
			p.load(ConnectionFactory.class.
					getResourceAsStream("db.properties"));
			
//			p.load(ConnectionFactory.class.
//					getClassLoader().getResourceAsStream(
//							"src/com/briup/common/db.properties"));
			
		
			driver=p.getProperty("driver");
			url=p.getProperty("url");
			username=p.getProperty("username");
			password=p.getProperty("password");
//			
//			System.out.println(driver);
//			System.out.println(url);
//			System.out.println(username);
//			System.out.println(password);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);

		return conn;

	}

	public static void close(ResultSet rs, Statement stmt, Connection conn)
			throws Exception {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();

	}

	public static void close(Statement stmt, Connection conn) throws Exception {
		close(null, stmt, conn);

	}
	
}
