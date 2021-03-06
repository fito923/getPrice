create table  GROUP_COMPANY
(ID INT PRIMARY KEY,
NAME VARCHAR(50)
);
INSERT INTO   GROUP_COMPANY VALUES (1,'ZARA');
INSERT INTO   GROUP_COMPANY VALUES (2,'PULL&BEAR');
INSERT INTO   GROUP_COMPANY VALUES (3,'MASSIMO DUTTI');
INSERT INTO   GROUP_COMPANY VALUES (4,'BERSHKA');
INSERT INTO   GROUP_COMPANY VALUES (5,'STRADIVARIUS');
INSERT INTO   GROUP_COMPANY VALUES (6,'OYSHO');
INSERT INTO   GROUP_COMPANY VALUES (7,'ZARA HOME');
INSERT INTO   GROUP_COMPANY VALUES (8,'UTERQ�E');


create table  PRICES
(BRAND_ID  int,
START_DATE TIMESTAMP  WITH TIME ZONE,
END_DATE TIMESTAMP  WITH TIME ZONE ,
PRICE_LIST int NOT NULL,
PRODUCT_ID  varchar(7) NOT NULL,
PRIORITY int,
PRICE NUMERIC(4,2), 
CURR VARCHAR(3) );

ALTER TABLE PRICES
    ADD FOREIGN KEY (BRAND_ID) 
    REFERENCES GROUP_COMPANY(ID);

ALTER TABLE PRICES
    ADD PRIMARY KEY (PRICE_LIST,PRODUCT_ID) ;

INSERT INTO PRICES VALUES(
1,
parsedatetime('2020-06-14-00.00.00 GMT+2', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+2'),
parsedatetime('2020-12-31-23.59.59 GMT+1', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+1')
,1,'35455',0,35.50,'EUR');

INSERT INTO PRICES VALUES(
1,
parsedatetime('2020-06-14-15.00.00 GMT+2', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+2'),
parsedatetime('2020-06-14-18.30.00 GMT+2', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+2')
,2,'35455',1,25.45,'EUR');

INSERT INTO PRICES VALUES(
1,
parsedatetime('2020-06-15-00.00.00 GMT+2', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+2'),
parsedatetime('2020-06-15-11.00.00 GMT+2', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+2'),
3,'35455',1,30.50,'EUR');

INSERT INTO PRICES VALUES(
1,
parsedatetime('2020-06-15-16.00.00 GMT+2', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+2'),
parsedatetime('2020-12-31-23.59.59 GMT+1', 'yyyy-MM-dd-hh.mm.ss', 'es', 'GMT+1'),
4,'35455',1,38.95,'EUR');


CREATE SEQUENCE HIBERNATE_SEQUENCE;

create table  LOGIN_USER
(
USER VARCHAR not null,
PASSWORD VARCHAR not null,
STATE int ,
CREATE_DATE TIMESTAMP);


ALTER TABLE LOGIN_USER
    ADD PRIMARY KEY (USER) ;

INSERT INTO LOGIN_USER VALUES('fito923','123asde@.eaA',0,SYSDATE);