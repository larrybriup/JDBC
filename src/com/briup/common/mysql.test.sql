create table person (
	id int unsigned not null,
	name varchar(30) not null,
	age int unsigned not null,
	primary key (id)
);

create table student (
	id int unsigned not null auto_increment, 
	mydate date not null,
	primary key (id)
);