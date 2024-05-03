ALTER TABLE group_x_user DROP CONSTRAINT IF EXISTS  grour_x_user_user_id_fkey;
ALTER TABLE group_x_user ALTER COLUMN user_id TYPE BIGINT USING (user_id::bigint);
ALTER TABLE tg_user ALTER COLUMN chat_id TYPE BIGINT USING (chat_id::bigint);
ALTER TABLE group_x_user ADD CONSTRAINT grour_x_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES tg_user(chat_id);