CREATE TABLE ITEM(
ID NUMBER,
CONSTRAINT ITEM_ID PRIMARY KEY(ID),
NAME NVARCHAR2(50),
DATECREATED TIMESTAMP,
LASTUPDATED TIMESTAMP,
DESCRIPTION NVARCHAR2(80)
);

SELECT *FROM ITEM;