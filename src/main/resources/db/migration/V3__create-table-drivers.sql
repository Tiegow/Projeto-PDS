CREATE TABLE drivers (
                         id SERIAL PRIMARY KEY,
                         broadcast_name VARCHAR(255),
                         country_code VARCHAR(10),
                         driver_number INTEGER,
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         headshot_url TEXT,
                         meeting_key INTEGER,
                         name_acronym VARCHAR(10),
                         session_key INTEGER,
                         team_colour VARCHAR(50),
                         team_name VARCHAR(100),
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_driver_driver_number ON drivers(driver_number);

CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_driver_updated_at
    BEFORE UPDATE ON drivers
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();