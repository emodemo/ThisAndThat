package org.example.entities;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Validate<T, R extends JpaRepository<T, Long>> {

    boolean assertNew(R repository);

    T assertExists(R repository);

}
