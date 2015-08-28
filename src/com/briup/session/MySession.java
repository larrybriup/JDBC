package com.briup.session;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.briup.common.ConnectionFactory;
import com.briup.common.SetValueHelperForPS;
import com.briup.test.Person;

public class MySession {
	private Connection conn;
	private PreparedStatement ps;
	// 所要处理的类叫什么
	private String className;
	// 这个类对应 的表示什么
	private String tableName;

	// key:类中的属性,value:表中的列
	// 属性和列的对应关系
	private Map<String, String> map;

	// key:表中列的名字,value:这个列和第几个占位符?相对应
	private Map<String, Integer> temp;

	public MySession() {
		init();
	}

	private void init() {
		temp = new HashMap<String, Integer>();
		//
		className = "com.briup.test.Person";
		tableName = "person";
		map = new HashMap<String, String>();
		map.put("id", "id");
		map.put("name", "name");
		map.put("age", "age");
	}

	public void save(Object o) throws Exception {
		// 1要有一個conn
		conn = ConnectionFactory.getConnection();
		// 2sql语句
		// String sql="insert into person(id,name,age) values(?,?,?)";
		String sql = createSql();

		// 3要有一個ps
		ps = conn.prepareStatement(sql);

		// 4对象o中的值放到sql语句的?里面

		setValue(ps, o);
		// 5执行ps.execute();
		ps.execute();
		System.out.println("操作成功!");

		ConnectionFactory.close(ps, conn);

	}

	private void setValue(PreparedStatement ps, Object o) throws Exception {
		// ps.setLong(1,id);
		// ps.setString(2,name);
		// ps.setInt(3,age);
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {

			String name = f.getName();
			String type = f.getType().getName();
			// System.out.println(name+"  "+type);

			// 获得这个属性对应的列的名字
			String col = map.get(name);
			// 获得这个列在sql语句中对应第几个占位符
			int index = temp.get(col);

			// 获得这个属性所对应的getXXX方法镜像
			Method m = getMethod(name, c);
			// 反射调用这个方法
			if ("long".equals(type)) {
				Long value = (Long) m.invoke(o);
				ps.setLong(index, value);
			}
			if ("java.lang.String".equals(type)) {
				String value = (String) m.invoke(o);
				ps.setString(index, value);
			}
			if ("int".equals(type)) {
				Integer value = (Integer) m.invoke(o);
				ps.setInt(index, value);
			}

		}
	}

	private Method getMethod(String name, Class c) throws SecurityException,
			NoSuchMethodException {
		// 首字母大写
		name = name.substring(0, 1).toUpperCase()
				+ name.substring(1, name.length());
		// getXxx
		String methodName = "get" + name;
		// 获得getXxx方法的镜像
		Method m = c.getMethod(methodName);
		// 返回方法的镜像
		return m;
	}

	private String createSql() {
		String s1 = "";
		String s2 = "";
		int index = 1;
		for (String col : map.values()) {
			s1 += col + ",";
			s2 += "?,";
			temp.put(col, index);
			index++;

		}
		s1 = s1.substring(0, s1.length() - 1);
		s2 = s2.substring(0, s2.length() - 1);
		// s1="id,name,age";
		// s2="?,?,?";
		String sql = "insert into " + tableName + "(" + s1 + ") values(" + s2
				+ ")";
		return sql;
	}

	// 非模板
	public void save2(Person p) {

	}
	// public static void main(String[] args) throws SecurityException,
	// SQLException, NoSuchMethodException {
	// try {
	// MySession ms=new MySession();
	// // String sql=ms.createSql();
	// // System.out.println(sql);
	// ms.setValue(null, new Person());
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
}
