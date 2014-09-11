CREATE TABLE Users (
  userId SERIAL PRIMARY KEY,
  firstName VARCHAR(255),
  lastName VARCHAR(255),
  emailAddr VARCHAR(255),
  password VARCHAR(255),
  birthDate TIMESTAMP,
  vip BOOLEAN
);

CREATE TABLE Movies (
  movieId SERIAL PRIMARY KEY,
  title VARCHAR(255),
  creationDate TIMESTAMP,
  releaseDate TIMESTAMP,
  genres VARCHAR(25)[],
  directorName VARCHAR(255),
  runtimeMins INT,
  summary TEXT
);

CREATE TABLE Comments (
  commentId SERIAL PRIMARY KEY,
  score INT,
  txt TEXT,
  creationDate TIMESTAMP,
  userId INT REFERENCES Users(userId),
  movieId INT REFERENCES Movies(movieId)
);


