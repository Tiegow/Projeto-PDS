CREATE TABLE vehicles (
                      id SERIAL PRIMARY KEY,
                      brake INTEGER,
                      date TIMESTAMP,
                      driver_number INTEGER,
                      drs INTEGER,
                      meeting_key INTEGER NOT NULL,
                      gear INTEGER,
                      rpm INTEGER,
                      session_key INTEGER NOT NULL,
                      speed DOUBLE PRECISION,
                      throttle DOUBLE PRECISION,

                      FOREIGN KEY (meeting_key) REFERENCES meetings(meeting_key) ON DELETE CASCADE,
                      FOREIGN KEY (session_key) REFERENCES sessions(session_key) ON DELETE CASCADE
);