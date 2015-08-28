package com.briup.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTemplate {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement ps;

	public JdbcTemplate() {
		init();
	}

	private void init() {
		try {
			conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excute(String sql) throws Exception {
		stmt = conn.createStatement();
		stmt.execute(sql);

		ConnectionFactory.close(stmt, conn);
	}

	public Object executeQuery(String sql, GetValueHelperForRS helper)
			throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		Object o = helper.getValue(rs);

		ConnectionFactory.close(stmt, conn);
		return o;
	}

	public void executeWithPS(String sql, SetValueHelperForPS helper, Object o)
			throws Exception {
		ps = conn.prepareStatement(sql);
		helper.setValue(ps, o);
		ps.execute();
		ConnectionFactory.close(ps, conn);
	}

}
