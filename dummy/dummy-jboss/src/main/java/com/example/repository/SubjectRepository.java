package com.example.repository;

import com.example.entity.Subject;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SubjectRepository extends Facade<Subject> {

	@PersistenceContext(name = "punit")
	protected EntityManager entityManager;

	public SubjectRepository() {
		super(Subject.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
