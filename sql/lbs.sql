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
    lon         double,
    lat         double
);

create table lifeCircleMessageItem
(
    id      bigint primary key auto_increment,
    user    bigint,
    title   varchar(50),
    content TEXT,
    time    datetime,
    lon     double,
    lat     double,
    images  text
);

create table viewRecords
(
    id        bigint primary key auto_increment,
    messageId bigint,
    checkUser bigint,
    time      datetime
);

create table highQualityUser
(
    id bigint primary key auto_increment,
    user bigint,
    joinTime datetime default current_timestamp,
    goodReason text
);

show tables
