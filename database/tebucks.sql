BEGIN TRANSACTION;

DROP TABLE IF EXISTS transfer;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
	role varchar(20),
	CONSTRAINT pk_users PRIMARY KEY (user_id),
	CONSTRAINT uq_username UNIQUE (username)
);
CREATE TABLE account (
    account_id serial NOT NULL,
    user_id serial NOT NULL,
    balance money NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT pk_account_id PRIMARY KEY (account_id)
);
CREATE TABLE transfer (
    transfer_id serial NOT NULL,
    to_user_id int NOT NULL,
    from_user_id int NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount money NOT NULL,
    status VARCHAR(50),
    CONSTRAINT pk_transfer_id PRIMARY KEY (transfer_id),
    CONSTRAINT fk_to_user_id FOREIGN KEY (to_user_id) REFERENCES users (user_id),
    CONSTRAINT fk_from_user_id FOREIGN KEY (from_user_id) REFERENCES users (user_id),
    CONSTRAINT type CHECK(type = 'Send' or type = 'Request'),
    CONSTRAINT status CHECK(status = 'Pending' or status = 'Rejected' or status = 'Approved'),
    CONSTRAINT amount CHECK(amount > '0')
);


COMMIT TRANSACTION;



-- Create users
INSERT INTO users (username, password_hash)
VALUES ('user1', 'password1'),
       ('user2', 'password2'),
       ('user3', 'password3');

-- Get user ids
DO $$
    DECLARE
        user1_id INTEGER;
        user2_id INTEGER;
        user3_id INTEGER;
    BEGIN
        SELECT user_id INTO user1_id FROM users WHERE username = 'user1';
        SELECT user_id INTO user2_id FROM users WHERE username = 'user2';
        SELECT user_id INTO user3_id FROM users WHERE username = 'user3';

        -- Create accounts
        INSERT INTO account (user_id, balance)
        VALUES (user1_id, 1000.0),
               (user2_id, 2000.0),
               (user3_id, 3000.0);

        -- Create transfers
        INSERT INTO transfer (to_user_id, from_user_id, type, amount, status)
        VALUES (user2_id, user1_id, 'Send', 100.0, 'Approved'),
               (user3_id, user1_id, 'Send', 200.0, 'Approved'),
               (user1_id, user2_id, 'Request', 50.0, 'Pending');
    END $$;
