-- Created by Vertabelo (http://vertabelo.com)
-- Script type: create
-- Scope: [tables, references]
-- Generated at Sat Jul 19 17:08:12 UTC 2014




-- tables
-- Table: boards
CREATE TABLE imgboard.boards (
	path        VARCHAR(10)  NOT NULL,
	description VARCHAR(100) NULL,
	CONSTRAINT boards_pk PRIMARY KEY (path)
);

-- Table: images
CREATE TABLE imgboard.images (
	image_id BIGSERIAL    NOT NULL,
	sha256   VARCHAR(100) NOT NULL,
	data     BYTEA        NOT NULL,
	preview     BYTEA                ,
	size     BIGINT       NOT NULL,
	width    INT          NOT NULL,
	height   INT          NOT NULL,
	CONSTRAINT images_pk PRIMARY KEY (image_id)
);

-- Table: posts
CREATE TABLE imgboard.posts (
	post_id     BIGSERIAL   NOT NULL,
	message     TEXT        NULL,
	thread_id   BIGINT      NOT NULL,
	image_id    BIGINT      NULL,
	posted_when TIMESTAMP   NOT NULL,
	ip          VARCHAR(30) NULL,
	author      VARCHAR(50) NULL,
	CONSTRAINT posts_pk PRIMARY KEY (post_id)
);

-- Table: threads
CREATE TABLE imgboard.threads (
	thread_id          BIGSERIAL   NOT NULL,
	is_pinned          BOOLEAN DEFAULT FALSE,
	board_path         VARCHAR(10) NOT NULL,
	header             VARCHAR(30),
	created_when       TIMESTAMP   NOT NULL,
	last_response_date TIMESTAMP,
	CONSTRAINT threads_pk PRIMARY KEY (thread_id)
);


-- foreign keys
-- Reference:  posts_images (table: imgboard.posts)


ALTER TABLE imgboard.posts ADD CONSTRAINT posts_images
FOREIGN KEY (image_id)
REFERENCES imgboard.images (image_id) NOT DEFERRABLE;

-- Reference:  posts_threads (table: imgboard.posts)


ALTER TABLE imgboard.posts ADD CONSTRAINT posts_threads
FOREIGN KEY (thread_id)
REFERENCES imgboard.threads (thread_id) NOT DEFERRABLE;

-- Reference:  threads_boards (table: imgboard.threads)


ALTER TABLE imgboard.threads ADD CONSTRAINT threads_boards
FOREIGN KEY (board_path)
REFERENCES imgboard.boards (path) NOT DEFERRABLE;

-- End of file.

