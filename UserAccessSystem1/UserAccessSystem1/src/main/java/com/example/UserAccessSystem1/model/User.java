package com.example.UserAccessSystem1.model;

import jakarta.persistence.Entity;

@Entity
public class User extends Person {
    public User(String name, String email) {
        super(name, email);
    }

    public User() {}
}
