drop table if exists event_compilations;
drop table if exists compilations;
drop table if exists requests;
drop table if exists events;
drop table if exists locations;
drop table if exists categories;
drop table if exists users;

CREATE TABLE IF NOT EXISTS users
(
    user_id    INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_name  VARCHAR(255) UNIQUE                  NOT NULL,
    user_email VARCHAR(255) UNIQUE                  NOT NULL,
    CONSTRAINT pk_user_id PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id   INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    category_name VARCHAR(255) UNIQUE                  NOT NULL,
    CONSTRAINT pk_category_id PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS locations
(
    location_id      INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    location_name VARCHAR(255) UNIQUE                  NOT NULL,
    lat              REAL                                 NOT NULL,
    lon              REAL                                 NOT NULL,
    CONSTRAINT pk_location_id PRIMARY KEY (location_id)
);

CREATE TABLE IF NOT EXISTS events
(
    event_id           INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id            INT                                  NOT NULL,
    category_id        INT                                  NOT NULL,
    location_id        INT                                  NOT NULL,
    annotation         VARCHAR(2000)                        NOT NULL,
    description        VARCHAR(7000)                        NOT NULL,
    event_date         VARCHAR(32)                          NOT NULL,
    paid               BOOLEAN                              NOT NULL,
    participant_limit  INT,
    request_moderation BOOLEAN,
    event_title        VARCHAR(120)                         NOT NULL,
    event_state        VARCHAR(32),
--     lat                REAL                                 NOT NULL,
--     lon                REAL                                 NOT NULL,
    created_on         VARCHAR(32),
    published_on       VARCHAR(32),
    CONSTRAINT pk_event_id PRIMARY KEY (event_id),
    CONSTRAINT fk_event_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_event_category_id FOREIGN KEY (category_id) REFERENCES categories (category_id),
    CONSTRAINT fk_event_location_id FOREIGN KEY (location_id) REFERENCES locations (location_id)
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id     INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    compilation_pinned BOOLEAN,
    compilation_title  VARCHAR(50)                          NOT NULL,
    CONSTRAINT pk_compilation_id PRIMARY KEY (compilation_id)
);

CREATE TABLE IF NOT EXISTS event_compilations
(
    event_id       INT NOT NULL,
    compilation_id INT NOT NULL,
    CONSTRAINT pk_event_compilations_id PRIMARY KEY (event_id, compilation_id),
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (compilation_id)
);

CREATE TABLE IF NOT EXISTS requests
(
    request_id      INT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    request_created VARCHAR(32),
    event_id        INT                                  NOT NULL,
    user_id         INT                                  NOT NULL,
    request_status  VARCHAR(32),
    CONSTRAINT pk_request_id PRIMARY KEY (request_id),
    CONSTRAINT fk_request_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT fk_request_event_id FOREIGN KEY (event_id) REFERENCES events (event_id)
);