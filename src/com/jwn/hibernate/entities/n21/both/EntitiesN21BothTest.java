package com.jwn.hibernate.entities.n21.both;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntitiesN21BothTest
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
	public void testCustomer()
	{
		Order order1=session.get(Order.class, 1);
		System.out.println(order1.getOrderName());
		System.out.println(order1.getCustomer().getClass());
	
		System.out.println(order1.getCustomer().getCustomerName());
		
	}
	@Test
	public void testGet()
	{
		Customer customer=session.get(Customer.class, 1);
		System.out.println(customer.getCustomerName());
		System.out.println(customer.getOrders().size());
		
	}
	@Test
	public void testSave()
	{
		Customer customer=new Customer();
		customer.setCustomerName("南京科技");
		
		Order order1=new Order();
		order1.setOrderName("AAA-1");
		order1.setCustomer(customer);
		
		Order order2=new Order();
		order2.setOrderName("BBB-2");
		order2.setCustomer(customer);
		
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		
		session.save(customer);
		session.save(order1);
		session.save(order2);
		
		
	}

}
