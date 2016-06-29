package com.aitzhan.datatehTest.rest;

import com.aitzhan.datatehTest.logic.OrderBean;
import com.aitzhan.datatehTest.logic.OrderStateMachine.OrderStateContext;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Session;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("order")
public class OrderService {
    @EJB
    OrderBean orderBean;

    @GET
    public Response getAllOrders() {
        return Response.status(200).entity(orderBean.getAllOrdersJSON()).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") long id) {
        return Response.status(200).entity(orderBean.get(id)).build();
    }

    @POST
    @Consumes("application/json")
    public Response storeOrder(String orderJSON) {
        boolean success = orderBean.add(orderJSON);
        if (success) return Response.status(200).build();
        else return Response.status(500).build();
    }


    @PUT
    @Consumes("application/json")
    public Response updateOrder(String orderJSON) {
        boolean success = orderBean.update(orderJSON);
        if (success) return Response.status(200).build();
        else return Response.status(500).build();
    }

    @GET
    @Path("/{id}/my")
    public Response getMyOrders(@PathParam("id") long id) {
        return  Response.status(200).entity(orderBean.getMyOrdersJSON(id)).build();
    }

    @GET
    @Path("/{id}/tome")
    public Response getOrdersToMe(@PathParam("id") long id) {
        return Response.status(200).entity(orderBean.getOrdersToMeJSON(id)).build();
    }

    @GET
    @Path("/{id}/sendToControl")
    public Response sendToControl(@PathParam("id") long id) {
        OrderStateContext orderStateContext = new OrderStateContext(id);
        orderStateContext.sendToControl();
        return Response.status(200).build();
    }

    @GET
    @Path("/{id}/sendToRework")
    public Response sendToRework(@PathParam("id") long id) {
        OrderStateContext orderStateContext = new OrderStateContext(id);
        orderStateContext.sendToRework();
        return Response.status(200).build();
    }

    @GET
    @Path("/{id}/accept")
    public Response accept(@PathParam("id") long id) {
        OrderStateContext orderStateContext = new OrderStateContext(id);
        orderStateContext.accept();
        return Response.status(200).build();
    }
}