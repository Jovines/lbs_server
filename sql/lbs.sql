drop database if exists lbs;
create database lbs;
show databases;

use lbs;

create table user
(
    phone       bigint primary key,
    nickname    varchar(30),
    password    char(16),
    description text,
    avatar      text,
    lng    decimal(10),
    lat    decimal(10)
);

create table lifeCircleMessageItem
(
    id       bigint primary key auto_increment,
    user     bigint,
    title    varchar(50),
    content  TEXT,
    time     datetime,
    lng    decimal(10),
    lat    decimal(10)
);
