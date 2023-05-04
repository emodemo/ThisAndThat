package org.example.repositories;

import org.example.entities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long>, CrudRepository<Subject, Long> {
}
