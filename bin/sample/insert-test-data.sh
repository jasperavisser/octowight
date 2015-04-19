#!/bin/bash
cd "$(dirname "$(readlink -f "${BASH_SOURCE[0]}")")"

pushd ..
bash run-sql-command.sh "delete from octowight.person;"
bash run-sql-command.sh "insert into octowight.person(id, name) values(nextval('octowight.person_sequence'), 'Picard');"
bash run-sql-command.sh "insert into octowight.person(id, name) values(nextval('octowight.person_sequence'), 'Riker');"
bash run-sql-command.sh "insert into octowight.person(id, name) values(nextval('octowight.person_sequence'), 'Barclay');"
bash run-sql-command.sh "insert into octowight.person(id, name) values(nextval('octowight.person_sequence'), 'Ishmael');"
bash run-sql-command.sh "insert into octowight.person(id, name) values(nextval('octowight.person_sequence'), 'Ahab');"
bash run-sql-command.sh "insert into octowight.person(id, name) values(nextval('octowight.person_sequence'), 'Queequeg');"

# TODO: if roles are inserted later, we won't see any captains yet :)
# TODO: this needs the dirty resource detector
# TODO: or stuff must be inserted in 1 transaction
