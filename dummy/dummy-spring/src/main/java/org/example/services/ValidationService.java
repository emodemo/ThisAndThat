package org.example.services;

import jakarta.validation.Valid;
import org.example.entities.Person;
import org.example.job.DummyScheduler;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated(value = NewEntity.class)
@Service
public class ValidationService {

    @Validated(value = ExistingEntity.class)
    public void validateMe(@Valid Whatever whatever){
        whatever.getSomething();
    }

    public void validateMe(@Valid Person person){
        Long id = person.getId();
        System.out.println(id);
    }
}
