package com.lurdharry.medicationReminder.ai;


import com.lurdharry.medicationReminder.ai.dto.ConversationMessageResponse;
import com.lurdharry.medicationReminder.ai.model.ConversationMessage;
import org.springframework.stereotype.Component;

@Component
public class ConversationMessageMapper {

    public ConversationMessageResponse toResponse(ConversationMessage message) {
        return ConversationMessageResponse.builder()
                .id(message.getId())
                .role(message.getRole())
                .content(message.getContent())
                .intent(message.getIntent())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
