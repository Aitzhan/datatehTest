package com.aitzhan.datatehTest.dao.Impl;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.dao.SubdivisionDAO;
import com.aitzhan.datatehTest.model.SubdivisionEntity;
import com.google.gson.Gson;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class SubdivisionDAOImpl implements SubdivisionDAO{

    public List<SubdivisionEntity> getList(long organizationId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        List<SubdivisionEntity> subdivisions = (List<SubdivisionEntity>) session.createQuery(
                "select subdivision from SubdivisionEntity subdivision where subdivision.organization.id = :organizationId")
                .setParameter("organizationId", organizationId).list();
        session.close();

        return subdivisions;
    }

    public boolean update(SubdivisionEntity subdivision) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        session.beginTransaction();
        session.update(subdivision);
        session.getTransaction().commit();
        session.close();

        return true;
    }

    public SubdivisionEntity get(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        SubdivisionEntity subdivision = (SubdivisionEntity) session.createQuery(
                "select subdivision from SubdivisionEntity subdivision where subdivision.subdivisionId = :subdivisionId")
                .setParameter("subdivisionId", id).list().get(0);

        session.close();

        return subdivision;
    }

    public boolean add(SubdivisionEntity subdivision) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(subdivision);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isValid(SubdivisionEntity subdivision) {
        return subdivision.getTitle() != null && subdivision.getTitle().length() > 0 && subdivision.getTitle().length() <= 20 &&
                subdivision.getContacts() != null && subdivision.getContacts().length() > 0 && subdivision.getContacts().length() <= 20;
    }

    public boolean remove(long id) {
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();

            SubdivisionEntity subdivision = (SubdivisionEntity) session.load(SubdivisionEntity.class, id);
            session.delete(subdivision);
            session.flush();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
