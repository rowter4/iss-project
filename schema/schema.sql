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


create table po (
    ord_id char(8) not null,
    username varchar(256) not null,
    order_date date default (current_date),
    primary key(ord_id),
    
    constraint fk_username
		foreign key(username)
        references users(username)
);



create table individual_item (
    item_id int not null auto_increment,
    amount float(5,2),
	price float(10,2),
    
    -- foreign keys
    ord_id char(8),

    -- keys
    primary key(item_id),

    constraint fk_ord_id 
        foreign key(ord_id) 
        references po(ord_id)
);