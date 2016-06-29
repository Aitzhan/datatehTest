package com.aitzhan.datatehTest.dao;

import com.aitzhan.datatehTest.model.SubdivisionEntity;

import javax.ejb.Local;
import java.sql.SQLException;
import java.util.List;

@Local
public interface SubdivisionDAO {
    List<SubdivisionEntity> getList(long organizationId);
    SubdivisionEntity get(long id);
    boolean update(SubdivisionEntity subdivision);
    boolean add(SubdivisionEntity subdivision);
    boolean isValid(SubdivisionEntity subdivision);
    boolean remove(long id);
}
