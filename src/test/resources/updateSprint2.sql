ALTER TABLE "users" RENAME TO users__old;
ALTER TABLE "movies" RENAME TO movies__old;
ALTER TABLE "comments" RENAME TO comments__old;

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2014-11-06 15:46:16

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 182 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2011 (class 0 OID 0)
-- Dependencies: 182
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 170 (class 1259 OID 44018)
-- Name: comment_reports; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE comment_reports (
    comments_id integer NOT NULL,
    reportingusers_id integer NOT NULL
);


ALTER TABLE public.comment_reports OWNER TO paw;

--
-- TOC entry 171 (class 1259 OID 44023)
-- Name: comment_scorers; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE comment_scorers (
    comments_id integer NOT NULL,
    scorers_id integer NOT NULL
);


ALTER TABLE public.comment_scorers OWNER TO paw;

--
-- TOC entry 173 (class 1259 OID 44030)
-- Name: comments; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE comments (
    id integer NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    score integer NOT NULL,
    text text NOT NULL,
    totalcommentscore integer NOT NULL,
    totalreports integer NOT NULL,
    movie_id integer,
    user_id integer
);


ALTER TABLE public.comments OWNER TO paw;

--
-- TOC entry 172 (class 1259 OID 44028)
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comments_id_seq OWNER TO paw;

--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 172
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE comments_id_seq OWNED BY comments.id;


--
-- TOC entry 175 (class 1259 OID 44041)
-- Name: genres; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE genres (
    id integer NOT NULL,
    name character varying(25) NOT NULL
);


ALTER TABLE public.genres OWNER TO paw;

--
-- TOC entry 174 (class 1259 OID 44039)
-- Name: genres_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE genres_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.genres_id_seq OWNER TO paw;

--
-- TOC entry 2013 (class 0 OID 0)
-- Dependencies: 174
-- Name: genres_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE genres_id_seq OWNED BY genres.id;


--
-- TOC entry 177 (class 1259 OID 44051)
-- Name: movies; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE movies (
    id integer NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    director character varying(70) NOT NULL,
    image bytea,
    releasedate timestamp without time zone NOT NULL,
    runtimeinmins integer NOT NULL,
    summary text NOT NULL,
    title character varying(255) NOT NULL,
    totalscore integer NOT NULL
);


ALTER TABLE public.movies OWNER TO paw;

--
-- TOC entry 178 (class 1259 OID 44062)
-- Name: movies_genres; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE movies_genres (
    movies_id integer NOT NULL,
    genres_id integer NOT NULL
);


ALTER TABLE public.movies_genres OWNER TO paw;

--
-- TOC entry 176 (class 1259 OID 44049)
-- Name: movies_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE movies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movies_id_seq OWNER TO paw;

--
-- TOC entry 2014 (class 0 OID 0)
-- Dependencies: 176
-- Name: movies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE movies_id_seq OWNED BY movies.id;


--
-- TOC entry 180 (class 1259 OID 44069)
-- Name: users; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    admin boolean NOT NULL,
    birthdate timestamp without time zone NOT NULL,
    email character varying(100) NOT NULL,
    firstname character varying(35) NOT NULL,
    lastname character varying(35) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO paw;

--
-- TOC entry 179 (class 1259 OID 44067)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: paw
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO paw;

--
-- TOC entry 2015 (class 0 OID 0)
-- Dependencies: 179
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: paw
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 181 (class 1259 OID 44077)
-- Name: users_users; Type: TABLE; Schema: public; Owner: paw; Tablespace: 
--

CREATE TABLE users_users (
    users_id integer NOT NULL,
    interestingusers_id integer NOT NULL
);


ALTER TABLE public.users_users OWNER TO paw;

--
-- TOC entry 1859 (class 2604 OID 44033)
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comments ALTER COLUMN id SET DEFAULT nextval('comments_id_seq'::regclass);


--
-- TOC entry 1860 (class 2604 OID 44044)
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE ONLY genres ALTER COLUMN id SET DEFAULT nextval('genres_id_seq'::regclass);


--
-- TOC entry 1861 (class 2604 OID 44054)
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE ONLY movies ALTER COLUMN id SET DEFAULT nextval('movies_id_seq'::regclass);


--
-- TOC entry 1862 (class 2604 OID 44072)
-- Name: id; Type: DEFAULT; Schema: public; Owner: paw
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1864 (class 2606 OID 44022)
-- Name: comment_reports_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY comment_reports
    ADD CONSTRAINT comment_reports_pkey PRIMARY KEY (comments_id, reportingusers_id);


--
-- TOC entry 1866 (class 2606 OID 44027)
-- Name: comment_scorers_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY comment_scorers
    ADD CONSTRAINT comment_scorers_pkey PRIMARY KEY (comments_id, scorers_id);


--
-- TOC entry 1868 (class 2606 OID 44038)
-- Name: comments_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT new_comments_pkey PRIMARY KEY (id);


--
-- TOC entry 1870 (class 2606 OID 44048)
-- Name: genres_name_key; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY genres
    ADD CONSTRAINT genres_name_key UNIQUE (name);


--
-- TOC entry 1872 (class 2606 OID 44046)
-- Name: genres_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (id);


--
-- TOC entry 1878 (class 2606 OID 44066)
-- Name: movies_genres_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY movies_genres
    ADD CONSTRAINT movies_genres_pkey PRIMARY KEY (movies_id, genres_id);


