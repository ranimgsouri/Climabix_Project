package com.example.UserAccessSystem1.model;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends Person {
    private String department;

    public Administrator() {
    }

    public Administrator(String name, String email, String department) {
        super(name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
