package com.github.cwilper.fcrepo.cloudsync.service.rest;

import com.github.cwilper.fcrepo.cloudsync.api.CloudSyncService;
import com.github.cwilper.fcrepo.cloudsync.api.Task;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("tasks")
public class TaskResource extends AbstractResource {

    public TaskResource(CloudSyncService service) {
        super(service);
    }

    @POST
    @Path("/")
    @Consumes({XML, JSON})
    @Descriptions({
        @Description(value = "Creates a task", target = DocTarget.METHOD),
        @Description(value = STATUS_201_CREATED, target = DocTarget.RESPONSE)
    })
    public Response createTask(@Context UriInfo uriInfo,
                               Task task) {
        String id = service.createTask(task);
        URI uri = getResourceURI(uriInfo.getRequestUri(), id);
        return Response.created(uri).build();
    }

    @GET
    @Path("/")
    @Produces({XML, JSON})
    @Descriptions({
        @Description(value = "Lists all tasks", target = DocTarget.METHOD),
        @Description(value = STATUS_200_OK, target = DocTarget.RESPONSE)
    })
    public Tasks listTasks() {
        Tasks list = new Tasks();
        list.setTask(service.listTasks());
        return list;
    }

    @GET
    @Path("{id}")
    @Produces({XML, JSON})
    @Descriptions({
        @Description(value = "Gets a task", target = DocTarget.METHOD),
        @Description(value = STATUS_200_OK, target = DocTarget.RESPONSE)
    })
    public Task getTask(@PathParam("id") String id) {
        return service.getTask(id);
    }

    @PUT
    @Path("{id}")
    @Consumes({XML, JSON})
    @Produces({XML, JSON})
    @Descriptions({
        @Description(value = "Updates a task", target = DocTarget.METHOD),
        @Description(value = STATUS_200_OK, target = DocTarget.RESPONSE)
    })
    public Task updateTask(@PathParam("id") String id,
                           Task task) {
        return service.updateTask(id, task);
    }

    @DELETE
    @Path("{id}")
    @Descriptions({
        @Description(value = "Deletes a task", target = DocTarget.METHOD),
        @Description(value = STATUS_204_NO_CONTENT, target = DocTarget.RESPONSE)
    })
    public void deleteTask(@PathParam("id") String id) {
        service.deleteTask(id);
    }

}