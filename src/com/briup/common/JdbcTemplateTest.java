package com.briup.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.briup.test.Person;

public class JdbcTemplateTest {
	
	@Test
	@Ignore
	public void testExecute() throws Exception{
		JdbcTemplate template = new JdbcTemplate();
		String sql="insert into person(id,name,age) values(9,'choda',23)";
		template.excute(sql);
	}

	@Test
	@Ignore
	public void testExecuteWithPS() throws Exception{
		JdbcTemplate template = new JdbcTemplate();
		String sql="insert into person(id,name,age) values(?,?,?)";
		Person p= new Person(3,"jj",89);
		
		SetValueHelperForPS psHelper = new SetValueHelperForPS() {
			
			public void setValue(PreparedStatement ps, Object o) throws Exception {
				Person p = (Person)o;
				ps.setLong(1, p.getId());
				ps.setString(2, p.getName());
		        ps.setInt(3, p.getAge());			
			}
		};
		template.executeWithPS(sql, psHelper, p);
	}
	
	@Test
//	@Ignore
	public void testExecuteQuery() throws Exception{
		JdbcTemplate template = new JdbcTemplate();
		String sql="select * from person";
			@SuppressWarnings("unchecked")
			List<Person> list=(List<Person>)template.executeQuery(sql, new GetValueHelperForRS() {
				
				public Object getValue(ResultSet rs) throws Exception {
					List<Person> list = new ArrayList<Person>();
					
					Person p = null;
					
					while(rs.next()){
						long id=rs.getLong("id");
						String name=rs.getString("name");
						int age = rs.getInt("age");
						
						p=new Person(id,name,age);
						list.add(p);
					}
					return list;
				}
			});
			
			for(Person p:list){
				System.out.println(p);
			}
	}
}
