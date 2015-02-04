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
ALTER TABLE  uvrating  ADD CONSTRAINT  Key22 PRIMARY KEY (issue_id,user_id);
