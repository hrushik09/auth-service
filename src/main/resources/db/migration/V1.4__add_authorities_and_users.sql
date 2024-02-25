INSERT INTO authorities(name)
VALUES ('read'),
       ('write'),
       ('defaultAdmin');

INSERT INTO users (username, password)
VALUES ('dummy', '$2a$10$uiw6LyXmzRasKj0MMfPbmODgz8lO03.56rKVFFod0jMMJ1sbfuyh2'),
       ('default-admin', '$2a$10$xwUiCoYsJA0ROJZcqZlz.u5.dkVr1KwUuXRL6A.Sq5jnJ6GGqmU7O');

INSERT INTO users_authorities(user_id, authority_id)
VALUES (1, 1),
       (1, 2),
       (2, 3);