INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (606,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-19', 90, 'A foo film about a foo life', 'foo', 0, 0, 'Pablo Martinez');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (606,17);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (607,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-18', 164, 'A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe', 'Guardians of the Galaxy', 0, 0, 'James Gunn');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (607,17);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (607,15);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (608,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-17', 182, 'When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping program called Ultron, things go horribly wrong and its up to Earths Mightiest Heroes to stop the villainous Ultron from enacting his terrible plans.', 'Avengers: Age of Ultron', 0, 0, ' Joss Whedon');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (608,17);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (608,15);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (608,6);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (609,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-16', 102, 'When their kingdom becomes trapped in perpetual winter, fearless Anna (Kristen Bell) joins forces with mountaineer Kristoff (Jonathan Groff) and his reindeer sidekick to find Annas sister, Snow Queen Elsa (Idina Menzel), and break her icy spell. Although their epic journey leads them to encounters with mystical trolls, a comedic snowman (Josh Gad), harsh conditions, and magic at every turn, Anna and Kristoff bravely push onward in a race to save their kingdom from winters cold grip', 'Frozen', 0, 0, 'Jennifer Lee');

INSERT INTO movies_genres(movies_id, genres_id) VALUES (609,4);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (609,9);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (609,14);
