drop schema if exists octowight cascade;
create schema octowight;

create sequence octowight.resource_sequence;
create sequence octowight.resource_root_sequence;
create sequence octowight.resource_version_sequence;

-- Create resource table
drop table if exists octowight.resource_root;
create table octowight.resource_root(
	id bigint not null primary key default nextval('octowight.resource_root_sequence'),
	atom_id bigint not null,
	atom_locus varchar(256) not null,
	atom_type varchar(256) not null,
	resource_id bigint not null default nextval('octowight.resource_sequence'),
	resource_type varchar(256) not null,
	version bigint not null default nextval('octowight.resource_version_sequence'),
	unique(resource_id, resource_type)
);
