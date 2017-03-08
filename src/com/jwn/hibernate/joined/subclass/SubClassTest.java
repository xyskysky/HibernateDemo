package com.jwn.hibernate.joined.subclass;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubClassTest
{

	private SessionFactory sessionfactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init()
	{
		Configuration configuration=new Configuration().configure();
		sessionfactory=configuration.buildSessionFactory();
		session=sessionfactory.openSession();
		transaction=session.beginTransaction();
	}
	@After
	public void destory()
	{
		transaction.commit();
		session.close();
		sessionfactory.close();
	}
	
	
	@Test
	public void testGet()
	{
         Student student=session.get(Student.class, 3);
         System.out.println(student);
         
         student.setSchool("长宁中心");
         
	}
	@Test
	public void testSave()
	{
		Person person=new Person();
		person.setAge(23);
		person.setName("AA");
		
		Person person1=new Person();
		person1.setAge(44);
		person1.setName("BB");
		
		Student student=new Student();
		student.setAge(55);
		student.setName("CC");
		student.setSchool("中心");
		
		Student student1=new Student();
		student1.setAge(66);
		student1.setName("DD");
		student1.setSchool("小学");
		
		session.save(person);
		session.save(person1);
		session.save(student);
		session.save(student1);
		
	}

}
