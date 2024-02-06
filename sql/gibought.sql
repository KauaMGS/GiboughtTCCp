/*Start Gibought.Sql */
--------------------------------------
/*Banco de Dados */
create database GBAPP collate Latin1_General_CI_AS
use GBAPP
--------------------------------------
/*Table UserCli*/
create table UserCli(
idCli int primary key identity(1,1),
nameCli nvarchar(250) NOT NULL,
emailCli nvarchar(200) NOT NULL,
passCli nvarchar(250) NOT NULL,
activedCli bit NOT NULL,
Constraint ak_UserCliEmail Unique(emailCli)
)

create table UserWp(
idWp int primary key identity(1,1),
nameWp nvarchar(250) NOT NULL,
emailWp nvarchar(200) NOT NULL,
passWp nvarchar(250) NOT NULL,
activedWp bit NOT NULL,
typeWp int NOT NULL,
dateCont date Not NULL,

Constraint ak_UserWpEmail Unique(emailWp)
)
insert into UserWp(nameWp,emailWp,passWp,activedWp,typeWp,dateCont)
values ('admin','admin@gmail.com','admin1234',1,0,'2021-09-19')

insert into UserWp(nameWp,emailWp,passWp,activedWp,typeWp,dateCont)
values ('funcionario','funcionario@gmail.com','funcionario1234',1,1,'2021-09-19')

--------------------------------------
/*Table Verification Version*/
create table Deluxe(
idDeluxe int primary key identity(1,1),
emailCli nvarchar(200) NOT NULL,
)
--------------------------------------
/*Table Message */
create table MessageCli(
idMessageCli int primary key identity(1,1),
nameCli nvarchar(400) NOT NULL,
emailCli nvarchar(200) NOT NULL,
msgCli nvarchar(4000) NOT NULL,
wpResp nvarchar(200) NOT NULL,
msgWp nvarchar(4000),
statusMsg bit NOT NUll,
dateMsg date NOT NUll,
)

drop table MessageCli
--------------------------------------
/*Table Account */
create table bankAccount(
idBankAccount int primary key identity(1,1),
nameBank nvarchar(200) NOT NULL,
idImage int NOT NULL,
valueBank money NOT NULL,
typeBank bit NOT NULL,
selectedBank bit NOT NULL,
lev nvarchar(30) NOT NULL,
lsv nvarchar(30) NOT NULL,
dateBankCreate date NOT NULL,
dateBankUpdate date NOT NULL,
idCli int,
Constraint fkUserCli FOREIGN KEY (idCli) REFERENCES UserCli (idCli)
)
--------------------------------------
/*Table Goals */
create table Goals(
idGoals int primary key identity(1,1),
nameGoal nvarchar(200) NOT NULL,
idImage int NOT NULL,
valueGoal money NOT NULL,
selectedGoal bit NOT NULL,
dateGoalCreate date NOT NULL,
dateGoalUpdate date NOT NULL,
typeGoal int NOT null,
valGoal money,
idBankAccount int,
idCli int,
Constraint fkGoalCli FOREIGN KEY (idCli) REFERENCES UserCli (idCli),
Constraint fkGoalBank FOREIGN KEY (idBankAccount) REFERENCES bankAccount (idBankAccount)
)

ALTER TABLE Goals NOCHECK CONSTRAINT fkGoalBank
--------------------------------------
/*Table Earnings */
create table earnAccount(
idEarn int primary key identity(1,1),
valueEarn money NOT NULL,
operation nvarchar(200) NOT NULL,
dateEarnCreate date NOT NULL,
idBankAccount int,
idCli int,
Constraint fkCli FOREIGN KEY (idCli) REFERENCES UserCli (idCli),
Constraint fkBank FOREIGN KEY (idBankAccount) REFERENCES bankAccount (idBankAccount)
)
--------------------------------------
/*Table Spendings */
create table spendAccount(
idSpend int primary key identity(1,1),
valueSpend money NOT NULL,
operation nvarchar(200) NOT NULL,
dateSpendCreate date NOT NULL,
idBankAccount int,
idCli int,
Constraint fkSpdCli FOREIGN KEY (idCli) REFERENCES UserCli (idCli),
Constraint fkSpdBank FOREIGN KEY (idBankAccount) REFERENCES bankAccount (idBankAccount)
)
--------------------------------------
/*Drops Tables and Select */
drop table UserCli
drop table UserWp
drop table bankAccount
drop table spendAccount
drop table earnAccount
drop table Goals
select * from bankAccount
select * from UserCli
select * from UserWp
select * from Goals
select * from MessageCli
select * from spendAccount
select * from earnAccount

select SUM(valueEarn) from earnAccount
inner join bankAccount on bankAccount.idBankAccount = bankAccount.selectedBank  
where Month(dateEarnCreate) = 8 and selectedBank = 1 
group by selectedBank

drop database GBAPP

drop Messagec