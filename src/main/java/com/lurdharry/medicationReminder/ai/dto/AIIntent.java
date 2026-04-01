package com.lurdharry.medicationReminder.ai.dto;

public enum AIIntent {
    // Medication management
    ADD_MEDICATION,
    UPDATE_MEDICATION,
    DELETE_MEDICATION,

    // Dose tracking
    MARK_TAKEN,
    SKIP_DOSE,
    SNOOZE_REMINDER,

    // Queries
    QUERY_SCHEDULE,
    QUERY_INTERACTIONS,
    QUERY_SIDE_EFFECTS,
    QUERY_MEDICATION_INFO,

    // Analytics
    GET_ADHERENCE_REPORT,
    GET_INSIGHTS,

    // Emergency
    REPORT_SIDE_EFFECT,
    REQUEST_HELP,

    // Conversational
    GREETING,
    GENERAL_QUESTION,
    CASUAL_CHAT,
    ERROR
}
