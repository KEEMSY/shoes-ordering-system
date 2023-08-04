DROP SCHEMA IF EXISTS "member" CASCADE;

CREATE SCHEMA "member";

DROP TABLE IF EXISTS "member".members CASCADE;

CREATE TABLE "member".members
(
    id            UUID         NOT NULL,
    name          VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    member_status VARCHAR(255) NOT NULL,
    messages      VARCHAR,
    CONSTRAINT members_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "member".member_address CASCADE;

CREATE TABLE "member".member_address
(
    id          UUID         NOT NULL,
    member_id   UUID         NOT NULL,
    street      VARCHAR(255) NOT NULL,
    postal_code VARCHAR(255) NOT NULL,
    city        VARCHAR(255) NOT NULL,
    CONSTRAINT member_address_pkey PRIMARY KEY (id, member_id),
    CONSTRAINT "FK_MEMBER_ID" FOREIGN KEY (member_id)
        REFERENCES "member".members (id) ON DELETE CASCADE
);


DROP SCHEMA IF EXISTS "product" CASCADE;

CREATE SCHEMA "product";

DROP TABLE IF EXISTS "product".products CASCADE;

CREATE TABLE "product".products
(
    product_id      UUID         NOT NULL,
    name            VARCHAR(255) NOT NULL,
    description     VARCHAR(255),
    price           numeric(10,2)      NOT NULL,
    product_category VARCHAR(255) NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (product_id)
);

