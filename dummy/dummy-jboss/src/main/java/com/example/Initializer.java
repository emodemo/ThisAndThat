package com.example;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class Initializer {

	// works both ways - with field and method
	// https://docs.jboss.org/weld/reference/latest-3.1/en-US/html_single/#_defining_a_resource
//	@Produces
	@PersistenceContext(name = "punit")
	EntityManager entityManager;

	@Produces
	public EntityManager getEntityManager(){
		return entityManager;
	}


}
