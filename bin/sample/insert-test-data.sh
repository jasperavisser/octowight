#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

pushd ..
bash run-sql-command.sh "delete from octowight.role; delete from octowight.person;"
bash run-sql-command.sh "insert into octowight.person(name) values('Picard');"
bash run-sql-command.sh "insert into octowight.person(name) values('Riker');"
bash run-sql-command.sh "insert into octowight.person(name) values('Barclay');"
bash run-sql-command.sh "insert into octowight.person(name) values('Ishmael');"
bash run-sql-command.sh "insert into octowight.person(name) values('Ahab');"
bash run-sql-command.sh "insert into octowight.person(name) values('Queequeg');"

bash run-sql-command.sh "insert into octowight.role(person, type) select id, 'harpooner' from octowight.person where name in ('Queequeg');"
bash run-sql-command.sh "insert into octowight.role(person, type) select id, 'captain' from octowight.person where name in ('Ahab', 'Picard');"
bash run-sql-command.sh "insert into octowight.role(person, type) select id, 'whaler' from octowight.person where name in ('Ahab', 'Ishmael', 'Queequeg');"
bash run-sql-command.sh "insert into octowight.role(person, type) select id, 'explorer' from octowight.person where name in ('Barclay', 'Picard', 'Riker');"
