-- music table
CREATE SEQUENCE IF NOT EXISTS music_seq START WITH 0 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS music (
    id BIGINT DEFAULT music_seq.nextval PRIMARY KEY,
    name VARCHAR2 NOT NULL,
    theme VARCHAR2 NOT NULL,
    played INT NOT NULL
);
ALTER TABLE music ADD CONSTRAINT IF NOT EXISTS name_theme_unique UNIQUE(name, theme);

-- profile table
CREATE SEQUENCE IF NOT EXISTS profile_seq START WITH 0 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS profile (
    id BIGINT DEFAULT profile_seq.nextval PRIMARY KEY,
    name VARCHAR2 NOT NULL UNIQUE,
    avatar_name VARCHAR2 NOT NULL
);

-- profile
CREATE SEQUENCE IF NOT EXISTS profile_stat_seq START WITH 0 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS profile_stat (
    id BIGINT DEFAULT profile_stat_seq.nextval PRIMARY KEY,
    profile_id BIGINT NOT NULL UNIQUE,
    played_games INT NOT NULL,
    listened_musics VARCHAR2 NOT NULL,
    found_musics VARCHAR2 NOT NULL,
    best_scores VARCHAR2 NOT NULL,
    FOREIGN KEY (profile_id) REFERENCES profile(id)
);
