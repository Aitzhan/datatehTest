package com.aitzhan.datatehTest.logic.Impl;

import com.aitzhan.datatehTest.dao.EmployeeDAO;
import com.aitzhan.datatehTest.logic.AllEmployees;
import com.aitzhan.datatehTest.model.EmployeeEntity;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class AllEmployeesImpl implements AllEmployees {

    @EJB
    private EmployeeDAO employeeDAO;

    private Gson gson = new Gson();

    public String getAllInJSON() {
        return gson.toJson(employeeDAO.getAll());
    }

    public boolean add(String json) {
        EmployeeEntity employee = gson.fromJson(json, EmployeeEntity.class);
        if (employeeDAO.isValid(employee)) {
            return employeeDAO.add(employee);
        }
        else return false;
    }

    public boolean remove(long id) {
        return employeeDAO.remove(id);
    }

    public boolean update(String json) {
        EmployeeEntity employee = gson.fromJson(json, EmployeeEntity.class);

        if (employeeDAO.isValid(employee)) {
            return employeeDAO.update(employee);
        }
        else return false;
    }

    public String getInJSON(long id) {
        EmployeeEntity employee = employeeDAO.get(id);

        return gson.toJson(employee);
    }
}
