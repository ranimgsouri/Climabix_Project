package com.example.UserAccessSystem1.controller;

import com.example.UserAccessSystem1.model.Person;
import com.example.UserAccessSystem1.service.PersonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/people")
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @PostMapping("/addPerson")
    public void addPerson(@RequestBody Person person) {
        personService.savePerson(person);
    }
    
    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        try {
            personService.deletePersonById(id);
            return ResponseEntity.ok("Person with ID " + id + " has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Person with ID " + id + " not found.");
        }
    }

}
