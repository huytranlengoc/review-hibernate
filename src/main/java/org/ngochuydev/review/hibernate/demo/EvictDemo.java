package org.ngochuydev.review.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ngochuydev.review.hibernate.DataUtils;
import org.ngochuydev.review.hibernate.HibernateUtils;
import org.ngochuydev.review.hibernate.entities.Employee;

public class EvictDemo {
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		Employee emp = null;
		
		try {
			session.getTransaction().begin();
			emp = DataUtils.findEmployee(session, "E7499");
			System.out.println("- emp Persistent? " + session.contains(emp));
			session.evict(emp);
			System.out.println("- emp Persistent? " + session.contains(emp) );
			emp.setEmpNo("NEW");
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
				
	}
}
