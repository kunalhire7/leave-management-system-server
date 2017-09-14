package com.lms.resources;

import com.lms.domain.Leave;
import com.lms.services.LmsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/leaves")
public class LeaveResource {

    private LmsService lmsService;

    @Inject
    public LeaveResource(LmsService lmsService) {
        this.lmsService = lmsService;
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response get(@PathParam("id") String id) {
        return Response.ok().entity(lmsService.getById(id)).build();
    }

    @POST
    public Response applyLeave(@Context UriInfo uriInfo, Leave leave) {
        Leave savedLeave = lmsService.save(leave);
        URI uri = uriInfo.getBaseUriBuilder().path("/leaves/" + savedLeave.getId()).build();
        return Response.created(uri).build();
    }

    @GET
    @Produces("application/json")
    public List<Leave> getLeaves() {
        return lmsService.getAll();
    }
}
