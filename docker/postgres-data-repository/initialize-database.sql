drop schema if exists octowight cascade;
create schema octowight;

-- Create atom change event table
create sequence octowight.event_sequence;
drop table if exists octowight.atom_change_event;
create table octowight.atom_change_event(
	id bigint not null primary key default nextval('octowight.event_sequence'),
	atom_id bigint not null,
	atom_origin varchar(256) not null,
	atom_type varchar(256) not null
);

-- Create atom change event trigger functions
create or replace function log_atom_change_event(atom_id bigint, atom_origin varchar(256), atom_type varchar(256))
	returns void as $$ begin
		insert into octowight.atom_change_event(id, atom_id, atom_origin, atom_type)
			values(nextval('octowight.event_sequence'), atom_id, atom_origin, atom_type);
	end; $$ language plpgsql;

-- Create person table
create sequence octowight.person_sequence;
drop table if exists octowight.person;
create table octowight.person(
	id bigint not null primary key default nextval('octowight.person_sequence'),
	name varchar(256) not null
);

-- Create role table
create sequence octowight.role_sequence;
drop table if exists octowight.role;
create table octowight.role(
	id bigint not null primary key default nextval('octowight.role_sequence'),
	person bigint not null,
	name varchar(256) not null,
	foreign key (person) references octowight.person(id)
);

-- Create person change event triggers
create or replace function log_person_insert_or_update_event()
	returns trigger as $$ begin
		perform log_atom_change_event(new.id, 'octowight', 'person');
		return null;
	end; $$ language plpgsql;

create or replace function log_person_delete_or_update_event()
	returns trigger as $$ begin
		perform log_atom_change_event(old.id, 'octowight', 'person');
		return null;
	end; $$ language plpgsql;

drop trigger if exists person_insert_or_update on octowight.person;
create trigger person_insert_or_update after insert or update on octowight.person
	for each row execute procedure log_person_insert_or_update_event();

drop trigger if exists person_delete_or_update on octowight.person;
create trigger person_delete_or_update after delete or update on octowight.person
	for each row execute procedure log_person_delete_or_update_event();

-- Create role change event triggers
create or replace function log_role_insert_or_update_event()
	returns trigger as $$ begin
		perform log_atom_change_event(new.person, 'octowight', 'person');
		perform log_atom_change_event(new.id, 'octowight', 'role');
		return null;
	end; $$ language plpgsql;

create or replace function log_role_delete_or_update_event()
	returns trigger as $$ begin
		perform log_atom_change_event(old.person, 'octowight', 'person');
		perform log_atom_change_event(old.id, 'octowight', 'role');
		return null;
	end; $$ language plpgsql;

drop trigger if exists role_insert_or_update on octowight.role;
create trigger role_insert_or_update after insert or update on octowight.role
	for each row execute procedure log_role_insert_or_update_event();

drop trigger if exists role_delete_or_update on octowight.role;
create trigger role_delete_or_update after delete or update on octowight.role
	for each row execute procedure log_role_delete_or_update_event();
