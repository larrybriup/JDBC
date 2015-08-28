package com.briup.test;

import java.sql.Connection;
import java.sql.DriverManager;

public class DriverTest1 {
	public static void main(String[] args) {
		// 连接数据库必须要有的String字符串信息

		// driver标明要连接的是哪一种数据库dbms
		// 驱动类的全包名加类名
//		String driver = "oracle.jdbc.driver.OracleDriver";
//
//		// 标明要连接的是哪一个数据库实例(地址)
//		String url = "jdbc:oracle:thin:@localhost:1521:XE";
//
//		//
//		String username = "king";
//
//		//
//		String password = "king999";
		String driver = "com.mysql.jdbc.Driver";

		// 标明要连接的是哪一个数据库实例(地址)
		String url = "jdbc:mysql://localhost:3306/test";

		//
		String username = "test";

		//
		String password = "test";

		try {
			
			// 1注册驱动
			Class.forName(driver).newInstance();
			// 2获得数据库连接对象Connection
			Connection conn = DriverManager.getConnection(url, username,
					password);
			System.out.println("conn=" + conn);
			// 3获得statement接口对象

			// 4执行sql语句\

			// 5如果是查询语句还要接受并处理得到的结果集resultSet

			// 6关闭之前打开的各种资源对象
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}
}
