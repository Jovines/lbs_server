create database lbs;
show databases;
use lbs;

create table user
(
    phone    bigint primary key,
    nickname varchar(30),
    password char(16)
);



show tables;


drop table user;

select * from user;