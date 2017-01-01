package org.ngochuydev.review.hibernate.query;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.ngochuydev.review.hibernate.HibernateUtils;
import org.ngochuydev.review.hibernate.entities.Employee;

public class QuerySomeColumnDemo {
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.getTransaction().begin();
			
			String sql = "select e.empId, e.empNo, e.empName from " 
					+ Employee.class.getName() + " e ";
			
			Query<Object[]> query = session.createQuery(sql);
			List<Object[]> datas = query.getResultList();
			for (Object[] emp : datas) {
				System.out.println("Emp Id: " + emp[0]);
				System.out.println(" Emp No: " + emp[1]);
				System.out.println(" Emp Name: " + emp[2]);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

}