--
-- TOC entry 1874 (class 2606 OID 44059)
-- Name: movies_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY movies
    ADD CONSTRAINT new_movies_pkey PRIMARY KEY (id);


--
-- TOC entry 1876 (class 2606 OID 44061)
-- Name: movies_title_director_key; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY movies
    ADD CONSTRAINT movies_title_director_key UNIQUE (title, director);


--
-- TOC entry 1880 (class 2606 OID 44076)
-- Name: users_email_key; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 1882 (class 2606 OID 44074)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT new_users_pkey PRIMARY KEY (id);


--
-- TOC entry 1884 (class 2606 OID 44083)
-- Name: users_users_interestingusers_id_key; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

--ALTER TABLE ONLY users_users
--    ADD CONSTRAINT users_users_interestingusers_id_key UNIQUE (interestingusers_id);


--
-- TOC entry 1886 (class 2606 OID 44081)
-- Name: users_users_pkey; Type: CONSTRAINT; Schema: public; Owner: paw; Tablespace: 
--

ALTER TABLE ONLY users_users
    ADD CONSTRAINT users_users_pkey PRIMARY KEY (users_id, interestingusers_id);


--
-- TOC entry 1887 (class 2606 OID 44084)
-- Name: fk360b17df7dad55f; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comment_reports
    ADD CONSTRAINT fk360b17df7dad55f FOREIGN KEY (reportingusers_id) REFERENCES users(id);


--
-- TOC entry 1888 (class 2606 OID 44089)
-- Name: fk360b17dfe96c5687; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comment_reports
    ADD CONSTRAINT fk360b17dfe96c5687 FOREIGN KEY (comments_id) REFERENCES comments(id);


--
-- TOC entry 1894 (class 2606 OID 44119)
-- Name: fk37775bcc3ad69bb3; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY movies_genres
    ADD CONSTRAINT fk37775bcc3ad69bb3 FOREIGN KEY (genres_id) REFERENCES genres(id);


--
-- TOC entry 1893 (class 2606 OID 44114)
-- Name: fk37775bcc5edfe87a; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY movies_genres
    ADD CONSTRAINT fk37775bcc5edfe87a FOREIGN KEY (movies_id) REFERENCES movies(id);


--
-- TOC entry 1890 (class 2606 OID 44099)
-- Name: fk677ab673e96c5687; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comment_scorers
    ADD CONSTRAINT fk677ab673e96c5687 FOREIGN KEY (comments_id) REFERENCES comments(id);


--
-- TOC entry 1889 (class 2606 OID 44094)
-- Name: fk677ab673ea0b9306; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comment_scorers
    ADD CONSTRAINT fk677ab673ea0b9306 FOREIGN KEY (scorers_id) REFERENCES users(id);


--
-- TOC entry 1891 (class 2606 OID 44104)
-- Name: fkdc17ddf4120fad6d; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf4120fad6d FOREIGN KEY (movie_id) REFERENCES movies(id);


--
-- TOC entry 1892 (class 2606 OID 44109)
-- Name: fkdc17ddf481d3448e; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY comments
    ADD CONSTRAINT fkdc17ddf481d3448e FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 1895 (class 2606 OID 44124)
-- Name: fkf6f8d9114a4fcda9; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY users_users
    ADD CONSTRAINT fkf6f8d9114a4fcda9 FOREIGN KEY (interestingusers_id) REFERENCES users(id);


--
-- TOC entry 1896 (class 2606 OID 44129)
-- Name: fkf6f8d9117ac41d31; Type: FK CONSTRAINT; Schema: public; Owner: paw
--

ALTER TABLE ONLY users_users
    ADD CONSTRAINT fkf6f8d9117ac41d31 FOREIGN KEY (users_id) REFERENCES users(id);


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-11-06 15:46:17

--
-- PostgreSQL database dump complete
--


insert into users(id, firstName, lastName, email, password, birthdate, admin) 
    select userId, firstName, lastName, emailAddr, password, birthdate, FALSE from users__old;

insert into movies(id,title,creationdate,releasedate, director,runtimeinmins,summary,totalscore,image)
    select movieId,title,creationDate,releaseDate,directorName,runtimeMins,summary,totalScore, null from movies__old;

create or replace function explode_array(in_array anyarray) returns setof anyelement as
$$
    select ($1)[s] from generate_series(1,array_upper($1, 1)) as s;
$$
language sql immutable;

-- GENRES Y MOVIES_GENRES
insert into genres(name) select distinct * from (select explode_array(movies__old.genres) from movies__old) as aux;

--insert into movies_genres(movies_id,genres_id)
insert into movies_genres(movies_id, genres_id)
    select movies.id, genres.id
    from movies, genres, movies__old
    where movies.director = movies__old.directorname 
        and movies.title = movies__old.title 
        and genres.name = any(movies__old.genres);

insert into comments(id,creationdate,score,text,totalcommentscore,totalreports,movie_id,user_id)
    select commentId, creationDate, score, txt, 0, 0, movieId, userId from comments__old;

SELECT setval('movies_id_seq', (select greatest(count(*), 1) from movies), 't');
SELECT setval('users_id_seq', (select greatest(count(*), 1) from users), 't');
SELECT setval('comments_id_seq', (select greatest(count(*), 1) from comments), 't');

-- drop all tables along with its sequences
DROP TABLE movies__old CASCADE;
DROP TABLE users__old CASCADE;
DROP TABLE comments__old CASCADE;

