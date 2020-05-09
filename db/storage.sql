drop database if exists docmoto;
create database docmoto;
use docmoto;

DROP user IF EXISTS 'motoDoc'@'localhost';
CREATE USER 'motoDoc'@'localhost' IDENTIFIED BY 'adminadmin';
GRANT ALL ON storage.* TO 'motoDoc'@'localhost';

create table Fornitore(
	p_iva		varchar(10)		not null primary key,
    nome		varchar(30) 	not null,
    indirizzo	varchar(50)		not null,
    fax			varchar(10)			not null
);

create table Deposito(
	codiceAlfanumerico varchar(4)	not null primary key,
    indirizzo	varchar(50)		not null
    );

create table Scaffale(
	codice				int			not null primary key,
    codiceAlfanumerico varchar(4)	not null,
    foreign key (codiceAlfanumerico) references Deposito(codiceAlfanumerico)
    );

create table Prodotto(
	codiceProd		int 		not null primary key,
    descrizione	varchar(100)	not null,
    prezzo 		double			not null,
    marca		varchar(10)		not null,
    disponibilità enum('y' , 'n') not null,
    p_iva		varchar(10)			not null,
    codiceAlfanumerico	varchar(4)	not null,
    codice		int				not null,
    foreign key (p_iva) references Fornitore (p_iva),
    foreign key (codiceAlfanumerico) references Deposito(codiceAlfanumerico),
    foreign key (codice) references Scaffale(codice)
);

create table Meccanica (
	impiego		varchar(10)		not null,
    codiceProd 		int				not null,
    foreign key (codiceProd) references Prodotto (codiceProd)
);    

create table Pneumatici(
	misura		varchar(9)			not null,
    stagione	varchar(10)			not null,
    codiceProd 		int				not null,
    foreign key (codiceProd) references Prodotto (codiceProd)
    );
    
create table Carrozzeria(
	materiale 	varchar(10)			not null,
    codiceProd 		int					not null,
    foreign key (codiceProd) references Prodotto(codiceProd)
    );
    
    create table Cliente(
	CF			varchar(16) 	not null primary key,
    nome		varchar(15)		not null,
    cognome		varchar(15)		not null,
    indirizzo	varchar(50)		not null
);

create table Ordine(
	codice		int				not null primary key,
    dataOrd		date			not null,
    npezzi		int 			not null,
    CF			varchar(16)		not null,
    foreign key(CF) references Cliente(CF)
);

create table Composto(
	codice 				int				not null,
    codiceProd 			int				not null,
    foreign key(codice) references Ordine(codice),
	foreign key(codiceProd) references Prodotto(codiceProd)    
);

create table Spedizione(
	codice			int  		not null primary key,
    dataSpedizione	date		not null,
	foreign key (codice) references Ordine (codice)
);

create table Telefono(
	numero 		varchar(10)		not null primary key,
    CF			varchar(16) 	not null,
    foreign key (CF) references Cliente(CF)
);
    
insert into Cliente (CF, nome, cognome, indirizzo)
values 	('PTTPLA00A24F912W', 'Paolo', 'Petta', 'Via s rocco 8'),
		('PTTNTN56E24G230A', 'Antonio', 'Petta', 'Via s rocco 8'),
        ('PTTNTN56E24G230B', 'Giuseppe', 'Amato', 'Via corallo 9'),
		('GPPNTN56E24G230B', 'Peppe', 'Cuofano', 'Via Franco 7'),
        ('LGLNTN56E24G230B', 'Luigi', 'Coppola', 'Via Napoli 9');
        
insert into Fornitore (p_iva, nome, indirizzo, fax)
values ('54555', 'Motopertutti', 'Via aztori', '0275468578'),
		('54758', 'Ricambidueruote', 'Via Clmente', '0452428745');
        
insert into Deposito (codiceAlfanumerico, indirizzo)
values ( 'A', 'Via aztori'),
		('B', 'Via lamia');

insert into Scaffale (codice, codiceAlfanumerico)
values (123, 'A'), (124, 'A'), (125, 'A'),(126, 'A'),(127, 'A'), (145, 'B'), (146, 'B'), (147, 'B'), (148, 'B'), (149, 'B');

insert into Prodotto (codiceProd, descrizione, prezzo, marca, disponibilità, p_iva, codiceAlfanumerico, codice)
values (2452, 'pilot', 55.00, 'Michelin', 'y', '54555', 'A', 123),
		(1574, 'free', 50.00, 'Pirelli', 'y', '54555', 'A', 124 ), 
        (1577, 'candela motore', 15.00, 'NGK', 'y', '54758', 'A', 125), 
        (1478, 'cambio elettronico', 200.50, 'Hirace', 'n', '54758', 'B', 145), 
        (1457, 'fianchetto', 50.0, 'Hirace', 'y', '54758', 'B', 146 ),
        (1744, 'serbatoio', 150.0, 'Hirace', 'n', '54758', 'B', 147),
		(1745, 'cavalletto', 25.0, 'Hirace', 'y', '54758', 'B', 148),
        (1845, 'parafango', 75.0, 'Hirace', 'y', '54758', 'A', 126),
        (1888, '4stag', 60.0, 'Michelin', 'n', '54758', 'A', 127);
        
    
insert into Pneumatici(misura, stagione, codiceProd)
values ('155\75R15', 'estiva', 2452), ('165\55R15', 'invernale', 1574), ('160\55R16', '4stagioni', 1888);

insert into Meccanica (impiego, codiceProd)
values ('candela', 1577), ('cambio', 1478), ('cavalletto', 1745);

insert into Carrozzeria (materiale, codiceProd)
values ('carbonio', 1457), ('alluminio', 1744), ('alluminio', 1845);

insert into Ordine (codice, dataOrd, npezzi, CF)
values (1234,  '2020-01-10', 2, 'PTTNTN56E24G230B'),
		(1235, '2020-01-02', 3, 'PTTPLA00A24F912W'),
        (1236, '2020-01-03', 2, 'PTTPLA00A24F912W'),
        (1237, '2020-01-20', 2, 'LGLNTN56E24G230B'),
		(1238, '2020-01-15', 1, 'GPPNTN56E24G230B'),
        (1239, '2020-01-16', 2, 'GPPNTN56E24G230B');
       

insert into Composto (codice, codiceProd)
values (1234, 2452), (1234, 1574), (1235, 1577), (1235, 1574), (1235, 1478), (1236, 1574), (1236, 2452), (1237, 1745),(1237, 1577),(1238, 2452), (1239, 1577), (1239, 1888);

insert into Spedizione ( codice, dataSpedizione)
values (1234, '2020-01-12'), (1235, '2020-01-05'), (1238, '2020-01-22'), (1239, '2020-01-23');

insert into Telefono (numero, CF)
values ('3243757854', 'PTTPLA00A24F912W'),
		('3243875956', 'PTTNTN56E24G230A'),
        ('3459865748', 'PTTNTN56E24G230B'),
		('3549876895', 'GPPNTN56E24G230B'),
        ('3405697858', 'LGLNTN56E24G230B');
        

		






    