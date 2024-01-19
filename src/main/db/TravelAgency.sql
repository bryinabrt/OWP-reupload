DROP SCHEMA IF EXISTS travelagency;
CREATE DATABASE IF NOT EXISTS TravelAgency;
USE TravelAgency;

CREATE TABLE Destinacije (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grad VARCHAR(255) NOT NULL,
    drzava VARCHAR(255) NOT NULL,
    kontinent VARCHAR(255) NOT NULL
);

DROP TABLE PUTOVANJA;

CREATE TABLE TipJedinice (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	nazivTipaJedinice varchar(255)
);

CREATE TABLE Usluge (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nazivUsluge varchar(255)
);


CREATE TABLE SmestajnaJedinica (
	id BIGINT PRIMARY KEY auto_increment,
	nazivJedinice varchar(255),
    idTipJedinice BIGINT,
    kapacitet INT NOT NULL,
    idDestinacije BIGINT,
    recenzija DECIMAL(10, 2),
    uslugaWifi bool,
    uslugaKupatilo bool,
    uslugaTv bool,
    opis VARCHAR(255),
    FOREIGN KEY (idTipJedinice) REFERENCES TipJedinice(id),
    FOREIGN KEY (idDestinacije) REFERENCES Destinacije(id)
);

CREATE TABLE PrevoznoSredstvo (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tip varchar(255),
    brojSedista INT NOT NULL,
    krajnjaDestinacija BIGINT,
	opis VARCHAR(255),
    FOREIGN KEY (krajnjaDestinacija) REFERENCES Destinacije(id)
);


CREATE TABLE Putovanja (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sifraPutovanja VARCHAR(255),
    idDestinacije BIGINT,
    idPrevoznoSredstvo BIGINT,
    smestajnaJedinica VARCHAR(255) NOT NULL,
    kategorija ENUM('zimovanje', 'letovanje', 'lastMinute', 'novogodisnja'),
    datumPolaska DATETIME NOT NULL,
    datumPovratka DATETIME NOT NULL,
    brojNocenja INT NOT NULL,
    cena DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (idDestinacije) REFERENCES Destinacije(id),
    FOREIGN KEY (idPrevoznoSredstvo) REFERENCES PrevoznoSredstvo(id)
);


CREATE TABLE Korisnici (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    korisnickoIme VARCHAR(255) NOT NULL,
    lozinka VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    ime VARCHAR(255) NOT NULL,
    prezime VARCHAR(255) NOT NULL,
    datumRodjenja DATETIME NOT NULL,
    adresa VARCHAR(255) NOT NULL,
    brojTelefona VARCHAR(20) NOT NULL,
    datumRegistracije TIMESTAMP NOT NULL,
    uloga ENUM('putnik', 'organizator', 'administrator') DEFAULT 'putnik'
);

INSERT INTO Korisnici values (1, 'bryinabrte', '123', 'prolic.djunior@gmail.com', 'Marko', 'Markic', '2001-08-25', 'Karagača 11A', '0615856916', '2023-12-21 21:30:16', 'administrator');
INSERT INTO Korisnici values (2, 'perogas', '1234', 'perogas@gmail.com', 'Pero', 'Peki', '1960-02-22', 'Krajiska 13', '0601512451', '2023-12-21 21:39:48', 'organizator');
INSERT INTO Korisnici values (3, 'radasheen', '12345', 'radasheen@gmail.com', 'Radisa', 'Miljkovic', '1960-06-29', 'negde u aleksincu', '0635812291', '2023-12-21 21:45:09', 'putnik');

INSERT INTO Destinacije values (1, 'Novi Sad', 'Srbija', 'Evropa');
INSERT INTO Destinacije values (2, 'Barselona', 'Spanija', 'Evropa');
INSERT INTO Destinacije values (3, 'Tokio', 'Japan', 'Azija');

INSERT INTO Putovanja values (1, 154, 3, 'Avion', 'Hotel Zvezda', 'zimovanje', '2024-06-29 19:00:00', '2024-07-05 07:00:00', 6, 40000);
INSERT INTO Putovanja values (2, 254, 2, 'Autobus', 'Hotel nzm', 'letovanje', '2024-06-29 19:00:00', '2024-07-05 07:00:00', 6, 9000);
INSERT INTO Putovanja values (3, 354, 1, 'Autobus', 'Hotel Sheraton', 'zimovanje', '2024-06-29 19:00:00', '2024-07-05 07:00:00', 6, 6000);

INSERT INTO TipJedinice values(1, 'Apartman');
INSERT INTO TipJedinice values(2, 'Hotel (Samo Nocenje)');
INSERT INTO TipJedinice values(3, 'Polupansion');

INSERT INTO USLUGE values(1, 'Wi-Fi');
INSERT INTO USLUGE values(2, 'Kupatilo');
INSERT INTO USLUGE values(3, 'TV');

INSERT INTO SmestajnaJedinica values(1, 'Hotel Sheraton', 2, 60, 1, 9.8, true, true, false, 'veoma kul');
INSERT INTO SmestajnaJedinica values(2, 'Hotel Prle', 3, 90, 2, 8.8, true, false, true, 'kul veoma');


select s.id, s.nazivJedinice, s.idTipJedinice, 
s.kapacitet, s.idDestinacije, s.recenzija, s.idUsluge, s.opis, 
t.id, t.nazivTipaJedinice, d.id, d.grad, d.drzava, d.kontinent, 
u.id, u.nazivUsluge from smestajnajedinica s 
left join destinacije d on d.id = s.idDestinacije
LEFT JOIN tipjedinice t ON t.id = s.idTipJedinice
LEFT JOIN usluge u ON u.id = s.idUsluge
where s.id = 2;

INSERT INTO tipJedinica (nazivTipaJedinice) VALUE ('kurva')