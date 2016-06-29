package com.aitzhan.datatehTest.dao;

import com.aitzhan.datatehTest.model.OrganizationEntity;
import com.aitzhan.datatehTest.model.SubdivisionEntity;

import javax.ejb.Local;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Aitzhan on 02.06.2016.
 */
@Local
public interface OrganizationDAO {
    OrganizationEntity getOrganizationById(long id);
}
