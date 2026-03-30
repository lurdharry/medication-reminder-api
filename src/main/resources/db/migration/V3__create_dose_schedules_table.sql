CREATE TABLE dose_schedules(
    id UUID primary key default gen_random_uuid(),
    medication_id UUID NOT NULL REFERENCES medications(id) ON DELETE CASCADE,
    time TIME,
    created_at DATE NOT NULL
)