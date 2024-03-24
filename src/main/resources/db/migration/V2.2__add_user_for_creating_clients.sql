INSERT INTO authorities(name)
VALUES ('clients:create');

INSERT INTO users (username, password)
VALUES ('user_create_client', '$2a$10$xwUiCoYsJA0ROJZcqZlz.u5.dkVr1KwUuXRL6A.Sq5jnJ6GGqmU7O');

INSERT INTO users_authorities(user_id, authority_id)
VALUES (3, 4);