ALTER TABLE realm
  ALTER COLUMN name TYPE Varchar;
  
-- Sequence: character_char_id_seq

-- DROP SEQUENCE realm_realm_id_seq;

CREATE SEQUENCE realm_realm_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2
  CACHE 1;
ALTER SEQUENCE realm_realm_id_seq
  OWNER TO "ComicsZoneRole";

ALTER TABLE realm
   ALTER COLUMN realm_id TYPE integer;
ALTER TABLE realm
   ALTER COLUMN realm_id SET DEFAULT nextval('realm_realm_id_seq'::regclass);

  
DROP TABLE universe CASCADE;
ALTER TABLE character 
  ADD COLUMN publisher_id integer;
ALTER TABLE character
  ADD CONSTRAINT created_by FOREIGN KEY ( publisher_id ) REFERENCES publisher (publisher_id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE comics
  ADD COLUMN is_checked boolean DEFAULT FALSE;

ALTER TABLE volume
  ADD COLUMN is_checked boolean DEFAULT FALSE;

ALTER TABLE issue
  ADD COLUMN is_checked boolean DEFAULT FALSE;

UPDATE comics
  SET is_checked = TRUE;

UPDATE volume
  SET is_checked = TRUE;

UPDATE issue
	SET is_checked = TRUE;
  
ALTER TABLE character ADD card text;

ALTER TABLE issue ADD card text;

ALTER TABLE messages ADD COLUMN msg_time timestamp;

ALTER TABLE comments ADD COLUMN comment_time timestamp;

CREATE TABLE ucrating  (user_id Integer NOT NULL, comics_id Integer NOT NULL, rating Real NOT NULL);
ALTER TABLE  ucrating  ADD CONSTRAINT  c_rated_by  FOREIGN KEY ( user_id ) REFERENCES  users  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE  ucrating  ADD CONSTRAINT  c_rated  FOREIGN KEY ( comics_id ) REFERENCES  comics  ( comics_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE  ucrating  ADD CONSTRAINT  Key20 PRIMARY KEY (comics_id,user_id);

CREATE TABLE uvrating  (user_id Integer NOT NULL, volume_id Integer NOT NULL, rating Real NOT NULL);
ALTER TABLE  uvrating  ADD CONSTRAINT  v_rated_by  FOREIGN KEY ( user_id ) REFERENCES  users  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE  uvrating  ADD CONSTRAINT  v_rated  FOREIGN KEY ( volume_id ) REFERENCES  volume  ( volume_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE  uvrating  ADD CONSTRAINT  Key21 PRIMARY KEY (volume_id,user_id);

CREATE TABLE uirating  (user_id Integer NOT NULL, issue_id Integer NOT NULL, rating Real NOT NULL);
ALTER TABLE  uirating  ADD CONSTRAINT  i_rated_by  FOREIGN KEY ( user_id ) REFERENCES  users  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE  uirating  ADD CONSTRAINT  i_rated  FOREIGN KEY ( issue_id ) REFERENCES  issue  ( issue_id ) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE  uirating  ADD CONSTRAINT  Key22 PRIMARY KEY (issue_id,user_id);

ALTER TABLE character ADD source Varchar;
ALTER TABLE issue ADD source Varchar;
ALTER TABLE volume ADD source Varchar;
ALTER TABLE comics ADD source Varchar;

ALTER TABLE comics ALTER rating SET DEFAULT 0;
UPDATE comics SET rating = 0;
ALTER TABLE comics ALTER rating SET NOT NULL;
ALTER TABLE friends ADD is_confirmed boolean NOT NULL DEFAULT false;
ALTER TABLE friends DROP CONSTRAINT key19;
ALTER TABLE friends ADD id serial PRIMARY KEY;
ALTER TABLE friends ADD are_friends boolean NOT NULL DEFAULT true; 

ALTER TABLE MESSAGES ADD COLUMN SHOW_TO_SENDER BOOLEAN DEFAULT TRUE;
ALTER TABLE MESSAGES ADD COLUMN SHOW_TO_RECEIVER BOOLEAN DEFAULT TRUE;

ALTER TABLE users ADD COLUMN source Varchar DEFAULT 'Wikia';

---MISC Use _only_ before using marvel migration code (comics.sql, chars.sql)
UPDATE comics SET source = 'Wikia';
UPDATE volume SET source = 'Wikia';
UPDATE issue SET source = 'Wikia';
UPDATE character SET source = 'Wikia';
---/MISC

CREATE TABLE user_comments_news (
	news_id integer NOT NULL,
	user_id integer NOT NULL,
	comics_id integer,
	issue_id integer,
	volume_id integer,
	viewed boolean NOT NULL,
	CONSTRAINT pk_user_comments_news PRIMARY KEY(news_id),
	CONSTRAINT fk_user_comments_news_user_id FOREIGN KEY(user_id) REFERENCES users (user_id),
	CONSTRAINT fk_user_comments_news_comics_id FOREIGN KEY(comics_id) REFERENCES comics (comics_id),
	CONSTRAINT fk_user_comments_news_issue_id FOREIGN KEY(issue_id) REFERENCES issue (issue_id),
	CONSTRAINT fk_user_comments_news_volume_id FOREIGN KEY(volume_id) REFERENCES volume (volume_id)
);

CREATE SEQUENCE user_comments_news_news_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER SEQUENCE user_comments_news_news_id_seq
  OWNER TO "ComicsZoneRole";
ALTER TABLE user_comments_news
ALTER COLUMN news_id
SET DEFAULT nextval('user_comments_news_news_id_seq');
INSERT INTO user_comments_news (user_id, comics_id, issue_id, volume_id, viewed)
SELECT DISTINCT user_id, comics_id, issue_id, volume_id, TRUE
FROM comments
EXCEPT
SELECT user_id, comics_id, issue_id, volume_id, TRUE
FROM user_comments_news;
ALTER TABLE user_comments_news ADD COLUMN last_seen TIMESTAMP;
UPDATE user_comments_news SET last_seen = now();
