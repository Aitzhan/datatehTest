package com.aitzhan.datatehTest.rest;

import com.aitzhan.datatehTest.logic.AllEmployees;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("employee")
public class EmployeeService {
    @EJB
    AllEmployees allEmployees;

    @GET
    public Response getAllEmployees() {
        String allEmployeesJSON = allEmployees.getAllInJSON();

        return Response.status(200).entity(allEmployeesJSON).build();
    }

    @GET
    @Path("/{id}")
    public Response getEmployee(@PathParam("id") long id){
        String employeeJSON = allEmployees.getInJSON(id);

        return Response.status(200).entity(employeeJSON).build();
    }

    @PUT
    @Consumes("application/json")
    public Response updateEmployee(String employeeJSON) {
        boolean success = allEmployees.update(employeeJSON);

        if (success) return Response.status(200).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes("application/json")
    public Response createEmployee(String employeeJSON) {
        boolean success = allEmployees.add(employeeJSON);

        if (success) return Response.status(200).build();
        else return Response.status(500).build();
    }
}
