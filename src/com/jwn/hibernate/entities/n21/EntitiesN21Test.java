package com.jwn.hibernate.entities.n21;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntitiesN21Test
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
		Customer customer=new Customer();
		customer.setCustomerName("¾°Íúµç×Ó2");
		
		Order order=new Order();
		order.setOrderName("AAA-2");
		order.setCustomer(customer);
		
		
		session.save(order);
		session.save(customer);
	}

}
