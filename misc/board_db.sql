-- tables
-- Table: boards
CREATE TABLE imgboard.boards (
    path varchar(10)  NOT NULL,
    description varchar(100)  NULL,
    CONSTRAINT boards_pk PRIMARY KEY (path)
);

-- Table: images
CREATE TABLE imgboard.images (
    image_id bigserial  NOT NULL,
    sha256 varchar(100)  NOT NULL,
    data bytea  NOT NULL,
    size int  NOT NULL,
    width int  NOT NULL,
    heigth int  NOT NULL,
    CONSTRAINT images_pk PRIMARY KEY (image_id)
);

-- Table: posts
CREATE TABLE imgboard.posts (
    post_id bigserial  NOT NULL,
    message text  NOT NULL,
    header varchar(100)  NULL,
    thread_id bigint  NOT NULL,
    image_id bigint  NULL,
    posted_when timestamp  NOT NULL,
    ip varchar(30)  NULL,
    CONSTRAINT posts_pk PRIMARY KEY (post_id)
);

-- Table: threads
CREATE TABLE imgboard.threads (
    thread_id bigserial  NOT NULL,
    is_pinned int  NULL,
    board_path varchar(10)  NOT NULL,
    CONSTRAINT threads_pk PRIMARY KEY (thread_id)
);





-- foreign keys
-- Reference:  posts_images (table: imgboard.posts)


ALTER TABLE imgboard.posts ADD CONSTRAINT posts_images 
    FOREIGN KEY (image_id)
    REFERENCES imgboard.images (image_id) NOT DEFERRABLE 
;

-- Reference:  posts_threads (table: imgboard.posts)


ALTER TABLE imgboard.posts ADD CONSTRAINT posts_threads 
    FOREIGN KEY (thread_id)
    REFERENCES imgboard.threads (thread_id) NOT DEFERRABLE 
;

-- Reference:  threads_boards (table: imgboard.threads)


ALTER TABLE imgboard.threads ADD CONSTRAINT threads_boards 
    FOREIGN KEY (board_path)
    REFERENCES imgboard.boards (path) NOT DEFERRABLE 
;






-- End of file.

