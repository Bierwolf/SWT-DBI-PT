Query:

SET foreign_key_checks=0;
set unique_checks=0;
SET autocommit=0;
load data infile 'branches.txt'
into table branches
fields terminated by ','
lines terminated by ';\n';

load data infile 'accounts.txt'
into table accounts
fields terminated by ','
lines terminated by ';\n';

load data infile 'tellers.txt'
into table tellers
fields terminated by ','
lines terminated by ';\n';

COMMIT;
SET unique_checks=1;
SET foreign_key_checks=1;

Die Files branches.txt, accounts.txt und tellers.txt m�ssen noch in den Ordner der DB kopiert werden, um dann die Query auszuf�hren.
Au�erdem m�ssen sie danach wieder gel�scht werden, damit die db gedroppt werden kann
Db kann momentan nicht aus Eclispe gedroppt werden.

Auch darf die accounts.txt nicht gepusht werden, weil sie zu gro� ist