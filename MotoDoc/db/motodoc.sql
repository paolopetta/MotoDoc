drop database if exists docmoto;
create database docmoto;
use docmoto;

DROP user IF EXISTS 'motoDoc'@'localhost';
CREATE USER 'motoDoc'@'localhost' IDENTIFIED BY 'adminadmin';
GRANT ALL ON docmoto.* TO 'motoDoc'@'localhost';

/*create table Fornitore(
  p_iva    varchar(10)    not null primary key,
    nome    varchar(30)   not null,
    indirizzo  varchar(50)    not null,
    fax      varchar(10)      not null
);

create table Deposito(
  codiceAlfanumerico varchar(4)  not null primary key,
    indirizzo  varchar(50)    not null
    );

create table Scaffale(
  codice        int      not null primary key,
    codiceAlfanumerico varchar(4)  not null,
    foreign key (codiceAlfanumerico) references Deposito(codiceAlfanumerico)
    );
*/

create table Prodotto(
  codiceProd    varchar(10)  not null primary key,
    nome      varchar(20)  not null,
    descrizione  varchar(100)  not null,
    img      varchar(300)  ,
    prezzo     double      not null,
    marca    varchar(10)    not null,
    disponibilità enum('y' , 'n') not null,
    offerta    varchar(1)
    /*foreign key (p_iva) references Fornitore (p_iva),
    foreign key (codiceAlfanumerico) references Deposito(codiceAlfanumerico),
    foreign key (codice) references Scaffale(codice)*/
);

create table Meccanica (
  impiego    varchar(10)    not null,
    codiceProd   varchar(10)          not null,
    foreign key (codiceProd) references Prodotto (codiceProd)
);

create table Pneumatici(
  misura    varchar(9)      not null,
    stagione  varchar(10)      not null,
    codiceProd   varchar(10)      not null,
    foreign key (codiceProd) references Prodotto (codiceProd)
    );

create table Carrozzeria(
  materiale   varchar(10)      not null,
    codiceProd   varchar(10)        not null,
    foreign key (codiceProd) references Prodotto(codiceProd)
    );

    create table Users(
    id integer unsigned auto_increment not null,
    username varchar(192) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null,
    auth enum('user', 'premium', 'admin') default 'user',
    active boolean default true,
	CF      varchar(16)   ,
    nome    varchar(15)    ,
    cognome    varchar(15)    ,
    indirizzo  varchar(50)    ,
    primary key(id)
) auto_increment = 1;

create table Ordine(
  codice    int        not null primary key,
    dataOrd    date      not null,
    npezzi    int       not null,
    Users_id      integer unsigned not null,
    foreign key(Users_id) references Users(id)
);

create table Composto(
  codice         int        not null,
    codiceProd     varchar(10)        not null,
    foreign key(codice) references Ordine(codice),
  foreign key(codiceProd) references Prodotto(codiceProd)
);

create table Spedizione(
  codice      int      not null primary key,
    dataSpedizione  date    not null,
  foreign key (codice) references Ordine (codice)
);

create table Telefono(
  numero     varchar(10)    not null primary key,
  Users_id      integer unsigned not null,
    foreign key(Users_id) references Users(id)
);

insert into Users (username, email, password, auth, CF, nome, cognome, indirizzo)
values ('Paolo00', 'paolo.petta00@gmail.com', sha1("password"), 'user','PTTPLA00A24F912W', 'Paolo', 'Petta', 'Via s rocco 8');

insert into Users (username, email, password, auth, nome)
values ('admin','admin@admin.it', sha1("password"), 'admin', 'Admin');

set global max_allowed_packet=20971520;

