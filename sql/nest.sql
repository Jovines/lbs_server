drop database if exists nest;
create database nest;
show databases;

use nest;

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

create table lifeCircle_message_item
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

create table view_records
(
    id        bigint primary key auto_increment,
    message_id bigint,
    check_user bigint,
    time      datetime
);

create table high_quality_user
(
    id         bigint primary key auto_increment,
    user       bigint,
    join_time   datetime default current_timestamp,
    goodReason text
);

create table activity
(
    id            bigint primary key auto_increment,
    activity_name varchar(225),
    content       text,
    time          timestamp default current_timestamp,
    front_cover   text,
    expired       int
);


create table comment
(
    id         bigint primary key auto_increment,
    message_id bigint,
    user_id    bigint,
    content    text,
    time       timestamp default current_timestamp
);

create table banner
(
    id         bigint primary key auto_increment,
    front_cover text
);
