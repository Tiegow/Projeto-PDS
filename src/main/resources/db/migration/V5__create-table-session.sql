CREATE TABLE sessions (
    session_key INTEGER PRIMARY KEY,
    location VARCHAR(255),
    country_key INTEGER,
    country_code VARCHAR(10),
    country_name VARCHAR(255),
    circuit_key INTEGER,
    circuit_short_name VARCHAR(255),
    session_type VARCHAR(100),
    session_name VARCHAR(255),
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    meeting_key INTEGER
);

CREATE INDEX idx_sessions_session_date_end ON sessions(end_date);
