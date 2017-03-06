package com.jwn.hibernate.one2one.foreign;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class One2OneTest
{

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init()
	{
		Configuration configuration=new Configuration().configure();
		
		sessionFactory=configuration.buildSessionFactory();
		session=sessionFactory.openSession();
		transaction=session.beginTransaction();
	}
	@After
	public void destory()
	{
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	@Test
	public void testSave()
	{
	     Department department=new Department();
	     department.setDeptName("¼¼Êõ²¿");
	     
	     Manager manager1=new Manager();
	     manager1.setMgrName("¸ðÔÆßÖ");
	     department.setManager(manager1);
	     
	     manager1.setDepartment(department);
	     
	     session.save(manager1);
	     session.save(department);
	     
	   
	    
	}

}
