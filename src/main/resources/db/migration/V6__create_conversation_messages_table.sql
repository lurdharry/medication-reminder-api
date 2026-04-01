CREATE TABLE conversation_messages (
       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
       user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
       role VARCHAR(20) NOT NULL CHECK (role IN ('system', 'user', 'assistant')),
       content TEXT NOT NULL,
       intent VARCHAR(50),
       created_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_conversation_messages_user
ON conversation_messages (user_id, created_at);