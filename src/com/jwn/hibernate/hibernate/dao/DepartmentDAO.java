package com.jwn.hibernate.hibernate.dao;

import org.hibernate.Session;

import com.jwn.hibernate.hibernate.entities.Department;
import com.jwn.hibernate.hibernate.utils.HibernateUtils;

public class DepartmentDAO
{
	public void save(Department dept){
		//�ڲ���ȡ Session ����
		//��ȡ�͵�ǰ�̰߳󶨵� Session ����
		//1. ����Ҫ���ⲿ���� Session ����
		//2. ��� DAO ����Ҳ����ʹ��һ������!
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println(session.hashCode());
		
		session.save(dept);
	}
	
	/**
	 * ����Ҫ����һ�� Session ����, ����ζ����һ��(Service)��Ҫ��ȡ�� Session ����.
	 * ��˵����һ����Ҫ�� Hibernate �� API �������. ���Բ��Ƽ�ʹ�ô��ַ�ʽ. 
	 */
	public void save(Session session, Department dept){
		session.save(dept);
	}
}
