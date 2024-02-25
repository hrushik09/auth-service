CREATE TABLE users_authorities
(
    id           INT NOT NULL AUTO_INCREMENT,
    user_id      INT NOT NULL,
    authority_id INT NOT NULL,
    CONSTRAINT PK_users_authorities PRIMARY KEY (id),
    CONSTRAINT FK_users_authorities_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FK_users_authorities_authority_id FOREIGN KEY (authority_id) REFERENCES authorities (id)
);