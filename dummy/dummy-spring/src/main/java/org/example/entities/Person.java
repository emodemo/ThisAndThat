package org.example.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.example.services.ExistingEntity;
import org.example.services.ExistingEntityValidation;
import org.example.services.NewEntity;
import org.example.services.NewEntityValidation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@ExistingEntityValidation(groups = {ExistingEntity.class})
@NewEntityValidation(groups = {NewEntity.class}, thisClass = Person.class)
@Entity
@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotBlank
    private String name;

    @ElementCollection
    private final List<Grade> grades = new ArrayList<>();

    protected Person() {
        // Required by Hibernate
    }

    public Person(String name) {
        this.name = name;
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }

    public List<Grade> getGrades() {
        return unmodifiableList(this.grades);
    }
}
