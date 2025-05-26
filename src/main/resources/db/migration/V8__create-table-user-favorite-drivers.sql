CREATE TABLE user_favorite_drivers (
    user_id INTEGER NOT NULL,
    driver_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, driver_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE
);
