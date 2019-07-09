
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


-- person

drop table if exists person;

create table person (
    id bigint unsigned primary key auto_increment,
    name varchar(32) not null,
    identity_id bigint unsigned not null
);

create unique index uk_person_identity_id on person (identity_id);


-- identity

drop table if exists identity;

create table identity (
    id bigint unsigned primary key auto_increment,
    number varchar(20) not null
);

create unique index uk_identity_number on identity (number);


-- phone

drop table if exists phone;

create table phone (
    id bigint unsigned primary key auto_increment,
    number varchar(15) not null,
    person_id bigint unsigned not null
);

create index idx_phone_person_id on phone (person_id);


-- project

drop table if exists project;

create table project (
    id bigint unsigned primary key auto_increment,
    name varchar(64) not null
);


-- project_person

drop table if exists project_person;

create table project_person (
    id bigint unsigned primary key auto_increment,
    project_id bigint unsigned not null,
    person_id bigint unsigned not null
);

create unique index uk_project_person_project_id_person_id on project_person (project_id, person_id);
create unique index uk_project_person_person_id_project_id on project_person (person_id, project_id);
