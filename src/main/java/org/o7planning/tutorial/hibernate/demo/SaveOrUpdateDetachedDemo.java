package org.o7planning.tutorial.hibernate.demo;

import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.o7planning.tutorial.hibernate.DataUtils;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Employee;

public class SaveOrUpdateDetachedDemo {
	public static void main(String[] args) {
		Employee emp = getEmployee_Detached();
		System.out.println(" - GET EMP " + emp.getEmpId());
		boolean delete = deleteOrNotDelete(emp.getEmpId());
		System.out.println(" - DELETE? " + delete);
		saveOrUpdate_test(emp);
		System.out.println(" - EMP ID " + emp.getEmpId());
	}

	private static Employee getEmployee_Detached() {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		Employee emp = null;

		try {
			session.getTransaction().begin();

			Long maxEmpId = DataUtils.getMaxEmpId(session);
			System.out.println(" - Max Emp ID " + maxEmpId);

			Employee emp2 = DataUtils.findEmployee(session, "E7839");

			Long empId = maxEmpId + 1;
			emp = new Employee();
			emp.setEmpId(empId);
			emp.setEmpNo("E" + empId);

			emp.setDepartment(emp2.getDepartment());
			emp.setEmpName(emp2.getEmpName());
			emp.setDepartment(emp2.getDepartment());
			emp.setEmpName(emp2.getEmpName());
			emp.setHireDate(emp2.getHireDate());
			emp.setJob("Test");
			emp.setSalary(1000F);
			
			session.persist(emp);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
		return emp;
	}
	
	private static boolean deleteOrNotDelete(Long empId) {
		int random = new Random().nextInt(10);
		if (random < 5) {
			return false;
		}
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.getTransaction().begin();
			String sql = "delete " + Employee.class.getName() + " e "
					+ " where e.empId = :empId";
			Query query = session.createQuery(sql);
			query.setParameter("empId", empId);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			return false;
		}
	}
	
	private static void saveOrUpdate_test(Employee emp) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		try {
			session.getTransaction().begin();
			System.out.println(" - emp Persitent? " + session.contains(emp));
			System.out.println(" - Emp salary before update: "
					+ emp.getSalary());
			
			emp.setSalary(emp.getSalary() + 100);
			session.saveOrUpdate(emp);
			session.flush();
			System.out.println(" - Emp salary after update: " + emp.getSalary());
			
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		}
	}
}
