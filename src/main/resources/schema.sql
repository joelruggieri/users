CREATE TABLE IF NOT EXISTS Users (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(128),
    created_at DATETIME NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Loans (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    amount DECIMAL(40,20) NOT NULL,
    user_id INTEGER      NOT NULL,
    created_at      DATETIME     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_Loans_User_id
            FOREIGN KEY (user_id) REFERENCES Users (id)
                ON UPDATE CASCADE ON DELETE CASCADE
);

