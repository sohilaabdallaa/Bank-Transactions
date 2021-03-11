use  banksystem;
CREATE TABLE Customer (
CustomerID int NOT NULL PRIMARY KEY auto_increment,
CustomerName VARCHAR(250),
CustomerAddress VARCHAR(250),
CustomerMobile VARCHAR(20),
 CustomerPassword varchar(25)
);
CREATE TABLE Bank_Account (
BankAcountID int NOT NULL PRIMARY KEY auto_increment,
BACreationDate date ,
BACurrentBalance Double ,
CustomerID int ,
FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID)
);

CREATE TABLE BankTransaction (
BankTransactionID int NOT NULL PRIMARY KEY auto_increment,
BTCreationDate Date,
BTAmount DOUBLE,
BTToAccount int ,
BTFromAccount int ,
FOREIGN KEY(BTFromAccount) REFERENCES Bank_Account(BankAcountID) ,
FOREIGN KEY(BTToAccount) REFERENCES Bank_Account(BankAcountID) 
);

INSERT INTO customer (CustomerName,CustomerAddress,CustomerMobile,CustomerPassword)
VALUES("sohila Abdalla ","Giza","0123456789","sohila");
INSERT INTO customer (CustomerName,CustomerAddress,CustomerMobile,CustomerPassword)
VALUES("Mona Adel ","Cairo","012479654","mona");
INSERT INTO customer (CustomerName,CustomerAddress,CustomerMobile,CustomerPassword)
VALUES("Shady Hassan ","Dokii","0127489542","shady");
INSERT INTO customer (CustomerName,CustomerAddress,CustomerMobile,CustomerPassword)
VALUES("Nour Hassan ","tahrer","012749542","nour");
INSERT INTO customer (CustomerName,CustomerAddress,CustomerMobile,CustomerPassword)
VALUES("hamza abdalla ","cairo","012749542","hamza");
SELECT * FROM customer ;

INSERT INTO bank_account(BACreationDate,BACurrentBalance,CustomerID)
VALUES('2020-12-11',1000,1);
INSERT INTO bank_account(BACreationDate,BACurrentBalance,CustomerID)
VALUES('2020-12-21',1000,2);
INSERT INTO bank_account(BACreationDate,BACurrentBalance,CustomerID)
VALUES('2020-12-01',1000,3);

SELECT * FROM bank_account;

INSERT INTO banktransaction(BTCreationDate,BTAmount,BTToAccount,BTFromAccount)
VALUES('2018-11-24',90,4,5);
INSERT INTO banktransaction(BTCreationDate,BTAmount,BTToAccount,BTFromAccount)
VALUES('2020-12-23',200,4,5);
INSERT INTO banktransaction(BTCreationDate,BTAmount,BTToAccount,BTFromAccount)
VALUES('2020-12-23',300,11,5);

INSERT INTO banktransaction(BTCreationDate,BTAmount,BTToAccount,BTFromAccount)
VALUES('2020-12-23',100,3,5);

SELECT * FROM banktransaction ;

SELECT * FROM customer ;

SELECT BTCreationDate FROM banktransaction WHERE BTFromAccount=5 AND BankTransactionID=11;

SELECT BTCreationDate FROM banktransaction WHERE BankTransactionID=5;

UPDATE bank_account SET  BACurrentBalance=(1000) where BankAcountID=4;
UPDATE bank_account SET   BACurrentBalance= 1000  WHERE BankAcountID=5;



SELECT * FROM banktransaction WHERE BankTransactionID=23;


delete from banktransaction where BankTransactionID=5;