/*insert into Fornitore (p_iva, nome, indirizzo, fax)
values ('54555', 'Motopertutti', 'Via aztori', '0275468578'),
    ('54758', 'Ricambidueruote', 'Via Clmente', '0452428745');

insert into Deposito (codiceAlfanumerico, indirizzo)
values ( 'A', 'Via aztori'),
    ('B', 'Via lamia');

insert into Scaffale (codice, codiceAlfanumerico)
values (123, 'A'), (124, 'A'), (125, 'A'),(126, 'A'),(127, 'A'), (145, 'B'), (146, 'B'), (147, 'B'), (148, 'B'), (149, 'B');
*/
insert into Prodotto (codiceProd, nome, descrizione, img, prezzo, marca, disponibilità, offerta)
values ('P2452', 'Gomme','pilot', 'https://i.imgur.com/6ojNILX.jpg', 55.00, 'Michelin', 'y','y'),
    ('P1574', 'Gomme Pirelli','free', 'https://d3nv2arudvw7ln.cloudfront.net/images/870/855/diablo_rosso_III_3_4,0.jpg', 50.00, 'Pirelli', 'y','n'),
       ('P1575', 'Gomme Dunlop','free','https://i.imgur.com/KrBxc9N.jpg', 50.00, 'Dunlop', 'y','n'),
       ('P1576', 'Gomme Power','free','https://i.imgur.com/3sQ46HN.jpg', 50.00, 'Power', 'y','n'),
        ('M1577', 'Candela', 'candela motore', 'https://images-na.ssl-images-amazon.com/images/I/61Lj6AA7BeL._AC_SX466_.jpg',15.00, 'NGK', 'y','n'),
       ('M1578', 'Filtro Aria', 'filtro aria', 'https://i.imgur.com/Rkft5ah.jpg',25.00, 'BMC', 'y','y'),
        ('M1478', 'Cambio','cambio elettronico', 'https://www.carpimoto.it/Images/Products/Detail/Dynojet_Quick_Shifter_Sensor_Cambio_Elettronico_E4-103.jpg',200.50, 'Hirace', 'n','n'),
        ('C1457', 'Fianchetto duke','fianchetto', 'https://www.motoricambiservice.com/ebay/readyproebayimages/CARENA-CODINO-SINISTRA-KTM-390-DUKE-ABS-2017-2019-9300804110028-LEFT-SIDE-REAR_462479.jpg',50.00, 'Hirace', 'y','n'),
       ('C1458', 'Specchietti','Duke', 'https://i.imgur.com/Xla8xXu.jpg',50.00, 'Hirace', 'y','y'),
       ('M1745', 'Serbatoio','serbatoio ktm', 'https://static.bakeca.it/immagini/edf/edf788f7f5a206a5402962374cc575ca.jpg',150.00, 'Hirace', 'n','n'),
    ('C1744', 'Cavalletto','cavalletto per z900', 'https://i.imgur.com/Uk05TQ2.jpg',25.00, 'Hirace', 'y','n'),
       ('C1846', 'Codino Duke','codino per duke', 'https://i.imgur.com/Rvl46ek.jpg',65.00, 'Hirace', 'y','n'),
       ('C1847', 'Codino Fazer','codino per fazer', 'https://i.imgur.com/MBU1rkE.jpg',95.00, 'Hirace', 'y','n'),
       ('C1848', 'Cupolino','Cupolino d epoca', 'https://i.imgur.com/POH57Lk.jpg',95.00, 'Hirace', 'y','y'),
        ('C1845', 'Parafango','parafango per vespa', 'https://www.ricambimotopalermo.it/wp-content/uploads/imported/PARAFANGO-VESPA-PX-FRENO-A-DISCO-RMS-142680130-161241378094.jpg',75.00, 'Hirace', 'y','n');


insert into Pneumatici(misura, stagione, codiceProd)
values ('155\75R15', 'estiva', 'P2452'), ('165\55R15', 'invernale', 'P1574'), ('165\55R15', 'invernale', 'P1575'), ('165\55R15', 'invernale', 'P1576');

insert into Meccanica (impiego, codiceProd)
values ('candela', 'M1577'), ('cambio', 'M1478'), ('cavalletto', 'M1745'), ('Filtro', 'M1577');

insert into Carrozzeria (materiale, codiceProd)
values ('carbonio', 'C1457'), ('alluminio', 'C1744'), ('alluminio', 'C1845'), ('alluminio', 'C1458'), ('carbonio', 'C1846'), ('carbonio', 'C1847'), ('plexiglass', 'C1848');