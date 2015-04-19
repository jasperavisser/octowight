drop schema if exists octowight cascade;
create schema octowight;

-- Create atom change event table
create sequence octowight.event_sequence;
drop table if exists octowight.atom_change_events;
create table octowight.atom_change_events(
	id bigint not null primary key,
	atom_id bigint not null,
	atom_locus varchar(256) not null,
	atom_type varchar(256) not null
);

-- Create atom change event trigger functions
create or replace function push_atom_insert_or_update_event()
	returns trigger as
	$$
	begin
		insert into octowight.atom_change_events(id, atom_id, atom_locus, atom_type)
			values(nextval('octowight.event_sequence'), new.id, tg_table_schema, tg_table_name);
		return null;
	end;
	$$ language plpgsql;

create or replace function push_atom_delete_or_update_event()
	returns trigger as
	$$
	begin
		insert into octowight.atom_change_events(id, atom_id, atom_locus, atom_type)
			values(nextval('octowight.event_sequence'), old.id, tg_table_schema, tg_table_name);
		return null;
	end;
	$$ language plpgsql;

-- Create person table (TODO: move to another script)
create sequence octowight.person_sequence;
drop table if exists octowight.person;
create table octowight.person(
	id bigint not null primary key,
	name varchar(256) not null
);

-- Create role table
create sequence octowight.role_sequence;
drop table if exists octowight.role;
create table octowight.role(
	id bigint not null primary key,
	person bigint not null,
	type varchar(256) not null,
	foreign key (person) references octowight.person(id)
);

-- Create person change event triggers
drop trigger if exists person_insert_or_update on octowight.person;
create trigger person_insert_or_update
	after insert or update on octowight.person
	for each row
	execute procedure push_atom_insert_or_update_event();

drop trigger if exists person_delete_or_update on octowight.person;
create trigger person_delete_or_update
	after delete or update on octowight.person
	for each row
	execute procedure push_atom_delete_or_update_event();

-- Create role change event triggers
drop trigger if exists role_insert_or_update on octowight.role;
create trigger role_insert_or_update
	after insert or update on octowight.role
	for each row
	execute procedure push_atom_insert_or_update_event();

drop trigger if exists role_delete_or_update on octowight.role;
create trigger role_delete_or_update
	after delete or update on octowight.role
	for each row
	execute procedure push_atom_delete_or_update_event();
