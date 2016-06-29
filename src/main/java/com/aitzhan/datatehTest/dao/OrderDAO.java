package com.aitzhan.datatehTest.dao;

import com.aitzhan.datatehTest.model.OrderEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderDAO {
    List<OrderEntity> getAllOrders();
    OrderEntity getOrder(long id);
    boolean createOrder(OrderEntity order);
    boolean update(OrderEntity order);
    boolean deleteOrder(long id);
    List<OrderEntity> getEmployeesOrders(long id);
    List<OrderEntity> getOrdersToEmployee(long id);
    boolean isValid(OrderEntity order);
}
