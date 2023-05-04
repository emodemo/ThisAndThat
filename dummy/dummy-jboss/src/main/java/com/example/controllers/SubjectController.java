package com.example.controllers;

import com.example.entity.Subject;
import com.example.repository.SubjectRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/subject")
public class SubjectController {

	@Inject
	private SubjectRepository subjectRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok().entity(subjectRepository.findAll()).build();
	}
}
