drop schema if exists octowight cascade;
create schema octowight;

-- Create resource table
create sequence octowight.resource_sequence;
drop table if exists octowight.resource;
create table octowight.resource(
	atom_id bigint not null,
	atom_locus varchar(256) not null,
	atom_type varchar(256) not null,
	resource_id bigint not null primary key,
	resource_type varchar(256) not null,
	unique(resource_id, resource_type)
);
