CREATE TABLE meetings (
                          meeting_key INTEGER PRIMARY KEY,
                          meeting_name VARCHAR(255),
                          meeting_official_name VARCHAR(255),
                          location VARCHAR(255),
                          country_key INTEGER,
                          country_code VARCHAR(10),
                          country_name VARCHAR(255),
                          circuit_key INTEGER,
                          circuit_short_name VARCHAR(255),
                          start_date TIMESTAMP WITH TIME ZONE,
                          year INTEGER
);

CREATE TABLE sessions (
                          session_key INTEGER PRIMARY KEY,
                          meeting_key INTEGER,
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
                          FOREIGN KEY (meeting_key) REFERENCES meetings(meeting_key)
);

CREATE INDEX idx_sessions_session_date_end ON sessions(end_date);