create database vrtech;

use vrtech;

create table logradouros (
	id int auto_increment not null,
	municipio varchar(60) not null,
	estado varchar(2) not null,
	
	primary key (id)
);

create table pessoas (
	id int auto_increment not null,
	nome varchar(100) not null,
	idade int not null,
	logradouro_id int not null,
	
	primary key (id),
	foreign key (logradouro_id) references logradouros(id)
);