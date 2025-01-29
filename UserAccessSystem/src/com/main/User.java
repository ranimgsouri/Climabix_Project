package com.main;

public class User extends Person {

    public User(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void accessComponent(String component) {
        if (component.equalsIgnoreCase("VisualizationTool")) {
            System.out.println("User " + getName() + " accessed the " + component + ".");
        } else {
            System.out.println("User " + getName() + " does not have access to " + component + ".");
        }
    }
}
