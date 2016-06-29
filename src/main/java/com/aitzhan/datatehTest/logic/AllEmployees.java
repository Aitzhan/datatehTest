package com.aitzhan.datatehTest.logic;

public interface AllEmployees {
    String getAllInJSON();
    String getInJSON(long id);
    boolean add(String json);
    boolean remove(long id);
    boolean update(String json);
}
