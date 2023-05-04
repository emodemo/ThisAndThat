package com.example;

import com.example.entity.Person;
import com.example.entity.Subject;
import com.example.repository.PersonRepository;
import com.example.repository.SubjectRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class DataLoader {

	@Inject PersonRepository personRepository;
	@Inject	SubjectRepository subjectRepository;

	public void onStart(@Observes @Initialized(ApplicationScoped.class) Object pointless){
		subjectRepository.create(new Subject("Geography"));
		subjectRepository.create(new Subject("Mathematics"));

		personRepository.create(new Person("Peter"));
		personRepository.create(new Person("George"));
		personRepository.create(new Person("Steven"));
		personRepository.create(new Person("John"));
		personRepository.create(new Person("Aaron"));
	}

}

//import javax.ejb.Singleton;
//import javax.ejb.Startup;
//@Startup
//@Singleton
//public class InitializerOnStart {
//
//	@PostConstruct
//	public void onStart() { }
//}