BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS transfer;

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
    balance double NOT NULL,
);
CREATE TABLE transfer (
    transfer_id serial NOT NULL,
    to_user_id int NOT NULL,
    from_user_id int NOT NULL,
    type VARCHAR(50),
    amount double NOT NULL,
    status VARCHAR(50),
);


COMMIT TRANSACTION;
