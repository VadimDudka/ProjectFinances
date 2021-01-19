DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS purse CASCADE;
DROP TABLE IF EXISTS token CASCADE;
DROP TABLE IF EXISTS transfer CASCADE;
DROP TABLE IF EXISTS tag CASCADE;
DROP TABLE IF EXISTS transfer_part CASCADE;

-- Create schemas

-- Create tables
CREATE TABLE customer
(
    customer_id SERIAL NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    username VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    PRIMARY KEY(customer_id)
);

CREATE TABLE purse
(
    purse_id SERIAL NOT NULL,
    customer_id INTEGER,
    name VARCHAR(64) NOT NULL,
    value DOUBLE PRECISION NOT NULL,
    PRIMARY KEY(purse_id)
);

CREATE TABLE token
(
    token_id SERIAL NOT NULL,
    customer_id INTEGER NOT NULL,
    value VARCHAR(128) NOT NULL UNIQUE,
    expiry_date TIMESTAMPTZ NOT NULL,
    PRIMARY KEY(token_id)
);

CREATE TABLE transfer
(
    transfer_id SERIAL NOT NULL,
    target_purse_id INTEGER NOT NULL,
    dest_purse_id INTEGER,
    name VARCHAR(64) NOT NULL,
    description VARCHAR(512),
    value INTEGER NOT NULL,
    date_time TIMESTAMPTZ NOT NULL,
    PRIMARY KEY(transfer_id)
);

CREATE TABLE tag
(
    tag_id SERIAL NOT NULL,
    customer_id INTEGER NOT NULL,
    name INTEGER NOT NULL,
    PRIMARY KEY(tag_id)
);

CREATE TABLE transfer_part
(
    transfer_part_id SERIAL NOT NULL,
    transfer_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    percentage DOUBLE PRECISION NOT NULL,
    PRIMARY KEY(transfer_part_id)
);


-- Create FKs
ALTER TABLE token
    ADD    FOREIGN KEY (customer_id)
    REFERENCES customer(customer_id)
    MATCH SIMPLE
;

ALTER TABLE purse
    ADD    FOREIGN KEY (customer_id)
    REFERENCES customer(customer_id)
    MATCH SIMPLE
;

ALTER TABLE transfer
    ADD    FOREIGN KEY (target_purse_id)
    REFERENCES purse(purse_id)
    MATCH SIMPLE
;

ALTER TABLE transfer_part
    ADD    FOREIGN KEY (transfer_id)
    REFERENCES transfer(transfer_id)
    MATCH SIMPLE
;

ALTER TABLE transfer
    ADD    FOREIGN KEY (dest_purse_id)
    REFERENCES purse(purse_id)
    MATCH SIMPLE
;

ALTER TABLE transfer_part
    ADD    FOREIGN KEY (tag_id)
    REFERENCES tag(tag_id)
    MATCH SIMPLE
;

ALTER TABLE tag
    ADD    FOREIGN KEY (customer_id)
    REFERENCES customer(customer_id)
    MATCH SIMPLE
;


-- Create Indexes
