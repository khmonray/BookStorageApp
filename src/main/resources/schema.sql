create TABLE readers
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


create TABLE books
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    reader_id   INT,
    FOREIGN KEY (reader_id) REFERENCES readers (id)
);


INSERT INTO readers (name)
VALUES ('John Doe'),
       ('Alice Smith'),
       ('Michael Johnson'),
       ('Emily Brown'),
       ('David Wilson'),
       ('Sarah Davis'),
       ('James Taylor');

INSERT INTO books (title, description, reader_id)
VALUES ('Book 1', 'Description for Book 1', 1),
       ('Book 2', 'Description for Book 2', 2),
       ('Book 3', 'Description for Book 3', 3),
       ('Book 4', 'Description for Book 4', 4),
       ('Book 5', 'Description for Book 5', 5),
       ('Book 6', 'Description for Book 6', 6),
       ('Book 7', 'Description for Book 7', 7),
       ('Book 8', 'Description for Book 8', 1),
       ('Book 9', 'Description for Book 9', 2),
       ('Book 10', 'Description for Book 10', 3);

