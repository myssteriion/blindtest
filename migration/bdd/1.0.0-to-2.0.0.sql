-------------- themes removed : JEUX and CLASSIQUES --------------
-- delete on music table
DELETE FROM music WHERE theme = 8 OR theme = 9;

-- select impacted profile_stat
SELECT found_musics, id FROM profile_stat where found_musics like '%JEUX%' ORDER BY id ASC;
SELECT found_musics, id FROM profile_stat where found_musics like '%CLASSIQUE%' ORDER BY id ASC;
SELECT listened_musics, id FROM profile_stat where listened_musics like '%JEUX%' ORDER BY id ASC;
SELECT listened_musics, id FROM profile_stat where listened_musics like '%CLASSIQUE%' ORDER BY id ASC;

-- update impacted profile_stat
UPDATE profile_stat SET found_musics = '' WHERE id = -1 ORDER BY id ASC;
UPDATE profile_stat SET listened_musics = '' WHERE id = -1 ORDER BY id ASC;


-------------- rank enum removed --------------
-- select impacted profile_stat
SELECT won_games, id FROM profile_stat ORDER BY id ASC;

-- update impacted profile_stat
UPDATE profile_stat SET won_games = '' WHERE id = -1 ORDER BY id ASC;


-------------- music : theme and connectionMode are string --------------
ALTER TABLE music ALTER COLUMN theme VARCHAR(255) NOT NULL;
UPDATE music SET theme = 'ANNEES_60' WHERE theme = '0';
UPDATE music SET theme = 'ANNEES_70' WHERE theme = '1';
UPDATE music SET theme = 'ANNEES_80' WHERE theme = '2';
UPDATE music SET theme = 'ANNEES_90' WHERE theme = '3';
UPDATE music SET theme = 'ANNEES_2000' WHERE theme = '4';
UPDATE music SET theme = 'ANNEES_2010' WHERE theme = '5';
UPDATE music SET theme = 'SERIES_CINEMAS' WHERE theme = '6';
UPDATE music SET theme = 'DISNEY' WHERE theme = '7';

ALTER TABLE music ALTER COLUMN connection_mode VARCHAR(255) NOT NULL;
UPDATE music SET connection_mode = 'OFFLINE' WHERE connection_mode = '0';
UPDATE music SET connection_mode = 'ONLINE' WHERE connection_mode = '1';