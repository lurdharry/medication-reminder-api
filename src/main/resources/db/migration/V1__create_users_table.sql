CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users(
      id  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
      name    VARCHAR(225) NOT NULL,
      email  VARCHAR(225) UNIQUE NOT NULL,
      date_of_birth  DATE,
      password_hash VARCHAR(225) NOT NULL,
      created_at  TIMESTAMP NOT NULL,
      updated_at  TIMESTAMP
);