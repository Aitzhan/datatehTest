package com.aitzhan.datatehTest.dao.Impl;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.dao.OrganizationDAO;
import com.aitzhan.datatehTest.model.OrganizationEntity;
import com.aitzhan.datatehTest.model.SubdivisionEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class OrganizationDAOImpl implements OrganizationDAO {
    public OrganizationEntity getOrganizationById(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        OrganizationEntity organization = (OrganizationEntity) session.createQuery(
                "select organization from OrganizationEntity organization " +
                "where organizationId = id")
                .list().get(0);
        session.close();
        return organization;
    }
}
