drop schema if exists octowight;

create schema octowight;

create sequence octowight.event_sequence;

drop table if exists octowight.row_change_events;

create table octowight.row_change_events(
	id bigint not null primary key,
	table_name varchar(256) not null,
	row_id bigint not null
);
