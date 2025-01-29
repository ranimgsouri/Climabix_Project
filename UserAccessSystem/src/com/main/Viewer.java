package com.main;

public class Viewer extends Person {

    public Viewer(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    public void accessComponent(String component) {
        if (component.equalsIgnoreCase("VisualizationTool")) {
            System.out.println("Viewer " + getName() + " accessed the " + component + ".");
        } else {
            System.out.println("Viewer " + getName() + " does not have access to " + component + ".");
        }
    }
}
