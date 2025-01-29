package com.example.UserAccessSystem1.service;

import com.example.UserAccessSystem1.model.Person;
import com.example.UserAccessSystem1.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public void savePerson(Person person) {
        personRepository.save(person);
    }
    public void deletePersonById(Long id) {
        personRepository.deleteById(id);
    }

}
