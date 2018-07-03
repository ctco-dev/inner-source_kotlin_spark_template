CREATE TABLE "user" (
    id        INT  NOT NULL PRIMARY KEY DEFAULT NEXTVAL('SEQ_DATA_ID'),
    firstname text NOT NULL,
    lastname  text NOT NULL
);

CREATE TABLE "role" (
    id          INT  NOT NULL PRIMARY KEY DEFAULT NEXTVAL('SEQ_DATA_ID'),
    description text NOT NULL
);

CREATE TABLE user_role (
    id      INT NOT NULL PRIMARY KEY DEFAULT NEXTVAL('SEQ_DATA_ID'),
    user_id int REFERENCES "user" (id),
    role_id int REFERENCES "role" (id)
);
