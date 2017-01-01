package org.o7planning.tutorial.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.DataUtils;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Department;

public class PersistentDemo {
	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.getCurrentSession();
		Department department = null;
		try {
			session.getTransaction().begin();
			System.out.println("- Finding Department deptNo = D10...");
			department = DataUtils.findDepartment(session, "D10");
			System.out.println("- First change Location");
			department.setLocation("Chicago " + System.currentTimeMillis());
			
			System.out.println("- Location = " + department.getLocation());
			System.out.println("- Calling flush...");
			
			session.flush();
			System.out.println("- Flush OK");
			
			System.out.println("- Second change Location");
			department.setLocation("Chicago " + System.currentTimeMillis());
			System.out.println("- Location = " + department.getLocation());
			System.out.println("- Calling commit...");
			session.getTransaction().commit();
			System.out.println("- Commit OK");
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		// Tạo lại session sau khi nó đã bị đóng trước đó
        // (Do commit hoặc rollback)
        session = factory.getCurrentSession();
        try {
            session.getTransaction().begin();
 
            System.out.println("- Finding Department deptNo = D10...");
            // Query lại Department D10.
            department = DataUtils.findDepartment(session, "D10");
 
            // In ra thông tin Location.
            System.out.println("- D10 Location = " + department.getLocation());
 
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
	}
}
