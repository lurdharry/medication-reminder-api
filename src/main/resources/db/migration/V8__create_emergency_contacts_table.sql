CREATE TABLE emergency_contacts (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
        user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
        name VARCHAR(225) NOT NULL,
        relationship VARCHAR(100) NOT NULL,
        phone VARCHAR(50),
        email VARCHAR(225),
        is_primary BOOLEAN DEFAULT false,
        notify_on_missed_dose BOOLEAN DEFAULT false,
        created_at TIMESTAMP NOT NULL,
        updated_at TIMESTAMP
);

CREATE INDEX idx_emergency_contacts_user
    ON emergency_contacts (user_id);
