DROP TABLE IF EXISTS group_sub;
DROP TABLE IF EXISTS grour_x_user;

CREATE TABLE group_sub
(

    id              serial PRIMARY KEY,
    title           varchar(100),
    last_article_id int

);

CREATE TABLE grour_x_user
(
    group_id int REFERENCES group_sub (id)    NOT NULL,
    user_id  VARCHAR(100) REFERENCES tg_user (chat_id) NOT NULL,
    UNIQUE (group_id, user_id)

)