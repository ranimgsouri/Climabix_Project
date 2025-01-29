package com.main;

public class DataScientist extends Person {

    public DataScientist(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void accessComponent(String component) {
        if (component.equalsIgnoreCase("Data Cleaner") || component.equalsIgnoreCase("Data Ingestion")) {
            System.out.println("Data Scientist " + getName() + " accessed the " + component + ".");
        } else {
            System.out.println("Data Scientist " + getName() + " does not have access to " + component + ".");
        }
    }
}
