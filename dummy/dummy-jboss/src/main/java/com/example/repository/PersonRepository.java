package com.example.repository;

import com.example.entity.Person;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersonRepository extends Facade<Person> {

	@PersistenceContext(name = "punit")
	protected EntityManager entityManager;

	public PersonRepository() {
		super(Person.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
