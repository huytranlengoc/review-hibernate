package org.o7planning.tutorial.hibernate.demo;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.DataUtils;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Department;
import org.o7planning.tutorial.hibernate.entities.Employee;

public class PersistDemo {
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		Department department = null;
		Employee emp = null;
		
		try {
			session.getTransaction().begin();
			Long maxEmpId = DataUtils.getMaxEmpId(session);
			Long empId = maxEmpId + 1;
			
			department = DataUtils.findDepartment(session, "D10");
			
			emp = new Employee();
			emp.setEmpId(empId);
			emp.setEmpNo("E"+empId);
			emp.setEmpName("Name " + empId);
			emp.setJob("Coder");
			emp.setSalary(1000f);
			emp.setManager(null);
			emp.setHireDate(new Date());
			System.out.println("date now: " + new Date());
			emp.setDepartment(department);
			session.persist(emp);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		System.out.println("Emp No: " + emp.getEmpNo());
	}
}
