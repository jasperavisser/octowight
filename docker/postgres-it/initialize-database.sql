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

create sequence octowight.book_sequence;
drop table if exists octowight.book;
create table octowight.book(
	id bigint not null primary key,
	genre varchar(256) not null,
	title varchar(256) not null
);

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

drop trigger if exists book_insert_or_update on octowight.book;
create trigger book_insert_or_update
	after insert or update on octowight.book
	for each row
	execute procedure push_atom_insert_or_update_event();

drop trigger if exists book_delete_or_update on octowight.book;
create trigger book_delete_or_update
	after delete or update on octowight.book
	for each row
	execute procedure push_atom_delete_or_update_event();
