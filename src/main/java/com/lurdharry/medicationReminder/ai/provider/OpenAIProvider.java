package com.lurdharry.medicationReminder.ai.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lurdharry.medicationReminder.ai.model.ConversationMessage;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.*;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;

import java.util.List;

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
    public String chat(String systemPrompt, List<ConversationMessage> messages) {
        var params = ChatCompletionCreateParams.builder()
                .model(model)
                .addMessage(ChatCompletionSystemMessageParam.builder()
                        .content(systemPrompt)
                        .build());

        for (var msg : messages) {
            if ("user".equals(msg.getRole())) {
                params.addMessage(ChatCompletionUserMessageParam.builder()
                        .content(msg.getContent())
                        .build());
            } else if ("assistant".equals(msg.getRole())) {
                params.addMessage(ChatCompletionAssistantMessageParam.builder()
                        .content(msg.getContent())
                        .build());
            }
        }

        ChatCompletion completion = client.chat().completions().create(params.build());
        return completion.choices().get(0).message().content().orElse("");
    }

    @Override
    public String getProviderName() {
        return "openai";
    }
}
