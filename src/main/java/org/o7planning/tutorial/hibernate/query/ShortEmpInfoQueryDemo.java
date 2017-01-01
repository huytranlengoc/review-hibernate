package org.o7planning.tutorial.hibernate.query;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.beans.ShortEmpInfo;
import org.o7planning.tutorial.hibernate.entities.Employee;

public class ShortEmpInfoQueryDemo {

	public static void main(String[] args) {
		SessionFactory factory = HibernateUtils.getSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.getTransaction().begin();

			// Sử dụng cấu tử của Class ShortEmpInfo
			String sql = "Select new " + ShortEmpInfo.class.getName() + "(e.empId, e.empNo, e.empName)" + " from "
					+ Employee.class.getName() + " e ";

			Query<ShortEmpInfo> query = session.createQuery(sql);
			// Thực hiện truy vấn.
			// Lấy ra danh sách các đối tượng ShortEmpInfo
			List<ShortEmpInfo> employees = query.getResultList();

			for (ShortEmpInfo emp : employees) {
				System.out.println("Emp: " + emp.getEmpNo() + " : " + emp.getEmpName());
			}

			// Commit data.
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// Rollback trong trường hợp có lỗi xẩy ra.
			session.getTransaction().rollback();
		}
	}

}