package com.briup.session;

import com.briup.test.Person;

public class TestMySession {
	public static void main(String[] args) {
		try {
			MySession session = new MySession();
			
			Person p = new Person(2,"lili",20);
			session.save(p);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
