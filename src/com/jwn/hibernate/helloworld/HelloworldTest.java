package com.jwn.hibernate.helloworld;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

public class HelloworldTest
{

	@Test
	public void testHibernate()
	{
		Configuration configuration=new Configuration().configure();
		
		
        ServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory=configuration.buildSessionFactory(serviceRegistry);
		
		
		
		Session session=sessionFactory.openSession();
		
		News news=new News("java", "ÕÅÈý", new Date());
		
		Transaction beginTransaction = session.beginTransaction();
		
		//News news = session.get(News.class, 1);
		session.save(news);
		System.out.println(news);
		
		beginTransaction.commit();
		
		session.close();
		sessionFactory.close();
		
		
	}

}
