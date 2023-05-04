package org.example.controllers;

import org.example.entities.Subject;
import org.example.repositories.SubjectRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("subjects")
public class SubjectController {

    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping
    public Iterable<Subject> getAll() {
        return subjectRepository.findAll();
    }
}
