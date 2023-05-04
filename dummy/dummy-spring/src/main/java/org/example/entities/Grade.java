package org.example.entities;

import jakarta.persistence.Column;
import lombok.Getter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
//@AllArgsConstructors
@Getter
public class Grade {

    @ManyToOne
    private Subject subject;

    @Column(name = "assessment")
    private Double value;

    public Grade(Subject subject, Double value) {
        this.subject = subject;
        this.value = value;
    }

    protected Grade() {
        // Required by Hibernate
    }
}
