package com.briup.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.briup.common.ConnectionFactory;

public class PreparedStatementTest {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private Statement stmt;

	public static void main(String[] args) {
		PreparedStatementTest pst = new PreparedStatementTest();
		List<Person> list = new ArrayList<Person>();

		Person p = null;
		for (int i = 0; i < 2000; i++) {
			p = new Person(i, "cho", 20);
			list.add(p);
			// pst.insert(p);
		}
		long start = System.currentTimeMillis();
//		pst.insertListForPS(list);// 261
		 pst.insertListForStmt(list);//1978
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
//		pst.psForDate();
//		pst.stmtForDate();

	}
public void stmtForDate(){
	try {
		Connection conn = 
				ConnectionFactory.getConnection();
		stmt=conn.createStatement();
//		java.util.Date date = new java.util.Date();
//		long time=date.getTime();
		
		String sql="insert into student(mydate) values(to_date('12-03-13','dd-mm-yy'))";
//		String sql="insert into student(mydate) values(to_date("+time+",'dd-mm-yy'))";
		System.out.println(sql);
		
		stmt.execute(sql);
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		try {
			ConnectionFactory.close(stmt, conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
public void psForDate() {
		try {
			Connection conn = 
					ConnectionFactory.getConnection();
			String sql="insert into student(mydate) values(?)";
			ps=conn.prepareStatement(sql);
			
			java.util.Date d1 = new java.util.Date();
//			System.out.println(d1);//Sun Aug 25 18:24:11 CST 2013
			long time = d1.getTime();
			
			java.sql.Date d2 = new java.sql.Date(time);//1
			ps.setDate(1, d2);
			

//			java.sql.Timestamp timestamp = new Timestamp(time);//2
//			ps.setTimestamp(1, timestamp);
			
			ps.execute();
		} catch (Exception e) {

		} finally {
			try {
				ConnectionFactory.close(ps, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void insertListForStmt(List<Person> list) {
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.createStatement();
			String sql = "";
			for (Person p : list) {
				long id = p.getId();
				String name = p.getName();
				int age = p.getAge();
				sql = "insert into person(id,name,age) values(" + id + ",'"
						+ name + "'," + age + ")";
				stmt.execute(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.close(stmt, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void insertListForPS(List<Person> list) {
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "insert into person(id,name,age) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			// 不自动提交
			// conn.setAutoCommit(false);
			int i = 0;
			for (Person p : list) {
				i++;
				ps.setLong(1, p.getId());
				ps.setString(2, p.getName());
				ps.setInt(3, p.getAge());
				// 设置批处理
				ps.addBatch();
				// 100条处理一次
				if (i == 100) {
					ps.executeBatch();
					i = 0;
				}
			}
			// 处理剩下的
			ps.executeBatch();
			// 手动提交
			// conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionFactory.close(ps, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void insert(Person p) {
		try {
			conn = ConnectionFactory.getConnection();
			String sql = "insert into person(id,name,age) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			// 用值把sql语句中的?去掉
			ps.setLong(1, p.getId());
			ps.setString(2, p.getName());
			ps.setInt(3, p.getAge());
			ps.execute();
			// 注意:这个时候一定不要把sql语句方进去,这样运行时会报错的
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				ConnectionFactory.close(ps, conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
