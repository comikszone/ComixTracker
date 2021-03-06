﻿/*
Created: 09.11.2014
Modified: 24.11.2014
Model: PostgreSQL 9.2
Database: PostgreSQL 9.2
*/


-- Create tables section -------------------------------------------------

-- Table COMICS

CREATE TABLE COMICS (
 comics_id Serial NOT NULL,
 name Varchar NOT NULL,
  description  Text,
  image  Text,
  rating  Real,
  votes  Integer,
  publisher_id  Integer,
  imprint_id  Integer,
  start_date  Date,
  end_date  Date,
  in_progress  Date
)
WITH (OIDS=FALSE)
;

-- Create indexes for table COMICS

CREATE INDEX  comi_Name  ON  COMICS  (LOWER(name))
;

CREATE UNIQUE INDEX  comi_ID  ON  COMICS  ( comics_id )
;

CREATE INDEX  comi_Pub  ON  COMICS  ( publisher_id )
;

CREATE INDEX  comi_Imp  ON  COMICS  ( imprint_id )
;

-- Add keys for table COMICS

ALTER TABLE  COMICS  ADD CONSTRAINT  Key1  PRIMARY KEY ( comics_id )
;

ALTER TABLE  COMICS  ADD CONSTRAINT  comics_id  UNIQUE ( comics_id )
;

-- Table CHARREFS

CREATE TABLE  CHARREFS (
  char_id  Integer NOT NULL,
  issue_id  Integer NOT NULL
)
WITH (OIDS=FALSE)
;

-- Create indexes for table CHARREFS

CREATE INDEX  charref_All  ON  CHARREFS  ( char_id , issue_id )
;

-- Add keys for table CHARREFS

ALTER TABLE  CHARREFS  ADD CONSTRAINT  Key3  PRIMARY KEY ( char_id , issue_id )
;

-- Table CHARACTER

CREATE TABLE  CHARACTER (
  char_id  Serial NOT NULL,
  name  Varchar NOT NULL,
  real_name  Varchar,
  description  Text,
  image  Text,
  realm_id  Bigint,
  universe_id  Integer
)
WITH (OIDS=FALSE)
;

-- Create indexes for table CHARACTER

CREATE INDEX  chari_Name  ON  CHARACTER  (LOWER(name))
;

CREATE UNIQUE INDEX  chari_ID  ON  CHARACTER  ( char_id )
;

CREATE INDEX  chari_Realm  ON  CHARACTER  ( realm_id )
;

CREATE INDEX  chari_Univ  ON  CHARACTER  ( universe_id )
;

-- Add keys for table CHARACTER

ALTER TABLE  CHARACTER  ADD CONSTRAINT  Key4  PRIMARY KEY ( char_id )
;

ALTER TABLE  CHARACTER  ADD CONSTRAINT  char_id  UNIQUE ( char_id )
;

-- Table MESSAGES

CREATE TABLE  MESSAGES (
  msg_id  Serial NOT NULL,
  sender  Integer NOT NULL,
  receiver  Integer NOT NULL,
  title  Varchar NOT NULL,
  text  Text NOT NULL
)
WITH (OIDS=FALSE)
;

-- Create indexes for table MESSAGES

CREATE INDEX  msgi_Title  ON  MESSAGES  ( title )
;

-- Add keys for table MESSAGES

ALTER TABLE  MESSAGES  ADD CONSTRAINT  Key6  PRIMARY KEY ( msg_id )
;

ALTER TABLE  MESSAGES  ADD CONSTRAINT  msg_id  UNIQUE ( msg_id )
;

-- Table USERS

CREATE TABLE  USERS (
  user_id  Serial NOT NULL,
  nickname  Varchar NOT NULL,
  pass  Varchar NOT NULL,
  avatar  Bytea,
  sex  Integer DEFAULT 0 NOT NULL,
  birthday  Date,
  email  Varchar NOT NULL,
  online  Boolean DEFAULT false,
  banned  Boolean DEFAULT false NOT NULL
)
WITH (OIDS=FALSE)
;

-- Create indexes for table USERS

CREATE INDEX  usi_Name  ON  USERS  (LOWER(nickname))
;

CREATE UNIQUE INDEX  usi_ID  ON  USERS  ( user_id )
;

-- Add keys for table USERS

ALTER TABLE  USERS  ADD CONSTRAINT  Key7  PRIMARY KEY ( user_id )
;

ALTER TABLE  USERS  ADD CONSTRAINT  user_id  UNIQUE ( user_id )
;

ALTER TABLE  USERS  ADD CONSTRAINT  nickname  UNIQUE ( nickname )
;

-- Table USER_GROUP

