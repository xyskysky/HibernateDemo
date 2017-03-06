package com.jwn.hibernate.n2n;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class N2NTest
{
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init()
	{
		Configuration configuration = new Configuration().configure();

		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	@After
	public void destory()
	{
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

	@Test
	public void testGet()
	{
		Category category=session.get(Category.class, 3);
		System.out.println(category.getName());
		System.out.println("==================");
		System.out.println(category.getItems().size());
		System.out.println("==================");
		for(Item item:category.getItems())
		{
			System.out.println(item.getName());
		}
	}
	@Test
	public void testSave()
	{
		Category category=new Category();
		category.setName("AA");
		
		Category category1=new Category();
		category1.setName("BB");
		
		Item item=new Item();
		item.setName("Item-AA");
		
		Item item1=new Item();
		item1.setName("Item-BB");
		
		category.getItems().add(item);
		category.getItems().add(item1);
		category1.getItems().add(item);
		category1.getItems().add(item1);
		
		
		item.getCategorys().add(category);
		item.getCategorys().add(category1);
		
		item1.getCategorys().add(category);
		item1.getCategorys().add(category1);
		
		session.save(category);
		session.save(category1);
		
		session.save(item);
		session.save(item1);
	}

}
