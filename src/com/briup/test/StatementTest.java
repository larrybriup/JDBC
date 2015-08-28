package com.briup.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.briup.common.ConnectionFactory;

public class StatementTest {

//	private String driver = "oracle.jdbc.driver.OracleDriver";
//	private String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	private String username = "king";
//	private String password = "king999";
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/test";
	private String username = "test";
	private String password = "test";

	public static void main(String[] args) {
		StatementTest st = new StatementTest();
		Person p = new Person(3, "Larry", 25);
		List<Person> list = st.queryAll();
		for (Person person : list) {
			System.out.println(person);
		}

//		 st.insert(p);
//		 st.delete(2);
		 st.update(p);
	}

	public void insert(Person p) {
		Connection conn = null;
		Statement stmt = null;
		try {
			// 1注册驱动
			Class.forName(driver);
			// 2获得数据库连接对象Connection
			// 这个对象代表java代码和数据库之间的连接
			conn = DriverManager.getConnection(url, username, password);

			// 3获得statement接口的实现对象
			// 这个对象可以帮我们把sql语句送到数据库里去执行
			stmt = conn.createStatement();

			// 4执行sql语句,默认情况下jdbc会自动提交事务
			// person_id_sequence.nextval
			long id = p.getId();
			String name = p.getName();
			int age = p.getAge();

			String sql = "insert into person(id,name,age) values(" + id + ",'"
					+ name + "'," + age + ")";
			// String sql=
			// "insert into person(id,name,age) ,(1,'陈大超',23)";
			// values(person_id_sequence.nextval,'陈大超',23)";
			stmt.execute(sql);
			System.out.println("数据插入成功!");

			// 5如果是查询语句还要接受并处理得到的结果集resultSet

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 6关闭之前打开的各种资源对象,主要关闭的顺序
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public void delete(long id) {

		Connection conn = null;
		Statement stmt = null;
		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, username, password);

			stmt = conn.createStatement();

			String sql = "delete from person where id=" + id + "";
			stmt.execute(sql);
			System.out.println("数据删除成功!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public void update(Person p) {

		Connection conn = null;
		Statement stmt = null;
		try {

			Class.forName(driver);

			conn = DriverManager.getConnection(url, username, password);

			stmt = conn.createStatement();

			long id = p.getId();
			String name = p.getName();
			int age = p.getAge();

			String sql = "update person set name='" + name + "' , age=" + age
					+ " where id=" + id + "";
			stmt.executeUpdate(sql);
			System.out.println("数据更新成功!");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public List<Person> queryAll() {

		List<Person> list = new ArrayList<Person>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
//			conn=DriverManager.getConnection(url,username,password);
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			String sql = "select * from person";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				Person p = new Person(id, name, age);
				list.add(p);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// if (stmt != null)
				// stmt.close();
				// if (conn != null)
				// conn.close();
				// if(rs!=null)
				// rs.close();
				ConnectionFactory.close(rs, stmt, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;

	}

}
