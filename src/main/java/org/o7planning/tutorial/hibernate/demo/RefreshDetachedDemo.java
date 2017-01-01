package org.o7planning.tutorial.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.DataUtils;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Employee;

public class RefreshDetachedDemo {
	public static void main(String[] args) {
		Employee emp = getEmployee_Detached();
		System.out.println(" - GET EMP " + emp.getEmpId());
		refresh_test(emp);
	}

	private static void refresh_test(Employee emp) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.getTransaction().begin();
			System.out.println(" - emp Persistent? " + session.contains(emp));
			System.out.println(" - Emp salary before update: "
					+ emp.getSalary());
			emp.setSalary(emp.getSalary() + 100);
			session.refresh(emp);
			System.out.println(" - emp Persistent? " + session.contains(emp));
			System.out.println(" - Emp salary after refresh: "
					+ emp.getSalary());
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}

	// Hàm trả về một đối tượng Employee đã
	// nằm ngoài sự quản lý của Hibernate (Detached).
	private static Employee getEmployee_Detached() {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session1 = factory.getCurrentSession();
		Employee emp = null;
		try {
			session1.getTransaction().begin();
			emp = DataUtils.findEmployee(session1, "E7839");
			session1.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session1.getTransaction().rollback();
		}
		return emp;
	}
}
