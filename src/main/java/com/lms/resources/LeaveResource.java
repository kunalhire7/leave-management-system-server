package com.lms.resources;

import com.lms.domain.Leave;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/leaves")
public class LeaveResource {

    @GET
    @Path("/{id}")
    public Leave get(@PathParam("id") String id) {
        return new Leave();
    }
}
