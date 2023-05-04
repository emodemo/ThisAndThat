package com.example.controllers;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Stateless
@Path("health")
public class HealthController {

	@PostConstruct
	public void init(){
		System.out.println(this.getClass().getName() + " was just created");
	}

	@GET
	public Response ping(){
		return Response.ok("ping").build();
	}

	@GET
	@Path("{id}")
	@Produces({APPLICATION_XML, APPLICATION_JSON})
	public Response doubleMe(@FormParam("id")int i){
		return Response.ok().entity(i^2).build();
	}

}
