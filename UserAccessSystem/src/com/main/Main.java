package com.main;

import com.notification.NotificationManager;

public class Main {
    public static void main(String[] args) {
        // Create user instances
        Person admin = new Administrator("1", "Anis", "Anis@gmail.com");
        Person user = new User("2", "Imen", "imen@gmail.com");
        Person dataScientist = new DataScientist("3", "Arij", "Arij@gmail.com");
        Person viewer = new Viewer("4", "Mohamed", "mohamed@gmail.com");

        // Notifications
        System.out.println("----- Notifications -----");

        // Send email to admin
        NotificationManager.notifyUser(admin, "System maintenance scheduled.", "email");

        // Send SMS to user
        NotificationManager.notifyUser(user, "Your access to the Visualization Tool has been granted.", "sms");

        // Send push notification to data scientist
        NotificationManager.notifyUser(dataScientist, "Data analysis task has been completed.", "push");

        // Send push notification to viewer
        NotificationManager.notifyUser(viewer, "New visualization report is ready.", "push");

        System.out.println("----- Access Control -----");

        // Access control demonstration
        admin.login();
        admin.accessComponent("Data Cleaner");
        admin.accessComponent("VisualizationTool");
        admin.logout();

        user.login();
        user.accessComponent("VisualizationTool");
        user.accessComponent("Regression Analysis");
        user.logout();

        dataScientist.login();
        dataScientist.accessComponent("Data Cleaner");
        dataScientist.accessComponent("Data Ingestion");
        dataScientist.logout();

        viewer.login();
        viewer.accessComponent("VisualizationTool");
        viewer.accessComponent("Data Cleaner");
        viewer.logout();
    }
}
