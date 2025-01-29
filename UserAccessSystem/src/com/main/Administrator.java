package com.main;

public class Administrator extends Person {

    public Administrator(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void accessComponent(String component) {
        System.out.println("Administrator " + getName() + " accessed the " + component + ".");
    }
}
