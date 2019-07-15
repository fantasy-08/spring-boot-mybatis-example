
-- user

drop table if exists user;

create table user (
    id bigint unsigned primary key auto_increment,
    username varchar(32) not null,
    password varchar(60) not null,
    sex tinyint unsigned not null,
    created_time datetime(3) not null,
    updated_time datetime(3) not null
);

create unique index uk_user_username on user (username);


