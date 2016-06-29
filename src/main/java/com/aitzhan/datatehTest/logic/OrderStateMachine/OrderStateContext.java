package com.aitzhan.datatehTest.logic.OrderStateMachine;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.model.ExecutionState;
import com.aitzhan.datatehTest.model.OrderEntity;
import org.hibernate.Session;

import javax.ejb.Local;
import javax.ejb.Stateless;

public class OrderStateContext {
    private OrderState state;
    private OrderEntity order;

    public OrderStateContext(long id) {
        order = loadOrder(id);
        ExecutionState status = order.getStatus();
        switch (status) {
            case IN_EXECUTION: state = new InExecutionState(); break;
            case IN_CONTROL: state = new InControlState(); break;
            case IN_REWORK: state = new InReworkState(); break;
        }
    }

    void setState(final OrderState newSate) {
       this.state = newSate;
    }

    private OrderEntity loadOrder(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        OrderEntity order = (OrderEntity) session.get(OrderEntity.class, id);
        session.close();
        return order;
    }

    public void sendToControl() {
        state.sendToControl(order);
    }

    public void accept() {
        state.accept(order);
    }

    public void sendToRework() {
        state.sendToRework(order);
    }
}
