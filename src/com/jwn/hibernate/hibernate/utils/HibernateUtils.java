package com.jwn.hibernate.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils
{
	private HibernateUtils()
	{
	}

	private static HibernateUtils instance = new HibernateUtils();
	public static HibernateUtils getInstance()
	{
		return instance;
	}
	private SessionFactory sessionFactory;
	private SessionFactory getSessionFactory()
	{
		if (sessionFactory == null)
		{
			Configuration configuration = new Configuration().configure();
			sessionFactory = configuration.buildSessionFactory();
		}
		return sessionFactory;
	}
	public Session getSession()
	{
		return getSessionFactory().getCurrentSession();
	}
}
