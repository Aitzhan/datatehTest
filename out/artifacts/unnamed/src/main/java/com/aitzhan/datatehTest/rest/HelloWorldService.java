package com.aitzhan.datatehTest.rest;

/**
 * Created by Aitzhan on 29.05.2016.
 */
        import javax.ws.rs.GET;
        import javax.ws.rs.Path;
        import javax.ws.rs.PathParam;
        import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldService {

    @GET
    @Path("/{param}")
    public Response getMsg(@PathParam("param") String msg) {

        String output = "Jersey say : " + msg;

        return Response.status(200).entity(output).build();

    }

    @GET
    @Path("/echo")
    public  Response echo() {
        return Response.status(200).entity("Aitzhan").build();
    }

}