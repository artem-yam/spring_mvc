--------------------------------------------------------
--  File created - �����������-��������-09-2019   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table BOOK_TAGS
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."BOOK_TAGS" 
   (	"BOOK" NUMBER(*,0), 
	"TAG" NUMBER(*,0)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table BOOKS
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."BOOKS" 
   (	"ID" NUMBER(*,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"TITLE" VARCHAR2(50 BYTE), 
	"AUTHOR" VARCHAR2(50 BYTE), 
	"IMAGE" BLOB, 
	"RATING" NUMBER DEFAULT 0, 
	"IS_DELETED" NUMBER(1,0) DEFAULT 0
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" 
 LOB ("IMAGE") STORE AS BASICFILE (
  TABLESPACE "SYSTEM" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION 
  NOCACHE LOGGING 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
--------------------------------------------------------
--  DDL for Table NOTIFICATION_TYPES
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."NOTIFICATION_TYPES" 
   (	"ID" NUMBER(*,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"TYPE" VARCHAR2(50 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table NOTIFICATIONS
--------------------------------------------------------
  CREATE TABLE "SYSTEM"."NOTIFICATIONS" 
   (	"ID" NUMBER(*,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 ORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"BOOK" NUMBER(*,0), 
	"CONTENT" VARCHAR2(50 BYTE), 
	"CATEGORY" VARCHAR2(50 BYTE), 
	"TYPE" VARCHAR2(50 BYTE), 
	"DATE" DATE DEFAULT sysdate
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table AVAILABLE_TAGS
--------------------------------------------------------

  CREATE TABLE "SYSTEM"."AVAILABLE_TAGS" 
   (	"ID" NUMBER(*,0) GENERATED ALWAYS AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE , 
	"TAG" VARCHAR2(50 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into SYSTEM.BOOK_TAGS
SET DEFINE OFF;
REM INSERTING into SYSTEM.BOOKS
SET DEFINE OFF;
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Fight Club','Chuck Palahniuk','0','1');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('The Trial','Franz Kafka','0','1');

Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Jewels of Nizam','Geeta Devi','5','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Cakes & Bakes','Sanjeev Kapoor','5','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Jamie''s Kitchen','Jamie Oliver','4','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Inexpensive Family Meals','Simon Holst','3','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Paleo Slow Cooking','Chrissy Gawer','1','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Cook Like an Italian','Toble Puttock','3','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Suneeta Vaswani','Geeta Devi','5','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Jamie Does','Jamie Oliver','3','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Jamie''s Italy','Jamie Oliver','5','0');
Insert into SYSTEM.BOOKS (TITLE,AUTHOR,RATING,IS_DELETED) values ('Vegetables Cookbook','Matthew Biggs','3','0');
REM INSERTING into SYSTEM.NOTIFICATION_TYPES
SET DEFINE OFF;
Insert into SYSTEM.NOTIFICATION_TYPES (TYPE) values ('ADD_BOOK');
Insert into SYSTEM.NOTIFICATION_TYPES (TYPE) values ('SEARCH');
Insert into SYSTEM.NOTIFICATION_TYPES (TYPE) values ('RATING');
REM INSERTING into SYSTEM.NOTIFICATIONS
SET DEFINE OFF;
Insert into SYSTEM.NOTIFICATIONS (BOOK,CONTENT,CATEGORY,TYPE,"DATE") values ('1',null,'Must Read Titles','1',to_date('01.01.19','DD.MM.RR'));
Insert into SYSTEM.NOTIFICATIONS (BOOK,CONTENT,CATEGORY,TYPE,"DATE") values ('2',null,'Must Read Titles','1',to_date('01.01.19','DD.MM.RR'));
REM INSERTING into SYSTEM.AVAILABLE_TAGS
SET DEFINE OFF;
Insert into SYSTEM.AVAILABLE_TAGS (TAG) values ('Must Read Titles');
Insert into SYSTEM.AVAILABLE_TAGS (TAG) values ('Best Of List');
Insert into SYSTEM.AVAILABLE_TAGS (TAG) values ('Classic Novels');
Insert into SYSTEM.AVAILABLE_TAGS (TAG) values ('Non Fiction');
--------------------------------------------------------
--  DDL for Index BOOKS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."BOOKS_PK" ON "SYSTEM"."BOOKS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index NOTIFICATION_TYPES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."NOTIFICATION_TYPES_PK" ON "SYSTEM"."NOTIFICATION_TYPES" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index NOTIFICATIONS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."NOTIFICATIONS_PK" ON "SYSTEM"."NOTIFICATIONS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index TAGS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYSTEM"."TAGS_PK" ON "SYSTEM"."AVAILABLE_TAGS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  Constraints for Table BOOK_TAGS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."BOOK_TAGS" MODIFY ("BOOK" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."BOOK_TAGS" MODIFY ("TAG" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table BOOKS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."BOOKS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."BOOKS" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."BOOKS" MODIFY ("AUTHOR" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."BOOKS" ADD CONSTRAINT "BOOKS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table NOTIFICATION_TYPES
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."NOTIFICATION_TYPES" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."NOTIFICATION_TYPES" ADD CONSTRAINT "NOTIFICATION_TYPES_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table NOTIFICATIONS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."NOTIFICATIONS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."NOTIFICATIONS" ADD CONSTRAINT "NOTIFICATIONS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table AVAILABLE_TAGS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."AVAILABLE_TAGS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SYSTEM"."AVAILABLE_TAGS" ADD CONSTRAINT "TAGS_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table BOOK_TAGS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."BOOK_TAGS" ADD CONSTRAINT "BOOK_TAGS_BOOKS_ID_FK" FOREIGN KEY ("BOOK")
	  REFERENCES "SYSTEM"."BOOKS" ("ID") ENABLE;
  ALTER TABLE "SYSTEM"."BOOK_TAGS" ADD CONSTRAINT "BOOK_TAGS_TAGS_ID_FK" FOREIGN KEY ("TAG")
	  REFERENCES "SYSTEM"."AVAILABLE_TAGS" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NOTIFICATIONS
--------------------------------------------------------

  ALTER TABLE "SYSTEM"."NOTIFICATIONS" ADD CONSTRAINT "NOTIFICATIONS_BOOKS__FK" FOREIGN KEY ("BOOK")
	  REFERENCES "SYSTEM"."BOOKS" ("ID") ENABLE;
