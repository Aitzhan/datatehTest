package com.aitzhan.datatehTest.rest;

import com.aitzhan.datatehTest.logic.OrganizationStructure;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("subdivision")
public class SubdivisionService {
    @EJB
    OrganizationStructure organizationStructure;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        String subdivisionJSON = organizationStructure.getSubdivisionJSON(id);

        return Response.status(200).entity(subdivisionJSON).build();
    }

    @PUT
    @Consumes("application/json")
    public Response update(String data) {
        boolean success = organizationStructure.updateSubdivision(data);

        if (success) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }


    @POST
    @Consumes("application/json")
    public Response store(String data) {
        boolean success = organizationStructure.addSubdivision(data);

        if (success) {
            return Response.status(200).build();
        } else {
            return Response.status(500).build();
        }
    }
}
