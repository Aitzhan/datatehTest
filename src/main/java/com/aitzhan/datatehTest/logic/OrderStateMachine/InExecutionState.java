package com.aitzhan.datatehTest.logic.OrderStateMachine;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.model.ExecutionState;
import com.aitzhan.datatehTest.model.OrderEntity;
import org.hibernate.Session;

public class InExecutionState implements OrderState {

    public void accept(OrderEntity order) {

    }

    public void sendToControl(OrderEntity order) {
        order.setStatus(ExecutionState.IN_CONTROL);
        update(order);

    }

    private void update(OrderEntity order) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(order);
        session.getTransaction().commit();
        session.close();
    }

    public void sendToRework(OrderEntity order) {

    }
}