CREATE TABLE  USER_GROUP (
  gname  Varchar NOT NULL,
  nickname  Varchar NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table USER_GROUP

ALTER TABLE  USER_GROUP  ADD CONSTRAINT  Key8  PRIMARY KEY ( gname , nickname )
;

-- Table ISSUE

CREATE TABLE  ISSUE (
  issue_id  Serial NOT NULL,
  name  Varchar NOT NULL,
  description  Text,
  img  Text,
  rating  Real,
  votes  Integer,
  rel_date  Date,
  volume_id  Integer NOT NULL
)
WITH (OIDS=FALSE)
;

-- Create indexes for table ISSUE

CREATE INDEX  issi_Name  ON  ISSUE  (LOWER(name))
;

CREATE UNIQUE INDEX  issi_ID  ON  ISSUE  ( issue_id )
;

CREATE INDEX  issi_Vol  ON  ISSUE  ( volume_id )
;

-- Add keys for table ISSUE

ALTER TABLE  ISSUE  ADD CONSTRAINT  Key9  PRIMARY KEY ( issue_id )
;

ALTER TABLE  ISSUE  ADD CONSTRAINT  issue_id  UNIQUE ( issue_id )
;

-- Table VOLUME

CREATE TABLE  VOLUME (
  volume_id  Serial NOT NULL,
  name  Varchar NOT NULL,
  description  Text,
  img  Text,
  rating  Real,
  votes  Integer,
  comics_id  Integer NOT NULL
)
WITH (OIDS=FALSE)
;

-- Create indexes for table VOLUME

CREATE INDEX  voli_Name  ON  VOLUME  (LOWER(name))
;

CREATE UNIQUE INDEX  voli_ID  ON  VOLUME  ( volume_id )
;

CREATE INDEX  voli_Com  ON  VOLUME  ( comics_id )
;

-- Add keys for table VOLUME

ALTER TABLE  VOLUME  ADD CONSTRAINT  Key10  PRIMARY KEY ( volume_id )
;

ALTER TABLE  VOLUME  ADD CONSTRAINT  volume_id  UNIQUE ( volume_id )
;

-- Table PROGRESS

CREATE TABLE  PROGRESS (
  user_id  Integer NOT NULL,
  issue_id  Integer NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table PROGRESS

ALTER TABLE  PROGRESS  ADD CONSTRAINT  Key11  PRIMARY KEY ( issue_id , user_id )
;

-- Table CHARVER

CREATE TABLE  CHARVER (
  char1  Integer NOT NULL,
  char2  Integer NOT NULL
)
WITH (OIDS=FALSE)
;

-- Create indexes for table CHARVER

CREATE INDEX  charveri_All  ON  CHARVER  ( char1 , char2 )
;

-- Add keys for table CHARVER

ALTER TABLE  CHARVER  ADD CONSTRAINT  Key13  PRIMARY KEY ( char2 , char1 )
;

-- Table UNIVERSE

CREATE TABLE  UNIVERSE (
  universe_id  Serial NOT NULL,
  name  Varchar NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table UNIVERSE

ALTER TABLE  UNIVERSE  ADD CONSTRAINT  Key14  PRIMARY KEY ( universe_id )
;

ALTER TABLE  UNIVERSE  ADD CONSTRAINT  universe_id  UNIQUE ( universe_id )
;

-- Table PUBLISHER

CREATE TABLE  PUBLISHER (
  publisher_id  Serial NOT NULL,
  name  Varchar NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table PUBLISHER

ALTER TABLE  PUBLISHER  ADD CONSTRAINT  Key15  PRIMARY KEY ( publisher_id )
;

ALTER TABLE  PUBLISHER  ADD CONSTRAINT  publisher_id  UNIQUE ( publisher_id )
;

-- Table IMPRINT

CREATE TABLE  IMPRINT (
  imprint_id  Serial NOT NULL,
  name  Varchar NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table IMPRINT

ALTER TABLE  IMPRINT  ADD CONSTRAINT  Key16  PRIMARY KEY ( imprint_id )
;

ALTER TABLE  IMPRINT  ADD CONSTRAINT  imprint_id  UNIQUE ( imprint_id )
;

-- Table COMMENTS

CREATE TABLE  COMMENTS (
  comment_id  Serial NOT NULL,
  comics_id  Integer,
  volume_id  Integer,
  issue_id  Integer,
  user_id  Integer NOT NULL,
  text  Text NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table COMMENTS

ALTER TABLE  COMMENTS  ADD CONSTRAINT  comment_id  PRIMARY KEY ( comment_id )
;

-- Table REALM

CREATE TABLE  REALM (
  realm_id  Serial NOT NULL,
  name  Varchar NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table REALM

ALTER TABLE  REALM  ADD CONSTRAINT  Key18  PRIMARY KEY ( realm_id )
;

ALTER TABLE  REALM  ADD CONSTRAINT  realm_id  UNIQUE ( realm_id )
;

-- Table FRIENDS

CREATE TABLE  FRIENDS (
  user1_id  Integer NOT NULL,
  user2_id  Integer NOT NULL
)
WITH (OIDS=FALSE)
;

-- Add keys for table FRIENDS

ALTER TABLE  FRIENDS  ADD CONSTRAINT  Key19  PRIMARY KEY ( user1_id , user2_id )
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE  MESSAGES  ADD CONSTRAINT  sent_by  FOREIGN KEY ( sender ) REFERENCES  USERS  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  MESSAGES  ADD CONSTRAINT  sent_to  FOREIGN KEY ( receiver ) REFERENCES  USERS  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  VOLUME  ADD CONSTRAINT  has_volumes  FOREIGN KEY ( comics_id ) REFERENCES  COMICS  ( comics_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  ISSUE  ADD CONSTRAINT  has_issues  FOREIGN KEY ( volume_id ) REFERENCES  VOLUME  ( volume_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  CHARREFS  ADD CONSTRAINT  chars_in_issues  FOREIGN KEY ( issue_id ) REFERENCES  ISSUE  ( issue_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  PROGRESS  ADD CONSTRAINT  has_in_list  FOREIGN KEY ( user_id ) REFERENCES  USERS  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  PROGRESS  ADD CONSTRAINT  progress_of  FOREIGN KEY ( issue_id ) REFERENCES  ISSUE  ( issue_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  CHARREFS  ADD CONSTRAINT  can_be_met_in  FOREIGN KEY ( char_id ) REFERENCES  CHARACTER  ( char_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  CHARVER  ADD CONSTRAINT  has_variations  FOREIGN KEY ( char1 ) REFERENCES  CHARACTER  ( char_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  CHARVER  ADD CONSTRAINT  variation_is  FOREIGN KEY ( char2 ) REFERENCES  CHARACTER  ( char_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  CHARACTER  ADD CONSTRAINT  from_universe  FOREIGN KEY ( universe_id ) REFERENCES  UNIVERSE  ( universe_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  COMICS  ADD CONSTRAINT  published  FOREIGN KEY ( publisher_id ) REFERENCES  PUBLISHER  ( publisher_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  COMICS  ADD CONSTRAINT  released  FOREIGN KEY ( imprint_id ) REFERENCES  IMPRINT  ( imprint_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  COMMENTS  ADD CONSTRAINT  written_by  FOREIGN KEY ( user_id ) REFERENCES  USERS  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  CHARACTER  ADD CONSTRAINT  from_realm  FOREIGN KEY ( realm_id ) REFERENCES  REALM  ( realm_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  FRIENDS  ADD CONSTRAINT  has_friend  FOREIGN KEY ( user1_id ) REFERENCES  USERS  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  FRIENDS  ADD CONSTRAINT  friend_of  FOREIGN KEY ( user2_id ) REFERENCES  USERS  ( user_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  COMMENTS  ADD CONSTRAINT  to_c  FOREIGN KEY ( comics_id ) REFERENCES  COMICS  ( comics_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  COMMENTS  ADD CONSTRAINT  to_v  FOREIGN KEY ( volume_id ) REFERENCES  VOLUME  ( volume_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  COMMENTS  ADD CONSTRAINT  to_i  FOREIGN KEY ( issue_id ) REFERENCES  ISSUE  ( issue_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE  USER_GROUP  ADD CONSTRAINT  in_group  FOREIGN KEY ( nickname ) REFERENCES  USERS  ( nickname ) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE MESSAGES OWNER TO "ComicsZoneRole";
ALTER TABLE USERS OWNER TO "ComicsZoneRole";
ALTER TABLE USER_GROUP OWNER TO "ComicsZoneRole";
ALTER TABLE FRIENDS OWNER TO "ComicsZoneRole";
ALTER TABLE PROGRESS OWNER TO "ComicsZoneRole";
ALTER TABLE COMMENTS OWNER TO "ComicsZoneRole";
ALTER TABLE ISSUE OWNER TO "ComicsZoneRole";
ALTER TABLE VOLUME OWNER TO "ComicsZoneRole";
ALTER TABLE COMICS OWNER TO "ComicsZoneRole";
ALTER TABLE PUBLISHER OWNER TO "ComicsZoneRole";
ALTER TABLE IMPRINT OWNER TO "ComicsZoneRole";
ALTER TABLE CHARACTER OWNER TO "ComicsZoneRole";
ALTER TABLE CHARVER OWNER TO "ComicsZoneRole";
ALTER TABLE CHARREFS OWNER TO "ComicsZoneRole";
ALTER TABLE UNIVERSE OWNER TO "ComicsZoneRole";
ALTER TABLE REALM OWNER TO "ComicsZoneRole";
