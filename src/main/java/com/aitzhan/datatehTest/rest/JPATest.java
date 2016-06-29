package com.aitzhan.datatehTest.rest;

import com.aitzhan.datatehTest.HibernateSessionFactory;
import com.aitzhan.datatehTest.model.EmployeeEntity;
import com.aitzhan.datatehTest.model.OrderEntity;
import com.aitzhan.datatehTest.model.OrganizationEntity;
import com.aitzhan.datatehTest.model.SubdivisionEntity;
import org.hibernate.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Path("/test")
public class JPATest {

    @GET
    @Path("/create")
    public Response createEmployee() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();

        List<EmployeeEntity> employees = new ArrayList<EmployeeEntity>();


        OrganizationEntity organizationEntity = new OrganizationEntity("Наша компания", "Комсомольская", "В Уфе", null);


        SubdivisionEntity subdivisionEntity = new SubdivisionEntity("Подразделение", "+798332434", null, organizationEntity);


        EmployeeEntity organizationEmployee = new EmployeeEntity("Петр", "Петров", "Петрович", "Руководитель", null);

        session.save(organizationEntity);
        session.save(subdivisionEntity);
        session.save(organizationEmployee);
        organizationEntity.setLeader(organizationEmployee);
        session.update(organizationEntity);

        EmployeeEntity subdivisionLeader = new EmployeeEntity("Александр", "Александров", "Александрович", "Руководитель", null);
        session.save(subdivisionLeader);
        subdivisionEntity.setLeader(subdivisionLeader);
        session.update(subdivisionEntity);

        GregorianCalendar d = new GregorianCalendar(2016, 3, 4);

        for (int i = 0; i < 35; i++) {
            EmployeeEntity myEmployee = new EmployeeEntity("Иван" + i, "Иванов", "Иванович", "Менеджер", subdivisionEntity);
            employees.add(myEmployee);
            session.save(myEmployee);
        }

        for(int i = 0; i < 20; i++) {
            OrderEntity order = new OrderEntity("Отчет за " + i + "-й квартал", employees.get(i), d.getGregorianChange(),
                    "Отчет лежит на столе", employees.get(i + 1), "Отчет охватывает 3 месяца", "Составить отчет за квартал, положить на стол");
            session.save(order);
        }

        session.getTransaction().commit();
        session.close();

        return Response.status(200).entity("JPA Test").build();
    }
}
