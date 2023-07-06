BEGIN TRANSACTION;

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

INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER'); -- 1
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER'); -- 2
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER'); -- 3

INSERT INTO account (user_id, balance) VALUES           (3, 500);
INSERT INTO account (user_id, balance) VALUES           (2, 200);

INSERT INTO transfer (to_user_id, from_user_id,type,amount,status) VALUES (2, 3, 'Request', '250', 'Pending');

COMMIT TRANSACTION;
