package com.notification;

public class PushNotification extends Notification {

    public PushNotification(String recipient, String message) {
        super(recipient, message);
    }

    @Override
    public void send() {
        System.out.println("Sending Push Notification to " + recipient + " with message: " + message);
    }
}
