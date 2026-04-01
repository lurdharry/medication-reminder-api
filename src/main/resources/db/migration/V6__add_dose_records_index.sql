CREATE INDEX idx_dose_records_user_scheduled
    ON dose_records (user_id, scheduled_at);