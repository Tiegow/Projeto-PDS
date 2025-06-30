CREATE TABLE driver_details (
                                id SERIAL PRIMARY KEY,
                                first_name VARCHAR(100),
                                last_name VARCHAR(100),
                                driver_number INT,
                                country_code VARCHAR(10),
                                headshot_url TEXT,

                                team_name VARCHAR(100),
                                team_colour VARCHAR(10),
                                team_full_name VARCHAR(150),
                                team_base VARCHAR(150),
                                team_principal VARCHAR(100),
                                team_championship_position INT,

                                championship_position INT,
                                points INT,
                                best_position INT,
                                worst_position INT,
                                victories INT,

                                car_model VARCHAR(50),
                                engine VARCHAR(50),
                                power_hp INT,
                                weight_kg INT,

                                date DATE
);