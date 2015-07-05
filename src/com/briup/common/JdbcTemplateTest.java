package com.briup.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.briup.test.Person;

public class JdbcTemplateTest {
public static void main(String[] args) {
	JdbcTemplate template = new JdbcTemplate();
	
////	String sql="insert into person(id,name,age) values(3,'choda',23)";
//	String sql="select * from person";
//	try {
////		template.excute(sql);
//		List<Person> list=(List)template.executeQuery(sql, new GetValueHelperForRS() {
//			
//			public Object getValue(ResultSet rs) throws Exception {
//				List<Person> list = new ArrayList<Person>();
//				
//				Person p = null;
//				
//				while(rs.next()){
//					long id=rs.getLong("id");
//					String name=rs.getString("name");
//					int age = rs.getInt("age");
//					
//					p=new Person(id,name,age);
//					list.add(p);
//					
//				}
//				return list;
//			}
//		});
//		
//		for(Person p:list){
//			System.out.println(p);
//		}
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
	try {
		String sql="insert into person(id,name,age) values(?,?,?)";
		Person p= new Person(8,"jj",89);
		
		SetValueHelperForPS psHelper = new SetValueHelperForPS() {
			
			public void setValue(PreparedStatement ps, Object o) throws Exception {
				Person p = (Person)o;
				ps.setLong(1, p.getId());
				ps.setString(2, p.getName());
		        ps.setInt(3, p.getAge());			
			}
		};
		template.executeWithPS(sql, psHelper, p);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
