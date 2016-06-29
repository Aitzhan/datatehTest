package com.aitzhan.datatehTest.dao.Impl;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.dao.OrderDAO;
import com.aitzhan.datatehTest.model.OrderEntity;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class OrderDAOImpl implements OrderDAO {
    public List<OrderEntity> getAllOrders() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        List<OrderEntity> orders;
        orders = (List<OrderEntity>) session.createQuery("select order from OrderEntity order").list();
        session.close();

        return orders;
    }

    public OrderEntity getOrder(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        OrderEntity order = (OrderEntity) session.createQuery("select order from OrderEntity order where order.orderId = :id")
                .setParameter("id", id).list().get(0);

        return order;
    }

    public List<OrderEntity> getEmployeesOrders(long employeeId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        List<OrderEntity> employeeOrders = (List<OrderEntity>) session.createQuery("select order from OrderEntity  order where order.author.employeeId = :employeeId")
                .setParameter("employeeId", employeeId).list();

        return employeeOrders;
    }

    public List<OrderEntity> getOrdersToEmployee(long employeeId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        return (List<OrderEntity>) session.createQuery("select order from OrderEntity order where order.executor.employeeId = :employeeId")
                .setParameter("employeeId", employeeId).list();
    }

    public boolean createOrder(OrderEntity order) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean update(OrderEntity order) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean isValid(OrderEntity order) {
        return order.getPeriodOfExecution() != null &&
                order.getSignOfControl().length() <= 200 &&
                order.getSignOfExecution().length() <= 200 &&
                order.getSubject().length() <= 100 &&
                order.getText().length() <= 2000 &&
                order.getAuthor() != null && order.getExecutor() != null;
    }

    public boolean deleteOrder(long id) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();

            OrderEntity employee = (OrderEntity) session.load(OrderEntity.class, id);
            session.delete(employee);
            session.flush();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
