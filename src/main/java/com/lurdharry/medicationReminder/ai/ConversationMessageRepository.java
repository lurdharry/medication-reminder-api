package com.lurdharry.medicationReminder.ai;

import com.lurdharry.medicationReminder.ai.model.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConversationMessageRepository extends JpaRepository<ConversationMessage, UUID> {
    List<ConversationMessage> findTop20ByUserIdOrderByCreatedAtDesc(UUID userId);
    List<ConversationMessage> findByUserIdOrderByCreatedAtAsc(UUID userId);
    void deleteByUserId(UUID userId);
}
