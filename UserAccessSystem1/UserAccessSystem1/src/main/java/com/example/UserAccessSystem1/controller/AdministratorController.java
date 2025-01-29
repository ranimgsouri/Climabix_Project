package com.example.UserAccessSystem1.controller;

import com.example.UserAccessSystem1.model.Administrator;
import com.example.UserAccessSystem1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdministratorController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<Administrator>> getAdministrators() {
        List<Administrator> admins = personRepository.findAll()
                .stream()
                .filter(person -> person instanceof Administrator)
                .map(person -> (Administrator) person)
                .toList();
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<String> addAdministrator(@RequestBody Administrator admin) {
        personRepository.save(admin);
        return ResponseEntity.ok("Administrator added successfully: " + admin.getName());
    }
}
