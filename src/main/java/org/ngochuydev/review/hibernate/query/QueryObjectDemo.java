package org.ngochuydev.review.hibernate.query;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.ngochuydev.review.hibernate.HibernateUtils;
import org.ngochuydev.review.hibernate.entities.Employee;

public class QueryObjectDemo {
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.getTransaction().begin();
			
			String sql = "select e from " + Employee.class.getName() + " e "
					+ " order by e.empName, e.empNo";
			
			Query<Employee> query = session.createQuery(sql);
			List<Employee> employees = query.getResultList();
			for (Employee emp : employees) {
				System.out.println("Emp: " + emp.getEmpNo() + " : "
						+ emp.getEmpName());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

}
