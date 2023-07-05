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
    balance float8 NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT pk_account_id PRIMARY KEY (account_id)
);
CREATE TABLE transfer (
    transfer_id serial NOT NULL,
    to_user_id int NOT NULL,
    from_user_id int NOT NULL,
    type VARCHAR(50) NOT NULL,
    amount float8 NOT NULL,
    status VARCHAR(50),
    CONSTRAINT pk_transfer_id PRIMARY KEY (transfer_id),
    CONSTRAINT fk_to_user_id FOREIGN KEY (to_user_id) REFERENCES users (user_id),
    CONSTRAINT fk_from_user_id FOREIGN KEY (from_user_id) REFERENCES users (user_id),
    CONSTRAINT type CHECK(type = 'Send' or type = 'Request'),
    CONSTRAINT status CHECK(status = 'Pending' or status = 'Rejected' or status = 'Approved'),
    CONSTRAINT amount CHECK(amount > 0)
);


COMMIT TRANSACTION;
