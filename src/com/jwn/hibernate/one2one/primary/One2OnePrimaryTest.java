package com.jwn.hibernate.one2one.primary;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class One2OnePrimaryTest
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
		department.setDeptName("维修部");
		
		Manager manager=new Manager();
		manager.setDepartment(department);
		manager.setMgrName("李天明");
		
		department.setManager(manager);;
		
		session.save(manager);
		session.save(department);
	}

}
