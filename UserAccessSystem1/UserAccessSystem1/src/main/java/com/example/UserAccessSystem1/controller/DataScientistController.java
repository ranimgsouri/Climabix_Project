package com.example.UserAccessSystem1.controller;

import com.example.UserAccessSystem1.model.DataScientist;
import com.example.UserAccessSystem1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datascientists")
public class DataScientistController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<DataScientist>> getDataScientists() {
        List<DataScientist> scientists = personRepository.findAll()
                .stream()
                .filter(person -> person instanceof DataScientist)
                .map(person -> (DataScientist) person)
                .toList();
        return ResponseEntity.ok(scientists);
    }

    @PostMapping("/addDataScientist")
    public ResponseEntity<String> addDataScientist(@RequestBody DataScientist dataScientist) {
        personRepository.save(dataScientist);
        return ResponseEntity.ok("Data Scientist added successfully: " + dataScientist.getName());
    }
}
