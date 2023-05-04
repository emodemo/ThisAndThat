package com.example.entity;

import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

@Entity
@Getter
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
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
