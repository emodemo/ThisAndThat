package com.example.controllers;

import com.example.dto.PersonDto;
import com.example.dto.SubjectDto;
import com.example.entity.Grade;
import com.example.entity.Person;
import com.example.entity.Subject;
import com.example.exception.PersonNotFoundException;
import com.example.repository.PersonRepository;
import com.example.repository.SubjectRepository;
import com.example.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
//import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;

@Stateless
@Path("people")
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PersonController {

//	@Inject private PersonRepository personRepository;
//	@Inject private SubjectRepository subjectRepository;
//	@Inject private EmailService emailService;

	private final PersonRepository personRepository;
	private final SubjectRepository subjectRepository;
	private final EmailService emailService;

	public PersonController() {
		this(null, null, null);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPeople(
			@FormParam("page") @DefaultValue("0") int page,
			@FormParam("size") @DefaultValue("20") int size
	) {

		return Response.ok().entity(personRepository.findAll()).build();

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerson(@PathParam("id") long id) {
		Person person = personRepository.find(id);
		if(person == null) throw new PersonNotFoundException(id);
		return Response.ok().entity(person).build();
	}

	@GET
	@Path("{id}/grades")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonGrades(@PathParam("id") long id) {
		Person person = personRepository.find(id);
		if( person == null ) throw new PersonNotFoundException(id);
		return Response.ok().entity(person.getGrades()).build();
	}


	@POST
	@Path("grades")
	@Consumes(MULTIPART_FORM_DATA)
	public void bulkGradesChange(@MultipartForm MultipartBody file) { // MultipartFormDataInput input

		List<PersonDto> data = parseData(file);
		for(PersonDto personDto : data){
			updateStudentGrades(personDto);
			// return message with students ids, that were not updated
		}
		emailService.send("admin@my-system.com", "File upload successfully processed.");
	}

	private void updateStudentGrades(PersonDto personDto){
		Person person = personRepository.find(personDto.getId());
		if(person == null) throw new PersonNotFoundException(personDto.getId());
		// TODO: handle the exception

		Map<Long, SubjectDto> dtoSubjects = personDto.getSubjects();
		List<Subject> subjects = new ArrayList<>();
		for(Long id : dtoSubjects.keySet()){
			Subject subject = subjectRepository.find(id);
			if(subject != null) subjects.add(subject);

		}
		for(Subject subject : subjects){
			double grade = dtoSubjects.get(subject.getId()).getGrade();
			person.addGrade(new Grade(subject, grade));
		}

		personRepository.edit(person);
	}

	private List<PersonDto> parseData(MultipartBody file) {
		Map<Long, PersonDto> result = new HashMap<>();

		String content = new String(file.getData(), Charset.defaultCharset());
		String[] lines = content.split("\\n");

		for(int i = 1; i < lines.length; i++){
			String[] pieces = lines[i].split(",");
			// TODO: validate lines a) interrupt on error, or return message with erroneous entries ??
			long personId = parseLong(pieces[0]);
			long subjectId = parseLong(pieces[1]);
			PersonDto person = getOrPutDefault(result, personId, new PersonDto(personId));
			person.getSubjects().put(subjectId, new SubjectDto(subjectId, parseDouble(pieces[2])));
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
