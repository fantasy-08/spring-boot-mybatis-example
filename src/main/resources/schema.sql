drop table if exists user;

create table user (
    id bigint primary key auto_increment,
    username varchar(32) not null unique,
    password varchar(60) not null,
    sex tinyint not null,
    created_time datetime(3) not null,
    updated_time datetime(3) not null
);

