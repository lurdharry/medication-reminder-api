package com.lurdharry.medicationReminder.ai.provider;

import com.lurdharry.medicationReminder.ai.model.ConversationMessage;

import java.util.List;

public interface LLMProvider {
    String chat(String systemPrompt, List<ConversationMessage> messages);
    String getProviderName();
}

