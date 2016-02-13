INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (506,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-15', 90, 'A foo film about a foo life', 'foo', 0, 0, 'Pablo Martinez');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (506,17);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (507,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-14', 164, 'A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe', 'Guardians of the Galaxy', 0, 0, 'James Gunn');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (507,17);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (507,15);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (508,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-13', 182, 'When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping program called Ultron, things go horribly wrong and its up to Earths Mightiest Heroes to stop the villainous Ultron from enacting his terrible plans.', 'Avengers: Age of Ultron', 0, 0, ' Joss Whedon');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (508,17);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (508,15);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (508,6);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (509,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-16', 102, 'When their kingdom becomes trapped in perpetual winter, fearless Anna (Kristen Bell) joins forces with mountaineer Kristoff (Jonathan Groff) and his reindeer sidekick to find Annas sister, Snow Queen Elsa (Idina Menzel), and break her icy spell. Although their epic journey leads them to encounters with mystical trolls, a comedic snowman (Josh Gad), harsh conditions, and magic at every turn, Anna and Kristoff bravely push onward in a race to save their kingdom from winters cold grip', 'Frozen', 0, 0, 'Jennifer Lee');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (509,4);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (509,9);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (509,14);
