-- create db
create database mb_main default character set utf8 ;
-- create table
use mb_main ;
create table tb_message(
        id int not null auto_increment primary key, 
        content text, 
        createdTime datetime
)engine=Innodb ;
-- add column
use mb_main ;
alter table tb_message add column userId int;

-- create slave
create database mb_slave default character set utf8 ;
-- create table
use mb_slave ;
create table tb_message(
        id int not null auto_increment primary key, 
        content text, 
        createdTime datetime
)engine=Innodb ;
-- insert 
insert into tb_message (
  content
  ,createdTime
) VALUES (
   'content3'
  ,null
)
use mb_slave ;
alter table tb_message add column userId int;


-- create user
use mb_main ;
create table tb_user(
        id int not null auto_increment primary key, 
        userName varchar(64) not null, 
        messageCount int(11)default 0
)engine=Innodb ;

-- alter table tb_message add column userId int(11) default 1 ;
create index idx_msg_uid on tb_message(userId) ;

insert into tb_user(userName) values('Lucy') ;
insert into tb_user(userName) values('Lily') ;
insert into tb_user(userName) values('Cathy') ;
insert into tb_user(userName) values('Polly, The Bird') ;
update tb_user set messageCount = (select count(*) from tb_message) where id = 1 ;
-- split one big table
use mb_main ;
create table tb_message_1 select * from tb_message where userId = 1 ;
create table tb_message_2 select * from tb_message where userId = 2 ;
create table tb_message_3 select * from tb_message where userId = 3 ;
create table tb_message_4 select * from tb_message where userId = 4 ;

alter table tb_message_1 modify column id int(11) not null auto_increment primary key ;
alter table tb_message_2 modify column id int(11) not null auto_increment primary key ;
alter table tb_message_3 modify column id int(11) not null auto_increment primary key ;
alter table tb_message_4 modify column id int(11) not null auto_increment primary key ;


-- db shards

-- create db
create database db_1 default character set utf8 ;
-- create table
use db_1 ;
create table tb_account_1(
        id int not null auto_increment primary key, 
        userId int ,
        areaId int ,
        account text
)engine=Innodb ;
create table tb_account_2(
        id int not null auto_increment primary key, 
        userId int ,
        areaId int ,
        account text
)engine=Innodb ;
create table tb_account_3(
        id int not null auto_increment primary key, 
        userId int ,
        areaId int ,
        account text
)engine=Innodb ;

-- create db
create database db_2 default character set utf8 ;
-- create table
use db_2 ;
create table tb_account_1(
        id int not null auto_increment primary key, 
        userId int ,
        areaId int ,
        account text
)engine=Innodb ;
create table tb_account_2(
        id int not null auto_increment primary key, 
        userId int ,
        areaId int ,
        account text
)engine=Innodb ;
create table tb_account_3(
        id int not null auto_increment primary key, 
        userId int ,
        areaId int ,
        account text
)engine=Innodb ;



-- custome table 
use mb_main ;
create table tb_s_property(
    id int not null AUTO_INCREMENT primary key , 
    cargoName varchar(32), 
    propName varchar(32), 
    colName varchar(32), 
    dataType varchar(32)
) ;
insert into tb_s_property(cargoName, propName, colName, dataType) values('book', 'ISBN','ISBN','string') ;
insert into tb_s_property(cargoName, propName, colName, dataType) values('book', 'author','author','string');
insert into tb_s_property(cargoName, propName, colName, dataType) values('book', 'publisher','publisher','string');
insert into tb_s_property(cargoName, propName, colName, dataType) values('crossStitch', 'gridNum','gridNum','int');
insert into tb_s_property(cargoName, propName, colName, dataType) values('crossStitch', 'backColor','backColor','string') ;
insert into tb_s_property(cargoName, propName, colName, dataType) values('crossStitch', 'size','size','string');
insert into tb_s_property(cargoName, propName, colName, dataType) values('crossStitch', 'brand','brand','string');

create table tb_cargo_book(
	id int not null AUTO_INCREMENT primary key , 
	name varchar(128), 
	description text, 
    storeCount int(11),
    price double, 
    onlineTime datetime, 
    ISBN varchar(64) not null, 
    author varchar(64), 
    publisher varchar(64)
);

create table tb_cargo_crossStitch(
	id int not null AUTO_INCREMENT primary key , 
	name varchar(128), 
	description text, 
    storeCount int(11), 
    price double, 
    onlineTime datetime, 
    gridNum int(11) not null, 
    backColor varchar(64), 
    size varchar(64), 
    brand varchar(64)
);
