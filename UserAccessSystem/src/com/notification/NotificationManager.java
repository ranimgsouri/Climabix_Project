package com.notification;

import com.main.Person;

public class NotificationManager {
    public static void sendNotification(Notification notification) {
        notification.send(); // Polymorphic behavior
    }

    public static void notifyUser(Person person, String message, String type) {
        Notification notification;
        if (type.equalsIgnoreCase("email")) {
            notification = new EmailNotification(person.getEmail(), message);
        } else if (type.equalsIgnoreCase("sms")) {
            notification = new SMSNotification(person.getEmail(), message); // Using email as a placeholder
        } else if (type.equalsIgnoreCase("push")) {
            notification = new PushNotification(person.getEmail(), message);
        } else {
            throw new IllegalArgumentException("Invalid notification type");
        }
        sendNotification(notification);
    }
}
