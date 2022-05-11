--- drop database if exists
drop schema if exists userdb;

create schema userdb;

use userdb;

create table users (
    username varchar(32) not null,
    email varchar(256) not null,
    password varchar(256) not null,
    primary key(username)
);