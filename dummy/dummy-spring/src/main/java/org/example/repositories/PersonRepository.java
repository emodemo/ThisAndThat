package org.example.repositories;

import org.example.entities.Person;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}
