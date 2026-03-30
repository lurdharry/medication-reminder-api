CREATE TABLE dose_records(
    id UUID primary key DEFAULT gen_random_uuid(),
    dose_schedule_id  UUID NOT NULL REFERENCES dose_schedules(id) ON DELETE CASCADE,
    medication_id UUID NOT NULL REFERENCES medications(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    scheduled_at TIMESTAMP,
    status VARCHAR(10) NOT NULL CHECK (status IN ('taken', 'skipped', 'missed')),
    recorded_at TIMESTAMP,
    created_at TIMESTAMP
)