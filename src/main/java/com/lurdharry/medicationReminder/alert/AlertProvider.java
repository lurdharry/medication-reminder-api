package com.lurdharry.medicationReminder.alert;

public interface AlertProvider {
    void sendAlert(String toEmail, String subject, String body);
}
