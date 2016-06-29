package com.aitzhan.datatehTest.logic.Impl;

import com.aitzhan.datatehTest.dao.OrderDAO;
import com.aitzhan.datatehTest.logic.OrderBean;
import com.aitzhan.datatehTest.model.OrderEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class OrderBeanImpl implements OrderBean {
    @EJB
    OrderDAO orderDAO;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public String getAllOrdersJSON() {
        return gson.toJson(orderDAO.getAllOrders());
    }

    public String getMyOrdersJSON(long employeeId) {
        return gson.toJson(orderDAO.getEmployeesOrders(employeeId));
    }

    public String getOrdersToMeJSON(long employeeId) {
        return gson.toJson(orderDAO.getOrdersToEmployee(employeeId));
    }

    public String get(long id) {
        return gson.toJson(orderDAO.getOrder(id));
    }

    public boolean add(String orderJSON) {
        OrderEntity order;

        try {
            order = gson.fromJson(orderJSON, OrderEntity.class);
        }
        catch (Exception e) {
            return false;
        }

        return orderDAO.isValid(order) && orderDAO.createOrder(order);
    }

    public boolean update(String orderJSON) {
        OrderEntity order;

        try {
            order = gson.fromJson(orderJSON, OrderEntity.class);
        } catch (Exception e) {
            return false;
        }

        return orderDAO.isValid(order) && orderDAO.update(order);
    }

    public boolean remove(long id) {
        return orderDAO.deleteOrder(id);
    }
}