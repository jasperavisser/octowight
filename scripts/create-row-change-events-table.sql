drop schema if exists projectx;

create schema projectx;

drop table if exists projectx.row_change_events;

create table projectx.row_change_events(
	id bigint not null primary key,
	table_name varchar(256) not null,
	row_id bigint not null
);
