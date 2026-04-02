package com.lurdharry.medicationReminder.alert;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailAlertProvider implements AlertProvider {

    private final JavaMailSender mailSender;

    @Value("${alert.from-email}")
    private String fromEmail;

    @Value("${alert.from-name}")
    private String fromName;

    @Override
    public void sendAlert(String toEmail, String subject, String body) {
        System.out.println("Sending email to: " + toEmail);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail, fromName);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}

