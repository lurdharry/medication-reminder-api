package com.lurdharry.medicationReminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MedicationReminderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicationReminderApplication.class, args);
	}

}
