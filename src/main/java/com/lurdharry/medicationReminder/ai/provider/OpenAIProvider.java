package com.lurdharry.medicationReminder.ai.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.*;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;

public class OpenAIProvider implements LLMProvider {

    private final OpenAIClient client;
    private final String model;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenAIProvider(String apiKey, String model) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
        this.model = model;
    }

    @Override
    public String chat(String systemPrompt, String userMessage) {
        ChatCompletion completion = client.chat().completions().create(
                ChatCompletionCreateParams.builder()
                        .model(model)
                        .addMessage(ChatCompletionSystemMessageParam.builder()
                                .content(systemPrompt)
                                .build())
                        .addMessage(ChatCompletionUserMessageParam.builder()
                                .content(userMessage)
                                .build())
                        .build()
        );

        return completion.choices().get(0).message().content().orElse("");
    }

    @Override
    public String getProviderName() {
        return "openai";
    }
}
