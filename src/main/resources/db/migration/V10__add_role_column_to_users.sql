ALTER TABLE users ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'USER';

INSERT INTO users (username, email, password, role, created_at)
VALUES('admin', 'admin@example.com', '$2a$12$MpOdE5hx4OfMsarBjyVChen9BjX2ZUvscCdbp7IfF4gZSLtg9Hin2', 'ADMIN', CURRENT_TIMESTAMP);