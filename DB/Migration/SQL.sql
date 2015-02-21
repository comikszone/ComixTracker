
CREATE TABLE "COMICS"(
 "name_lowercase" Varchar,
 "comics_id" number NOT NULL,
 "name" Varchar NOT NULL,
 "description" Text,
 "image" Varchar,
 "rating" Real,
 "rel_date" Interval 
)

ALTER TABLE "COMICS" ADD CONSTRAINT  PRIMARY KEY ("comics_id")
;

CREATE TABLE "CHARREFS"(
 "char_id" number NOT NULL,
 "issue_id" number NOT NULL
)
;

ALTER TABLE "CHARREFS" ADD CONSTRAINT PRIMARY KEY ("charver_id","issue_id")
;

CREATE TABLE "CHARACTER"(
 "name_lowercase" Varchar,
 "char_id" number NOT NULL,
 "name" Varchar NOT NULL,
 "description" Text,
 "image" Varchar,
 "universe" Varchar,
 "real_name" Varchar,
 "realm" Varchar
)
;

ALTER TABLE "CHARACTER" ADD CONSTRAINT  PRIMARY KEY ("char_id")
;

CREATE TABLE "ISSUE"(
"name_lowercase" Varchar,
 "issue_id" number NOT NULL,
 "name" Varchar NOT NULL,
 "description" Text,
 "image" Bytea,
 "rating" Real,
 "imprint" Varchar,
 "rel_date" Date,
 "publisher" Varchar,
 "volume_id" number NOT NULL
)
;

ALTER TABLE "ISSUE" ADD CONSTRAINT  PRIMARY KEY ("issue_id")
;

CREATE TABLE "VOLUME"(
"name_lowercase" Varchar,
 "volume_id" number NOT NULL,
 "name" Varchar NOT NULL,
 "description" Text,
 "image" Bytea,
 "rating" Real,
 "comics_id" number NOT NULL
)
;


ALTER TABLE "VOLUME" ADD CONSTRAINT  PRIMARY KEY ("volume_id")
;

ALTER TABLE "VOLUME" ADD CONSTRAINT "has volumes" FOREIGN KEY ("comics_id") REFERENCES "COMICS" ("comics_id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "ISSUE" ADD CONSTRAINT "has issues" FOREIGN KEY ("volume_id") REFERENCES "VOLUME" ("volume_id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "CHARREFS" ADD CONSTRAINT "can be meet in" FOREIGN KEY ("char_id") REFERENCES "CHARVER" ("char_id") ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE "CHARACTER" ADD CONSTRAINT "chars in issues" FOREIGN KEY ("issue_id") REFERENCES "ISSUE" ("issue_id") ON DELETE NO ACTION ON UPDATE NO ACTION
;


