-- los IDs son mayores para no pisar posibles usuarios que ya existan
INSERT INTO users(id,birthdate,email,firstname,admin,lastname,password) VALUES (999,TIMESTAMP '1984-09-10','admin@admin.com','admin',TRUE,'admin','1234567890');
INSERT INTO users(id,birthdate,email,firstname,admin,lastname,password) VALUES (998,TIMESTAMP '2004-09-10','user.1@user.com','ordinal',FALSE,'user','1234567890');
INSERT INTO users(id,birthdate,email,firstname,admin,lastname,password) VALUES (997,TIMESTAMP '2004-09-10','user.2@user.com','ordinal',FALSE,'user','1234567890');
INSERT INTO users(id,birthdate,email,firstname,admin,lastname,password) VALUES (996,TIMESTAMP '2004-09-10','user.3@user.com','ordinal',FALSE,'user','1234567890');

INSERT INTO users_users(users_id,interestingusers_id) VALUES(998,997);
INSERT INTO users_users(users_id,interestingusers_id) VALUES(997,996);
INSERT INTO users_users(users_id,interestingusers_id) VALUES(997,998);