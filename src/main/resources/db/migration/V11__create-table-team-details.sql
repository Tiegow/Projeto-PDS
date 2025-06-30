CREATE TABLE team_detail (
                       id BIGSERIAL PRIMARY KEY,
                       team_name VARCHAR(255),
                       team_base VARCHAR(255),
                       team_principal VARCHAR(255),
                       team_championship_position INTEGER,
                       victories INTEGER,
                       team_colour VARCHAR(255),
                       team_points INTEGER,
                       date DATE
);