package com.lurdharry.medicationReminder.ai.provider;

public interface LLMProvider {
    String chat(String systemPrompt, String userMessage);
    String getProviderName();
}

