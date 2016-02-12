CREATE TABLE users (
  userId SERIAL PRIMARY KEY,
  firstName VARCHAR(35) NOT NULL,
  lastName VARCHAR(35) NOT NULL,
  emailAddr VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  birthDate TIMESTAMP NOT NULL,

  UNIQUE(emailAddr)
);

CREATE TABLE movies (
  movieId SERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  creationDate TIMESTAMP NOT NULL,
  releaseDate TIMESTAMP NOT NULL,
  genres VARCHAR(25)[] NOT NULL,
  directorName VARCHAR(70) NOT NULL,
  runtimeMins INT CHECK (runtimeMins > 0),
  totalScore INT CHECK (totalScore >= 0),
  totalComments INT CHECK (totalComments >= 0),
  summary TEXT NOT NULL,

  UNIQUE(title, directorName)
);

CREATE TABLE comments (
  commentId SERIAL PRIMARY KEY,
  score INT CHECK (score >= 0 AND score <= 5),
  txt TEXT NOT NULL,
  creationDate TIMESTAMP NOT NULL,
  userId INT REFERENCES users(userId),
  movieId INT REFERENCES movies(movieId)
);


