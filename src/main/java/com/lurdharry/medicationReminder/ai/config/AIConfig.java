package com.lurdharry.medicationReminder.ai.config;


import com.lurdharry.medicationReminder.ai.provider.LLMProvider;
import com.lurdharry.medicationReminder.ai.provider.OpenAIProvider;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public LLMProvider llmProvider(
            @Value("${ai.provider}") String provider,
            @Value("${ai.openai.api-key}") String openaiKey,
            @Value("${ai.openai.model}") String openaiModel
    ) {
        return switch (provider) {
            case "openai" -> new OpenAIProvider(openaiKey, openaiModel);
            default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };
    }
}
