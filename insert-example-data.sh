#!/bin/bash
bash run-sql-command.sh "delete from octowight.book;"
bash run-sql-command.sh "insert into octowight.book(id, title, genre) values(nextval('octowight.book_sequence'), 'Dune', 'scifi');"
bash run-sql-command.sh "insert into octowight.book(id, title, genre) values(nextval('octowight.book_sequence'), 'Dracula', 'horror');"
bash run-sql-command.sh "insert into octowight.book(id, title, genre) values(nextval('octowight.book_sequence'), 'Do androids dream of electric sheep', 'scifi');"
bash run-sql-command.sh "insert into octowight.book(id, title, genre) values(nextval('octowight.book_sequence'), 'Catcher in the rye', 'realistic fiction');"
