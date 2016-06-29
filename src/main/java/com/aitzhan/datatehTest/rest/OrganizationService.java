package com.aitzhan.datatehTest.rest;

import com.aitzhan.datatehTest.logic.OrganizationStructure;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/organization")
public class OrganizationService {
    @EJB
    OrganizationStructure organizationStructure;

    @GET
    @Path("/{id}")
    public Response getOrganization(@PathParam("id") long id) {
        String organizationJSON = organizationStructure.getOrganizationJSON(id);

        return Response.status(200).entity(organizationJSON).build();
    }

    @GET
    @Path("/{id}/subdivisions")
    public Response getSubdivisions(@PathParam("id") long organizationId) throws SQLException {
        String subdivisionsJSON = organizationStructure.getSubdivisionsListJSON(organizationId);

        return Response.status(200).entity(subdivisionsJSON).build();
    }
}
