drop schema if exists octowight cascade;

create schema octowight;

create sequence octowight.huxtable_sequence;

drop table if exists octowight.huxtable;

create table octowight.huxtable(
	id bigint not null primary key,
	type varchar(32) not null
);
