package com.aitzhan.datatehTest.dao.Impl;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.dao.EmployeeDAO;
import com.aitzhan.datatehTest.model.EmployeeEntity;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class EmployeeDAOImpl implements EmployeeDAO {
    public List<EmployeeEntity> getAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        List<EmployeeEntity> employees;
        employees = (List<EmployeeEntity>) session.createQuery("select employee from EmployeeEntity employee order by employee.employeeId").list();
        session.close();

        return employees;
    }

    public EmployeeEntity get(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        EmployeeEntity employee = (EmployeeEntity) session.createQuery("select employee from EmployeeEntity employee where employee.employeeId = :id")
                .setParameter("id", id).list().get(0);

        return employee;
    }

    public boolean update(EmployeeEntity employee) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(employee);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    public boolean add(EmployeeEntity employee) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean remove(long id) {
        Session session = null;
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();

            EmployeeEntity employee = (EmployeeEntity)session.load(EmployeeEntity.class, id);
            session.delete(employee);
            session.flush();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean isValid(EmployeeEntity employee) {
            if (employee.getFirstName() == null ||
                employee.getLastName() == null ||
                employee.getPatronymic() == null ||
                employee.getPosition() == null)
            return false;

        return  employee.getFirstName().length() > 0 &&  employee.getFirstName().length() <= 20 &&
                employee.getLastName().length() > 0 && employee.getLastName().length() <= 20 &&
                employee.getPatronymic().length() > 0 && employee.getPatronymic().length() <= 20 &&
                employee.getPosition().length() > 0 && employee.getPosition().length() <= 20;
    }
}
