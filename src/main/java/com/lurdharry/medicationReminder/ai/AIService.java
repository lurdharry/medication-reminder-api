package com.lurdharry.medicationReminder.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lurdharry.medicationReminder.ai.dto.AIIntent;
import com.lurdharry.medicationReminder.ai.dto.ChatRequest;
import com.lurdharry.medicationReminder.ai.dto.ChatResponse;
import com.lurdharry.medicationReminder.ai.provider.LLMProvider;
import com.lurdharry.medicationReminder.medication.MedicationRepository;
import com.lurdharry.medicationReminder.medication.model.Medication;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AIService {

    private final LLMProvider llmProvider;
    private final MedicationRepository medicationRepository;
    private final ObjectMapper objectMapper;

    public ChatResponse chat(ChatRequest request, User user) {
        var medications = medicationRepository.findByUserId(user.getId());
        String systemPrompt = buildSystemPrompt(user, medications);
        String rawResponse = llmProvider.chat(systemPrompt, request.message());

        try {
            var parsed = objectMapper.readValue(rawResponse, Map.class);
            return ChatResponse.builder()
                    .message((String) parsed.get("message"))
                    .intent(AIIntent.valueOf((String) parsed.get("intent")))
                    .data((Map<String, Object>) parsed.get("data"))
                    .provider(llmProvider.getProviderName())
                    .build();
        } catch (Exception e) {
            return ChatResponse.builder()
                    .message(rawResponse)
                    .intent(AIIntent.GENERAL_QUESTION)
                    .data(null)
                    .provider(llmProvider.getProviderName())
                    .build();
        }
    }

    private String buildSystemPrompt(User user, List<Medication> medications) {
        String medList = medications.stream()
                .map(m -> "  - " + m.getName() + " " + m.getDosage() + m.getUnit() + " (" + m.getPurpose() + ")")
                .collect(Collectors.joining("\n"));

        return """
        You are MediCare AI, a compassionate medication assistant for elderly users.
        
        RULES:
        - Short, clear sentences (max 20 words)
        - Simple words, no medical jargon
        - Warm, encouraging tone
        - Never provide medical advice - refer to doctors
        - Prioritize user safety
        
        CONTEXT:
        - User: %s
        - Medications:
        %s
        
        IMPORTANT: Always respond in this exact JSON format and nothing else:
        {
            "message": "your response to the user",
            "intent": "one of the intents listed below",
            "data": { structured data based on intent }
        }
        
        INTENT AND DATA RULES:
        - MARK_TAKEN: data = {"medicationName": "name"}
        - SKIP_DOSE: data = {"medicationName": "name"}
        - QUERY_SCHEDULE: data = {"medications": [{"name": "x", "dosage": "x", "times": ["08:00"]}]}
        - QUERY_INTERACTIONS: data = {"medications": ["name1", "name2"]}
        - QUERY_MEDICATION_INFO: data = {"medicationName": "name"}
        - GET_ADHERENCE_REPORT: data = null
        - ADD_MEDICATION: data = {"name": "x", "dosage": "x", "unit": "mg/ml/pills"}
        - GREETING: data = null
        - GENERAL_QUESTION: data = null
        - CASUAL_CHAT: data = null
        - REPORT_SIDE_EFFECT: data = {"medicationName": "name", "sideEffect": "description"}
        - REQUEST_HELP: data = null
        - ERROR: data = null
        
        Respond ONLY with valid JSON. No extra text before or after the JSON.
        """.formatted(user.getName(), medList);
    }

}

