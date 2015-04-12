drop schema if exists octowight cascade;

create schema octowight;

create sequence octowight.event_sequence;

drop table if exists octowight.atom_change_events;

create table octowight.atom_change_events(
	id bigint not null primary key,
	atom_id bigint not null,
	atom_locus varchar(256) not null,
	atom_type varchar(256) not null
);
