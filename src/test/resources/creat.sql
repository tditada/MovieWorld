CREATE TABLE Users (
  id SERIAL PRIMARY KEY,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
  emailAddr VARCHAR(255),
  password VARCHAR(255),
  birthDate TIMESTAMP,
  vip BOOLEAN
);

CREATE TABLE Movies (
  id SERIAL PRIMARY KEY,
  title VARCHAR(255),
  creationDate TIMESTAMP,
  releaseDate TIMESTAMP,
  genres VARCHAR(25)[],
  directorName VARCHAR(255),
  runtimeInMins INT,
  summary TEXT
);

CREATE TABLE Comments (
  id SERIAL PRIMARY KEY,
  score INT,
  txt TEXT,
  userId INT REFERENCES Users(id),
  movieId INT REFERENCES Movies(id)
);


