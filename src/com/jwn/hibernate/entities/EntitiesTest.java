package com.jwn.hibernate.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import javax.imageio.stream.FileImageInputStream;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EntitiesTest
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
	public void testGetImage() throws IOException, SQLException
	{
		Worker worker=session.get(Worker.class, 2);
		System.out.println(worker.getImage().getBinaryStream().available());
	}
	@Test
	public void testSaveImage() throws IOException
	{
        InputStream inputStream=new FileInputStream("image.jpg");

		Worker worker=new Worker();
		worker.setName("BBB");
		Blob image=Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());
		worker.setImage(image);
		Pay pay=new Pay();
		pay.setMonthlyPay(5);
		pay.setYearPay(2015);
		pay.setVocationWithPay(86000);
		worker.setPay(pay);
		session.save(worker);
		
		
	}
	@Test
	public void testDoWork()
	{
		session.doWork(new Work()
		{
			
			@Override
			public void execute(Connection arg0) throws SQLException
			{
				//利用原生的jdbc 来调用存储过程
			}
		});
	}
	@Test
	public void testGet()
	{
		Worker worker=session.get(Worker.class, 1);
		System.out.println(worker.getName());
		System.out.println(worker.getPay().getVocationWithPay());
	}
	@Test
	public void testSave()
	{
		Worker worker=new Worker();
		worker.setName("AAA");
		
		Pay pay=new Pay();
		pay.setMonthlyPay(5);
		pay.setYearPay(2016);
		pay.setVocationWithPay(56000);
		worker.setPay(pay);
		session.save(worker);
		
	}
	

}
