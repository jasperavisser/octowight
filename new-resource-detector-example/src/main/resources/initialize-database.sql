drop schema if exists octowight cascade;

create schema octowight;

drop table if exists octowight.book;

create table octowight.book(
	id bigint not null primary key,
	genre varchar(256) not null,
	title varchar(256) not null
);
