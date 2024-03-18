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
VALUES
('The Enchanted Forest', 'A tale of magic and adventure in a mystical woodland.', 1),
('The Last Star', 'A journey through space to save the galaxy from impending doom.', 2),
('Whispers of the Wind', 'A saga of love and loss in the heart of a stormy city.', 3),
('The Silent Sea', 'An underwater odyssey of discovery and survival.', 4),
('Echoes of the Past', 'A ghostly journey through time, uncovering secrets of the past.', 5),
('The Forgotten Kingdom', 'A quest to reclaim a lost kingdom, filled with danger and mystery.', 6),
('The Celestial Dance', 'A ballet of celestial bodies, where every move is a dance with destiny.', 7),
('The Shadowed Path', 'A journey through the shadows of a forgotten civilization.', 8),
('The Eternal Flame', 'A story of love and sacrifice in the face of eternal darkness.', 9),
('The Lost Chronicles', 'An epic quest to uncover the secrets of a long-lost civilization.', 10);


