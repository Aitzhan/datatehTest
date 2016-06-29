package com.aitzhan.datatehTest.logic.OrderStateMachine;

import com.aitzhan.datatehTest.model.OrderEntity;

import javax.ejb.Local;

@Local
public interface OrderState {
    void accept(OrderEntity order);
    void sendToControl(OrderEntity order);
    void sendToRework(OrderEntity orderEntity);
}
