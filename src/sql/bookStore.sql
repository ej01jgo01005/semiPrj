  CREATE TABLE "SCOTT"."BOOKSTORE" 
   (	"BOOKNUM" NUMBER(5,0), 
	"GENRE" VARCHAR2(10 BYTE), 
	"TITLE" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	"WRITER" VARCHAR2(50 BYTE) NOT NULL ENABLE, 
	"PUBLISHER" VARCHAR2(50 BYTE), 
	"KEYWORD" VARCHAR2(200 BYTE), 
	"SUMMARY" VARCHAR2(1000 BYTE), 
	 PRIMARY KEY ("BOOKNUM");

  CREATE TABLE "SCOTT"."BOOK" 
   (	"BCODE" NUMBER(5,0), 
	"BOCODE" NUMBER(5,0), 
	 PRIMARY KEY ("BCODE");

CREATE SEQUENCE SEQ_B_CODE INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_BO_CODE INCREMENT BY 1 START WITH 1;


------

  CREATE TABLE "SCOTT"."BOOKLIST" 
   (	"BBNUM" NUMBER(10,0), 
	"BISBN" VARCHAR2(18 BYTE) NOT NULL ENABLE, 
	"BTITLE" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"BWRITER" VARCHAR2(200 BYTE) NOT NULL ENABLE, 
	"BPUBLISHER" VARCHAR2(200 BYTE), 
	"BAGE" VARCHAR2(50 BYTE), 
	"BCAUSE" VARCHAR2(500 BYTE), 
	 PRIMARY KEY ("BBNUM");

  CREATE TABLE "SCOTT"."BOOKBUY" 
   (	"BBCODE" NUMBER(5,0), 
	"BBOCODE" NUMBER(5,0), 
	 PRIMARY KEY ("BBCODE");

CREATE SEQUENCE SEQ_BB_CODE INCREMENT BY 1 START WITH 1;
CREATE SEQUENCE SEQ_BBO_CODE INCREMENT BY 1 START WITH 1;