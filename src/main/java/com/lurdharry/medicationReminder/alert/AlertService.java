package com.lurdharry.medicationReminder.alert;

import com.lurdharry.medicationReminder.emergencycontact.EmergencyContactRepository;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertProvider alertProvider;
    private final EmergencyContactRepository contactRepository;

    public void notifyEmergencyContacts(User user, String medicationName, int missedCount) {
        var contacts = contactRepository.findByUserIdAndNotifyOnMissedDoseTrue(user.getId());

        for (var contact : contacts) {
            if (contact.getEmail() == null) continue;

            String subject = "MediRemind Alert: Missed Medication";
            String body = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
                    <div style="background-color: #e74c3c; padding: 20px; border-radius: 10px 10px 0 0; text-align: center;">
                        <h1 style="color: white; margin: 0; font-size: 24px;">⚠️ Medication Alert</h1>
                    </div>
                    <div style="background-color: #ffffff; padding: 30px; border: 1px solid #e0e0e0;">
                        <p style="font-size: 16px; color: #333;">Dear <strong>%s</strong>,</p>
                        <p style="font-size: 16px; color: #333;">
                            We're reaching out because <strong>%s</strong> has missed 
                            <strong style="color: #e74c3c;">%d dose(s)</strong> of 
                            <strong>%s</strong> in the last 24 hours.
                        </p>
                        <div style="background-color: #fdf2f2; border-left: 4px solid #e74c3c; padding: 15px; margin: 20px 0; border-radius: 4px;">
                            <p style="margin: 0; font-size: 14px; color: #555;">
                                <strong>Medication:</strong> %s<br>
                                <strong>Missed Doses:</strong> %d<br>
                                <strong>Last Checked:</strong> %s
                            </p>
                        </div>
                        <p style="font-size: 16px; color: #333;">
                            Please check on them when you can to ensure they are okay.
                        </p>
                        <div style="text-align: center; margin-top: 30px;">
                            <a href="#" style="background-color: #3498db; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px; font-size: 16px;">
                                Open MediRemind
                            </a>
                        </div>
                    </div>
                    <div style="background-color: #f8f8f8; padding: 15px; border-radius: 0 0 10px 10px; text-align: center; border: 1px solid #e0e0e0; border-top: none;">
                        <p style="margin: 0; font-size: 12px; color: #999;">
                            This is an automated alert from MediRemind.<br>
                            Please do not reply to this email.
                        </p>
                    </div>
                </div>
                """.formatted(
                                contact.getName(),
                                user.getName(),
                                missedCount,
                                medicationName,
                                medicationName,
                                missedCount,
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a"))
                        );

            alertProvider.sendAlert(contact.getEmail(), subject, body);
        }
    }
}

