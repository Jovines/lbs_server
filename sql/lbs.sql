drop database if exists lbs;
create database lbs;
show databases;

use lbs;

create table user
(
    phone    bigint primary key,
    nickname varchar(30),
    password char(30)
);

show tables;

select * from user;