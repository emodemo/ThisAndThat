package com.example.controllers;

import com.example.services.SyncAsyncService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Stateless
@Path("sync")
public class SyncAsyncController {

	@Inject
	private SyncAsyncService myService;

	@GET
	@Path("/sync/{id}")
	public Response sync(@FormParam("id")int i) {
		myService.printAfter("message in a bottle");
		return Response.ok().entity(i^2).build();
	}


	@GET
	@Path("/async/{id}")
	public Response async(@FormParam("id")int i) {
		myService.printAsync("message in a bottle");
		return Response.ok().entity(i^2).build();
	}


}
