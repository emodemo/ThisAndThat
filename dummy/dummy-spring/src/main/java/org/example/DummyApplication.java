package org.example;

import org.example.entities.Person;
import org.example.entities.Subject;
import org.example.repositories.PersonRepository;
import org.example.repositories.SubjectRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static java.util.Arrays.asList;

@SpringBootApplication
@ConfigurationPropertiesScan // added for @ConfigurationProperties
public class DummyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DummyApplication.class, args);
    }

    @Bean
    ApplicationRunner seedData(SubjectRepository subjectRepository, PersonRepository personRepository) {
        return args -> {

            subjectRepository.save(new Subject("Geography"));
            subjectRepository.save(new Subject("Mathematics"));

            List<Person> people = asList(
                    new Person("Peter"),
                    new Person("George"),
                    new Person("Steven"),
                    new Person("John"),
                    new Person("Peter"),
                    new Person("Aaron")
            );

            personRepository.saveAll(people);
        };
    }
}
