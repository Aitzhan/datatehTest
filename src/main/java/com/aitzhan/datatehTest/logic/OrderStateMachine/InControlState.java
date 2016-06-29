package com.aitzhan.datatehTest.logic.OrderStateMachine;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.dao.OrderDAO;
import com.aitzhan.datatehTest.model.ExecutionState;
import com.aitzhan.datatehTest.model.OrderEntity;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

public class InControlState implements OrderState {

    public void accept(OrderEntity order) {
        order.setStatus(ExecutionState.ACCEPTED);
        update(order);
    }

    public void sendToRework(OrderEntity order) {
        order.setStatus(ExecutionState.IN_REWORK);
        update(order);
    }

    private void update(OrderEntity order) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(order);
        session.getTransaction().commit();
        session.close();
    }

    public void sendToControl(OrderEntity order) {
    }
}
