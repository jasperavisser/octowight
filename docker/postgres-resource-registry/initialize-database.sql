drop schema if exists octowight cascade;
create schema octowight;

create sequence octowight.resource_sequence;

-- Create resource table
drop table if exists octowight.resource_root;
create table octowight.resource_root(
	atom_id bigint not null,
	atom_locus varchar(256) not null,
	atom_type varchar(256) not null,
	resource_id bigint not null primary key,
	resource_type varchar(256) not null,
	unique(resource_id, resource_type)
);

create sequence octowight.resource_element_sequence;
create table octowight.resource_element(
	id bigint not null primary key default nextval('octowight.resource_element_sequence'),
	atom_id bigint not null,
	atom_locus varchar(256) not null,
	atom_type varchar(256) not null,
	resource_id bigint not null,
	resource_type varchar(256) not null,
	unique(atom_id, atom_locus, atom_type, resource_id, resource_type)
);
