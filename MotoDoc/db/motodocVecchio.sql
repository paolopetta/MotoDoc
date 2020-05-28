drop database if exists docmoto;
create database docmoto;
use docmoto;

DROP user IF EXISTS 'motoDoc'@'localhost';
CREATE USER 'motoDoc'@'localhost' IDENTIFIED BY 'adminadmin';
GRANT ALL ON docmoto.* TO 'motoDoc'@'localhost';

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
	codiceProd		varchar(10)	not null primary key,
    nome			varchar(20)	not null,
    descrizione	varchar(100)	not null,
    img			varchar(300)	,
    prezzo 		double			not null,
    marca		varchar(10)		not null,
    disponibilit� enum('y' , 'n') not null,
    p_iva		varchar(10)			not null,
    codiceAlfanumerico	varchar(4)	not null,
    codice		int				not null,
    foreign key (p_iva) references Fornitore (p_iva),
    foreign key (codiceAlfanumerico) references Deposito(codiceAlfanumerico),
    foreign key (codice) references Scaffale(codice)
);

create table Meccanica (
	impiego		varchar(10)		not null,
    codiceProd 	varchar(10)					not null,
    foreign key (codiceProd) references Prodotto (codiceProd)
);    

create table Pneumatici(
	misura		varchar(9)			not null,
    stagione	varchar(10)			not null,
    codiceProd 	varchar(10)			not null,
    foreign key (codiceProd) references Prodotto (codiceProd)
    );
    
create table Carrozzeria(
	materiale 	varchar(10)			not null,
    codiceProd 	varchar(10)				not null,
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
    codiceProd 		varchar(10)				not null,
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

insert into Prodotto (codiceProd, nome, descrizione, img, prezzo, marca, disponibilit�, p_iva, codiceAlfanumerico, codice)
values ('2452', 'Gomme','pilot', 'https://aecbmesvcm.cloudimg.io/cdno/n/webp.png-lossless/https://blobs.dgadteamdev.com/b2c-experience-production/attachments/cjfv2om9u0rg40hqmmc9xb11o-moto-tyres-pilot-power-3-persp.full.png', 55.00, 'Michelin', 'y', '54555', 'A', 123),
    ('1574', 'Gomme Pirelli','free', 'https://d3nv2arudvw7ln.cloudfront.net/images/870/855/diablo_rosso_III_3_4,0.jpg', 50.00, 'Pirelli', 'y', '54555', 'A', 124 ),
        ('1577', 'Candela', 'candela motore', 'https://images-na.ssl-images-amazon.com/images/I/61Lj6AA7BeL._AC_SX466_.jpg',15.00, 'NGK', 'y', '54758', 'A', 125),
        ('1478', 'Cambio','cambio elettronico', 'https://www.carpimoto.it/Images/Products/Detail/Dynojet_Quick_Shifter_Sensor_Cambio_Elettronico_E4-103.jpg',200.50, 'Hirace', 'n', '54758', 'B', 145),
        ('1457', 'Fianchetto duke','fianchetto', 'https://www.motoricambiservice.com/ebay/readyproebayimages/CARENA-CODINO-SINISTRA-KTM-390-DUKE-ABS-2017-2019-9300804110028-LEFT-SIDE-REAR_462479.jpg',50.00, 'Hirace', 'y', '54758', 'B', 146 ),
        ('1744', 'Serbatoio','serbatoio ktm', 'https://static.bakeca.it/immagini/edf/edf788f7f5a206a5402962374cc575ca.jpg',150.00, 'Hirace', 'n', '54758', 'B', 147),
    ('1745', 'Cavalletto','cavalletto per z900', 'https://images.wemoto.com/full/CENTRESTAND/10060403.jpg',25.00, 'Hirace', 'y', '54758', 'B', 148),
        ('1845', 'Parafango','parafango per vespa', 'https://www.ricambimotopalermo.it/wp-content/uploads/imported/PARAFANGO-VESPA-PX-FRENO-A-DISCO-RMS-142680130-161241378094.jpg',75.00, 'Hirace', 'y', '54758', 'A', 126),
        ('1888', 'Gomme diavel','4stag-all seasons', 'https://i.ebayimg.com/images/g/oVMAAOSwhpZaIfJc/s-l300.jpg',60.00, 'Michelin', 'n', '54758', 'A', 127);
    
insert into Pneumatici(misura, stagione, codiceProd)
values ('155\75R15', 'estiva', '2452'), ('165\55R15', 'invernale', '1574'), ('160\55R16', '4stagioni', '1888');

insert into Meccanica (impiego, codiceProd)
values ('candela', '1577'), ('cambio', '1478'), ('cavalletto', '1745');

insert into Carrozzeria (materiale, codiceProd)
values ('carbonio', '1457'), ('alluminio', '1744'), ('alluminio', '1845');

insert into Ordine (codice, dataOrd, npezzi, CF)
values (1234,  '2020-01-10', 2, 'PTTNTN56E24G230B'),
		(1235, '2020-01-02', 3, 'PTTPLA00A24F912W'),
        (1236, '2020-01-03', 2, 'PTTPLA00A24F912W'),
        (1237, '2020-01-20', 2, 'LGLNTN56E24G230B'),
		(1238, '2020-01-15', 1, 'GPPNTN56E24G230B'),
        (1239, '2020-01-16', 2, 'GPPNTN56E24G230B');
       

insert into Composto (codice, codiceProd)
values (1234, '2452'), (1234, '1574'), (1235, '1577'), (1235, '1574'), (1235, '1478'), (1236, '1574'), (1236, '2452'), (1237, '1745'),(1237, '1577'),(1238, '2452'), (1239, '1577'), (1239, '1888');

insert into Spedizione ( codice, dataSpedizione)
values (1234, '2020-01-12'), (1235, '2020-01-05'), (1238, '2020-01-22'), (1239, '2020-01-23');

insert into Telefono (numero, CF)
values ('3243757854', 'PTTPLA00A24F912W'),
		('3243875956', 'PTTNTN56E24G230A'),
        ('3459865748', 'PTTNTN56E24G230B'),
		('3549876895', 'GPPNTN56E24G230B'),
        ('3405697858', 'LGLNTN56E24G230B');
        