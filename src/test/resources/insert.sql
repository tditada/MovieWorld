INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (306,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-15', 90, 'A foo film about a foo life', 'foo', 0, 0, 'Pablo Martinez');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (306,17);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (307,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-12', 164, 'A group of intergalactic criminals are forced to work together to stop a fanatical warrior from taking control of the universe', 'Guardians of the Galaxy', 5, 0, 'James Gunn');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (307,17);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (307,15);
INSERT INTO comments(creationdate, score, text, movie_id, user_id, totalcommentscore,totalreports) VALUES(TIMESTAMP '2016-02-12',5,'BEST MOVIE EVER', 307, 997,0,0);

INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (308,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-13', 182, 'When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping program called Ultron, things go horribly wrong and its up to Earths Mightiest Heroes to stop the villainous Ultron from enacting his terrible plans.', 'Avengers: Age of Ultron', 3, 0, ' Joss Whedon');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (308,17);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (308,15);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (308,6);
INSERT INTO comments(creationdate, score, text, movie_id, user_id, totalcommentscore,totalreports) VALUES(TIMESTAMP '2016-02-12',3,'a little cliché', 308, 996,0,0);


INSERT INTO movies(id,creationdate, image, releasedate, runtimeinmins, summary, title, totalscore, visits, director) VALUES (309,TIMESTAMP '2016-02-12', NULL, TIMESTAMP '2016-02-16', 102, 'When their kingdom becomes trapped in perpetual winter, fearless Anna (Kristen Bell) joins forces with mountaineer Kristoff (Jonathan Groff) and his reindeer sidekick to find Annas sister, Snow Queen Elsa (Idina Menzel), and break her icy spell. Although their epic journey leads them to encounters with mystical trolls, a comedic snowman (Josh Gad), harsh conditions, and magic at every turn, Anna and Kristoff bravely push onward in a race to save their kingdom from winters cold grip', 'Frozen', 4, 0, 'Jennifer Lee');
INSERT INTO movies_genres(movies_id, genres_id) VALUES (309,4);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (309,9);
INSERT INTO movies_genres(movies_id, genres_id) VALUES (309,14);
INSERT INTO comments(creationdate, score, text, movie_id, user_id,totalcommentscore,totalreports) VALUES(TIMESTAMP '2016-02-12',4,'really nice', 309, 998,0,0);

