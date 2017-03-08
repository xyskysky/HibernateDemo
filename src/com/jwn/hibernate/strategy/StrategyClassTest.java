package com.jwn.hibernate.strategy;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StrategyClassTest
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
	public void testClassLevelStrategy()
	{
		Customer customer=session.get(Customer.class,1);
		System.out.println(customer.getClass());
		System.out.println("========================");
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerName());
		
		System.out.println(customer.getOrders().size());
		
		
	}

}
