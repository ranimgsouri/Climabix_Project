package com.example.UserAccessSystem1.controller;

import com.example.UserAccessSystem1.model.User;
import com.example.UserAccessSystem1.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = personRepository.findAll()
                .stream()
                .filter(person -> person instanceof User)
                .map(person -> (User) person)
                .toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        personRepository.save(user);
        return ResponseEntity.ok("User added successfully: " + user.getName());
    }
}
