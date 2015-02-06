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
  
ALTER TABLE comics ALTER rating SET DEFAULT 0;
UPDATE comics SET rating = 0;
ALTER TABLE comics ALTER rating SET NOT NULL;
ALTER TABLE friends ADD is_confirmed boolean NOT NULL DEFAULT false;
ALTER TABLE friends DROP CONSTRAINT key19;
ALTER TABLE friends ADD id serial PRIMARY KEY;
ALTER TABLE friends ADD are_friends boolean NOT NULL DEFAULT true; 

ALTER TABLE MESSAGES ADD COLUMN MSG_TIME TIMESTAMP;
ALTER TABLE MESSAGES ADD COLUMN SHOW_TO_SENDER BOOLEAN DEFAULT TRUE;
ALTER TABLE MESSAGES ADD COLUMN SHOW_TO_RECEIVER BOOLEAN DEFAULT TRUE; 