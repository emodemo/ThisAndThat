package org.example.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.entities.Person;
import org.example.services.ValidationService;
import org.example.services.Whatever;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("validation")
public class ValidationController {

    private final ValidationService validationService;
    private final ObjectMapper objectMapper;

    @GetMapping("/whatever") // test validation annotation
    public JsonNode customTest(){
        Whatever whatever = new Whatever.WhateverImpl("aaa", "bbb");
        validationService.validateMe(whatever);
        return objectMapper.convertValue(whatever, JsonNode.class);
    }

    @GetMapping("/whatever/{id}") // test validation annotation
    public JsonNode customTest2(@PathParam("id") Long id){
        Person person = new Person("George");
        validationService.validateMe(person);
        return objectMapper.convertValue(person, JsonNode.class);
    }
}
