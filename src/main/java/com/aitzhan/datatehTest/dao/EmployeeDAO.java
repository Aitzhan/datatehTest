package com.aitzhan.datatehTest.dao;

import com.aitzhan.datatehTest.model.EmployeeEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Aitzhan on 16.06.2016.
 */
@Local
public interface EmployeeDAO {
    List<EmployeeEntity> getAll();

    EmployeeEntity get(long id);

    boolean update(EmployeeEntity employee);

    boolean add(EmployeeEntity employee);

    boolean remove(long id);

    boolean isValid(EmployeeEntity employee);
}
