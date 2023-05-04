package org.example.controllers;

import lombok.Getter;
import lombok.Setter;
import org.example.controllers.api.Filter;
import org.example.controllers.api.FilterPredicate;
import org.example.controllers.api.Filtered;
import org.example.controllers.api.FilteredType;
import org.example.dto.PersonDto;
import org.example.dto.SubjectDto;
import org.example.entities.Grade;
import org.example.entities.Person;
import org.example.entities.Subject;
import org.example.exceptions.PersonNotFoundException;
import org.example.repositories.PersonRepository;
import org.example.repositories.SubjectRepository;
import org.example.services.EmailService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

@RestController
@RequestMapping("people")
public class PersonController {

    private final PersonRepository personRepository;
    private final SubjectRepository subjectRepository;
    private final EmailService emailService;

    public PersonController(PersonRepository personRepository, SubjectRepository subjectRepository, EmailService emailService) {
        this.personRepository = personRepository;
        this.subjectRepository = subjectRepository;
        this.emailService = emailService;
    }

    @GetMapping
    public Iterable<Person> getPeople(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {

        PageRequest pageRequest = PageRequest.of(page, size);

        return personRepository.findAll(pageRequest);
    }

    @GetMapping("v2")
    public Iterable<Person> getPeople2(Pageable pageable) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @GetMapping("v3") // sort=name,asc&sort=id,asc, sort=name,id,asc
    public Iterable<Person> getPeople3(Pageable pageable, PeopleFilter filter) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @GetMapping("v3a") // sort=name,asc&sort=id,asc, sort=name,id,asc
    public Iterable<Person> getPeople3a(Pageable pageable, PeopleFilter filter) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
       // FilterPredicate<Person, PeopleFilter> criteria = new FilterPredicate<>(filter);
        return personRepository.findAll(filter.toPredicate(), pageRequest);
    }

    @Getter @Setter
    static class PeopleFilter implements Filter<Person, PeopleFilter> {
        private Long id;
        private String name;
    }

    @GetMapping("v4")
    public Iterable<Person> getPeople4(Pageable pageable, PeopleFilter4 filter) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @Getter @Setter
    class PeopleFilter4 {
        FilteredType<Integer> id;
        FilteredType<String> name;
    }

    @GetMapping("v5") // /v5?name=Aaron,Geroge, /v5?name=Aaron$names=Geroge
    public Iterable<Person> getPeople5(Pageable pageable, @RequestParam List<String> name) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @GetMapping("v6") // /v5?names=Aaron,Geroge, /v5?names=Aaron$names=Geroge
    public Iterable<Person> getPeople6(Pageable pageable, Names name) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @Getter @Setter
    class Names {
        List<String> names;
    }

    @GetMapping("v7") // localhost:8000/api/people/v7?name=Aaron&name=Maaron&pageNumber=1&sort=name,asc&sort=id,asc
    public Iterable<Person> getPeople7(Pageable pageable, @RequestParam MultiValueMap<String, String> map) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @GetMapping("v8")
    public Iterable<Person> getPeople8(Pageable pageable, PeopleFilter8 filter) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        return personRepository.findAll(pageRequest);
    }

    @Getter @Setter
    class PeopleFilter8{
        private Map<String, String> values;

        public PeopleFilter8() {
        }

        public PeopleFilter8(String str) {
            String[] split = str.split(",");
            values = new HashMap<>();
            values.put(split[0], split[1]);

        }
    }


    @GetMapping("{id}")
    public Person getPerson(@PathVariable long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @GetMapping("{id}/grades")
    public List<Grade> getPersonGrades(@PathVariable long id) {
        return personRepository.findById(id)
                .map(Person::getGrades)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }


    @PostMapping("grades")
    public void bulkGradesChange(@RequestParam("grades") MultipartFile file) {

        List<PersonDto> data = parseData(file);
        for(PersonDto personDto : data){
            updateStudentGrades(personDto);
            // return message with students ids, that were not updated
        }
        emailService.send("admin@my-system.com", "File upload successfully processed.");
    }

    private void updateStudentGrades(PersonDto personDto){
        Person person = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new PersonNotFoundException(personDto.getId()));
        // TODO: handle the exception

        Map<Long, SubjectDto> dtoSubjects = personDto.getSubjects();
        Iterable<Subject> subjects = subjectRepository.findAllById(dtoSubjects.keySet());

        for(Subject subject : subjects){
            double grade = dtoSubjects.get(subject.getId()).getGrade();
            person.addGrade(new Grade(subject, grade));
        }

        personRepository.save(person);
    }

    private List<PersonDto> parseData(MultipartFile file) {
        Map<Long, PersonDto> result = new HashMap<>();

        try {
            String content = new String(file.getBytes(), Charset.defaultCharset());
            String[] lines = content.split("\\n");

            for(int i = 1; i < lines.length; i++){
                String[] pieces = lines[i].split(",");
                // TODO: validate lines a) interrupt on error, or return message with erroneous entries ??
                long personId = parseLong(pieces[0]);
                long subjectId = parseLong(pieces[1]);
                PersonDto person = getOrPutDefault(result, personId, new PersonDto(personId));
                person.getSubjects().put(subjectId, new SubjectDto(subjectId, parseDouble(pieces[2])));
            }
        } catch (IOException e) {
            // TODO: log me
        }
        return new ArrayList<>(result.values());
    }

    private <K, V> V getOrPutDefault(Map<K, V> map, K key, V newValue){

        V oldValue = map.get(key);
        if(oldValue == null){
            map.put(key, newValue);
            return newValue;
        }
        return oldValue;
    }
}
