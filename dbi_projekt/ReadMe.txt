Query:

load data infile 'branches.txt'
into table branches
fields terminated by ','
lines terminated by ';';

load data infile 'accounts.txt'
into table accounts
fields terminated by ','
lines terminated by ';';

load data infile 'tellers.txt'
into table tellers
fields terminated by ','
lines terminated by ';';

Die Files branches.txt, accounts.txt und tellers.txt müssen noch in den Ordner der DB kopiert werden, um dann die Query auszuführen.
Außerdem müssen sie danach wieder gelöscht werden, damit die db gedroppt werden kann

Auch darf die accounts.txt nicht gepusht werden, weil sie zu groß ist