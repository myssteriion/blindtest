-- themes removed : JEUX and CLASSIQUES

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