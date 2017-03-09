package com.jwn.hibernate.hibernate.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jwn.hibernate.hibernate.dao.DepartmentDAO;
import com.jwn.hibernate.hibernate.entities.Department;
import com.jwn.hibernate.hibernate.entities.Employee;
import com.jwn.hibernate.hibernate.utils.HibernateUtils;

public class HibernateTest
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
	public void testDoWork()
	{
		session.doWork(new Work()
		{
			
			@Override
			public void execute(Connection arg0) throws SQLException
			{
				System.out.println("========================");
			}
		});
	}
	@Test
	public void testManageSession()
	{
		Session session2=HibernateUtils.getInstance().getSession();
		System.out.println("-->" + session2.hashCode());
		Transaction transaction2=session2.beginTransaction();
		
		Department department=new Department();
		department.setName("王秀部门");
		
		DepartmentDAO dao=new DepartmentDAO();
		dao.save(department);
		dao.save(department);
		dao.save(department);
		dao.save(department);
		transaction2.commit();
		System.out.println(session2.isOpen());
	}
	@Test
	public void testUpdateTimeStampCache(){
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		
		Employee employee = (Employee) session.get(Employee.class, 100);
		employee.setSalary(5566);
		
		emps = query.list();
		System.out.println(emps.size());
	}
	
	@Test
	public void testQueryCache()
	{
		Query query = session.createQuery("from Employee");
		query.setCacheable(true);
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		
		emps=query.list();
		System.out.println(emps.size());
		
		Criteria createCriteria = session.createCriteria(Employee.class);
		createCriteria.setCacheable(true);
		
		emps=createCriteria.list();
		System.out.println(emps.size());
		
		emps=createCriteria.list();
		System.out.println(emps.size());
		
	}
	@Test
	public void testCollectionSecondLevelCache()
	{
	
		Department department=session.get(Department.class, 2);
		System.out.println(department);
		System.out.println(department.getEmps().size());
		transaction.commit();
		session.close();
		session=sessionfactory.openSession();
		transaction=session.beginTransaction();
		
		Department department1=session.get(Department.class, 2);
		System.out.println(department1);
		System.out.println(department1.getEmps().size());
	}
	@Test
	public void testHibernateSecondLevelCache()
	{
		Employee employee=session.get(Employee.class, 100);
		System.out.println(employee);
		
		transaction.commit();
		session.close();
		session=sessionfactory.openSession();
		transaction=session.beginTransaction();
		
		Employee employee1=session.get(Employee.class, 100);
		System.out.println(employee1);
	}
	@Test
	public void testInnerJoin()
	{
		String hql="select distinct d from Department d inner join d.emps where d.id<:id";
		List<Department> list=session.createQuery(hql).setInteger("id", 8).list();
		for(Department d:list)
		{
			System.out.println(d+"===="+d.getEmps().size());
		}
	}
	@Test
	public void testHQLUpdate()
	{
		String hql="delete from Department d where d.id=:id";
		Query query = session.createQuery(hql);
		query.setInteger("id", 22);
		query.executeUpdate();
	}
	@Test
	public void testNativeSQL()
	{
		String hql="insert into d_department(name) values(?)";
		Query query = session.createSQLQuery(hql);
		query.setString(0, "微信部");
		query.executeUpdate();
	}
	@Test
	public void testQBC5()
	{
		Criteria criteria=session.createCriteria(Employee.class);
		criteria.add(Restrictions.gt("salary",8500f)).add(Restrictions.like("email", "%12%"));
		int pageNo=2;
		int pageSize=6;
		List<Employee> list = criteria.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e+"----"+e.getDept());
		}
	}
	@Test
	public void testQBC4()
	{
		Criteria criteria=session.createCriteria(Employee.class);
		criteria.setProjection(Projections.max("salary"));
		
		System.out.println(criteria.uniqueResult());
	}
	@Test
	public void testQBC3()
	{
		Criteria criteria=session.createCriteria(Employee.class);
		
		//And
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.gt("salary", 8500f));
		conjunction.add(Restrictions.like("email", "%12%"));
		System.out.println(conjunction);
		
		//Or
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.gt("salary", 8500f));
		disjunction.add(Restrictions.like("email", "%13%"));
		System.out.println(disjunction);
		
		
		criteria.add(disjunction);
		criteria.add(conjunction);
	
		
		List<Employee> list=criteria.list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e+"----"+e.getDept());
		}
		
	}
	@Test
	public void testQBC2()
	{
		Criteria criteria = session.createCriteria(Department.class);
		criteria.add(Restrictions.ge("id", 6));
		List<Department> list=criteria.list();
		System.out.println(list.size());
       for(Department d:list)
       {
    	   System.out.println(d+"===="+d.getEmps().size());
       }
	}
	@Test
	public void testQBC()
	{
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.add(Restrictions.gt("salary", 8500f)).add(Restrictions.like("email", "%12%"));
		List<Employee> list = criteria.list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e+"======"+e.getDept());
		}
		
	}
	@Test
	public void testInnerJoinFetch()
	{
		String hql="select distinct d from Department d inner join fetch d.emps where d.id<:id";
		List<Department> list=session.createQuery(hql).setInteger("id", 5).list();
		//去除重复数据
		//list=new ArrayList<>(new HashSet<>(list));
		System.out.println(list.size());
		for(Department d:list)
		{
			System.out.println(d+"===="+d.getEmps().size());
		}
	}
	@Test
	public void testLeftJoin()
	{
		String hql="select e from Employee e left join e.dept where e.email like :email";
		
		List<Employee> list=session.createQuery(hql).setString("email", "%12%").list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e+"===="+e.getDept());
		}
	}
	
	
	@Test
	public void testLeftJoinFetch()
	{
		String hql="from Employee e left join fetch e.dept where e.email like :email";
		List<Employee> list=session.createQuery(hql).setString("email", "%12%").list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e+"===="+e.getDept());
		}
	}
	@Test
	public void testGroupBy()
	{
		String hql="select min(e.salary),max(e.salary) from Employee e group by e.dept";
		List<Object[]>list=session.createQuery(hql).list();
		for(Object[]objs:list)
		{
			System.out.println(Arrays.asList(objs));
		}
	}
	@Test
	public void testFieldQuery2()
	{
		String hql="select  new Employee(e.name,e.salary,e.dept) from Employee e where e.salary>:salary and e.email like :email";
		Query query = session.createQuery(hql);
		
		List<Employee> list=query.setFloat("salary", 8500f).setString("email", "%12%").list();
		
		for(Employee e:list)
		{
			System.out.println(e+"--"+e.getDept());
		}
	}
	@Test
	public void testFieldQuery()
	{
		String hql="select e.salary,e.name,e.dept from Employee e where e.salary>:salary and e.email like :email";
		
		Query query = session.createQuery(hql);
		query.setFloat("salary", 8500).setString("email", "%12%");
		List<Object[]> list = query.list();
		for(Object[] objs:list)
		{
			System.out.println(Arrays.asList(objs));
		}
	}
	@Test
	public void testParameterQuery()
	{
		String hql="from Employee e where e.salary>:salary and e.email like :email";
		Query query = session.createQuery(hql);
		query.setParameter("salary", 8500f).setParameter("email", "%12%");
		List<Employee> list=query.list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e+"---"+e.getDept());
		}
	}
	@Test
	public void testNamedQuery()
	{
		Query query = session.getNamedQuery("salaryEmps");
		query.setFloat("salary", 5000).setString("email", "%12%");
		List<Employee> list=query.list();
		System.out.println(list.size());
	}
	@Test
	public void testPageQuery()
	{
		String hql="from Employee e where e.email like :email ";
	    Query query = session.createQuery(hql);
	    
	    int pageNo=1;
	    int pageSize=10;
	    
	    query.setString("email","%12%");
	    query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize);
	    List<Employee> list=query.list();
	    System.out.println(list.size());
	    for(Employee e:list)
	    {
	    	System.out.println(e);
	    }
	}
	
	@Test
	public void testHQLNamedParameter()
	{
		String hql="from Employee e where e.salary>:salary and e.email like :email";
		
		List<Employee> list=session.createQuery(hql).setFloat("salary", 8500f).setString("email", "%12%").list();
		System.out.println(list.size());
	}
	@Test
	public void testHQL3()
	{
		String hql="from Employee e where e.id in(:id)";
		Query query = session.createQuery(hql);
		query.setParameterList("id",new Integer[]{1,2,3,4,5,6});
		List<Employee> list = query.list();
		System.out.println(list.size());
		for(Employee e:list)
		{
			System.out.println(e);
		}
	}
	@Test
	public void testHQL2()
	{
		String hql="from Employee where salary>? and email like ?";
		List<Employee> list=session.createQuery(hql).setFloat(0, 8500).setString(1, "%12%").list();
		System.out.println(list.size());
	}
	@Test
	public void testHQL()
	{
		String hql="from Employee e where e.salary>? and e.email like ? and e.dept=?";
		
		
		Department dept=new Department();
		dept.setId(1);
		Query query = session.createQuery(hql);
		
		query.setFloat(0, 10500).setString(1, "%12%").setEntity(2, dept);
	   
		
		List<Employee> list = query.list();
		
		System.out.println(list.size());
		
		
	}
	@Test
	public void testSave()
	{
		int index=1;
		
		Random random=new Random();
		for(int i=0;i<20;i++)
		{
			Department dept=new Department();
			dept.setName("技术--"+(i+1));
			session.save(dept);
			
			for (int j = 0; j < 50; j++)
			{
				index++;
				Employee emp=new Employee();
				emp.setDept(dept);
				emp.setEmail("xien2--"+index+"@163.com");
				emp.setName("张三--"+random.nextInt(100));
				emp.setSalary(5000*random.nextInt(20));
				session.save(emp);
				
				
			}
		}
		
	}

}
