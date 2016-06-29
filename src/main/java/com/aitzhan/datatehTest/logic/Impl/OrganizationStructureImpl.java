package com.aitzhan.datatehTest.logic.Impl;

import com.aitzhan.datatehTest.dao.EmployeeDAO;
import com.aitzhan.datatehTest.dao.OrganizationDAO;
import com.aitzhan.datatehTest.dao.SubdivisionDAO;
import com.aitzhan.datatehTest.logic.OrganizationStructure;
import com.aitzhan.datatehTest.model.EmployeeEntity;
import com.aitzhan.datatehTest.model.SubdivisionEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class OrganizationStructureImpl implements OrganizationStructure {
    @EJB
    OrganizationDAO organizationDAO;
    @EJB
    SubdivisionDAO subdivisionDAO;
    @EJB
    EmployeeDAO employeeDAO;

    private Gson gson = new Gson();

    public String getOrganizationJSON(long id) {
        return gson.toJson(organizationDAO.getOrganizationById(id));
    }

    public String getSubdivisionsListJSON(long organizationId) {
        return gson.toJson(subdivisionDAO.getList(organizationId));
    }

    public String getSubdivisionJSON(long subdivisionId) {
        return gson.toJson(subdivisionDAO.get(subdivisionId));
    }

    public boolean updateSubdivision(String subdivisionJSON) {
        SubdivisionEntity subdivision = gson.fromJson(subdivisionJSON, SubdivisionEntity.class);
        return subdivisionDAO.isValid(subdivision) && subdivisionDAO.update(subdivision);
    }

    public boolean addSubdivision(String subdivisionJSON) {
        boolean success = false;
        SubdivisionEntity subdivision = gson.fromJson(subdivisionJSON, SubdivisionEntity.class);
        if(subdivisionDAO.isValid(subdivision)) {
            success = subdivisionDAO.add(subdivision);
            EmployeeEntity employee = subdivision.getLeader();
            employee.setSubdivision(null);
            success &= employeeDAO.update(employee);
        }
        return success;
    }

    public boolean deleteSubdivision(long id) {
        return subdivisionDAO.remove(id);
    }
}
