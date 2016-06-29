package com.aitzhan.datatehTest.logic;

public interface OrderBean {
    String getAllOrdersJSON();
    String getMyOrdersJSON(long employeeId);
    String getOrdersToMeJSON(long employeeId);
    String get(long id);
    boolean add(String orderJSON);
    boolean update(String orderJSON);
    boolean remove(long id);
}