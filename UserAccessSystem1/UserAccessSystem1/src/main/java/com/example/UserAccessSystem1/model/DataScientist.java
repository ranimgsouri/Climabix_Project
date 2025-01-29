package com.example.UserAccessSystem1.model;

import jakarta.persistence.Entity;

@Entity
public class DataScientist extends Person {
    private String specialization;

    public DataScientist() {
    }

    public DataScientist(String name, String email, String specialization) {
        super(name, email);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
