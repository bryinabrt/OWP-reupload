DROP SCHEMA IF EXISTS travelagency;
CREATE DATABASE IF NOT EXISTS TravelAgency;
USE TravelAgency;

CREATE TABLE Destinacije (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grad VARCHAR(255) NOT NULL,
    drzava VARCHAR(255) NOT NULL,
    kontinent VARCHAR(255) NOT NULL
);

ALTER TABLE Putovanja
ADD slika longblob;

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
    idDestinacijeSmestaja BIGINT,
    recenzija DECIMAL(10, 2),
    uslugaWifi bool,
    uslugaKupatilo bool,
    uslugaTv bool,
    opis VARCHAR(255),
    FOREIGN KEY (idTipJedinice) REFERENCES TipJedinice(id),
    FOREIGN KEY (idDestinacijeSmestaja) REFERENCES Destinacije(id)
);

CREATE TABLE PrevoznoSredstvo (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipSredstva varchar(255),
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
    idSmestajnaJedinica BIGINT,
    kategorija ENUM('zimovanje', 'letovanje', 'lastMinute', 'novogodisnja'),
    brojNocenja INT NOT NULL,
    slika VARCHAR(1024),
    FOREIGN KEY (idDestinacije) REFERENCES Destinacije(id),
    FOREIGN KEY (idPrevoznoSredstvo) REFERENCES PrevoznoSredstvo(id),
    FOREIGN KEY (idSmestajnaJedinica) REFERENCES SmestajnaJedinica(id)
);

ALTER TABLE Putovanja
MODIFY COLUMN slika VARCHAR(1024);

ALTER TABLE Korisnici
MODIFY COLUMN korisnickoIme VARCHAR(255) NOT NULL unique;

CREATE TABLE Korisnici (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    korisnickoIme VARCHAR(255) NOT NULL unique,
    lozinka VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    ime VARCHAR(255) NOT NULL,
    prezime VARCHAR(255) NOT NULL,
    datumRodjenja DATETIME NOT NULL,
    adresa VARCHAR(255) NOT NULL,
    brojTelefona VARCHAR(20) NOT NULL,
    datumRegistracije TIMESTAMP NOT NULL,
    blokiran bool NOT NULL,
    uloga ENUM('putnik', 'organizator', 'administrator') DEFAULT 'putnik'
);



INSERT INTO Korisnici values (1, 'bryinabrte', '123', 'prolic.djunior@gmail.com', 'Marko', 'Markic', '2001-08-25', 'Karagača 11A', '0615856916', '2023-12-21 21:30:16', 'administrator');
INSERT INTO Korisnici values (2, 'perogas', '1234', 'perogas@gmail.com', 'Pero', 'Peki', '1960-02-22', 'Krajiska 13', '0601512451', '2023-12-21 21:39:48', 'organizator');
INSERT INTO Korisnici values (3, 'radasheen', '12345', 'radasheen@gmail.com', 'Radisa', 'Miljkovic', '1960-06-29', 'negde u aleksincu', '0635812291', '2023-12-21 21:45:09', 'putnik');

INSERT INTO Destinacije values (1, 'Novi Sad', 'Srbija', 'Evropa');
INSERT INTO Destinacije values (2, 'Barselona', 'Spanija', 'Evropa');
INSERT INTO Destinacije values (3, 'Tokio', 'Japan', 'Azija');

INSERT INTO Putovanja VALUES (1, 154, 3, 1, 2, 1, 6, 'images/smilelok.png');
INSERT INTO Putovanja values (2, 254, 2, 2, 2, 2, 6, 'images/1664979500673875.jpg');
INSERT INTO Putovanja values (3, 354, 1, 2, 3, 2, 6, 'images/1664979500673875.jpg');

delete from putovanja;

INSERT INTO TipJedinice values(1, 'Apartman');
INSERT INTO TipJedinice values(2, 'Hotel (Samo Nocenje)');
INSERT INTO TipJedinice values(3, 'Polupansion');

INSERT INTO USLUGE values(1, 'Wi-Fi');
INSERT INTO USLUGE values(2, 'Kupatilo');
INSERT INTO USLUGE values(3, 'TV');

INSERT INTO SmestajnaJedinica values(1, 'Hotel Sheraton', 2, 60, 1, 9.8, true, true, false, 'veoma kul');
INSERT INTO SmestajnaJedinica values(2, 'Hotel Prle', 3, 90, 2, 8.8, true, false, true, 'kul veoma');
INSERT INTO SmestajnaJedinica values(3, 'Hotel Hilton', 3, 90, 2, 8.8, true, false, true, 'kul veoma');
INSERT INTO SmestajnaJedinica values(5, 'Hotel Nzm vise', 3, 90, 3, 9.0, true, false, true, 'kul veoma');



INSERT INTO PrevoznoSredstvo values(1, 'Avion', 90, 3, 'leti brt');
INSERT INTO PrevoznoSredstvo values(2, 'Autobus', 40, 3, 'ide brzo');
INSERT INTO PrevoznoSredstvo values(3, 'Double decker', 90, 3, 'ide ne tako brzo');
INSERT INTO PrevoznoSredstvo values(4, 'Brod', 50, 4, 'plovi brt');

CREATE TABLE listaZelja (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	idKorisnika BIGINT,
    idPutovanja BIGINT,
    FOREIGN KEY(idKorisnika) references korisnici(id),
    FOREIGN KEY(idPutovanja) references putovanja(id)
);

CREATE TABLE rezervacije (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    korisnikId BIGINT,
    zeljenoPutovanjeId BIGINT,
    zeljenaDestinacijaId BIGINT,
    brojMesta INT,
    brojPutnika INT,
    cenaRezervacije DECIMAL(10, 2),
    datumRezervisanja DATETIME,
    FOREIGN KEY(korisnikId) REFERENCES korisnici(id)
		ON DELETE CASCADE,
    FOREIGN KEY(zeljenoPutovanjeId) REFERENCES putovanja(id)
		ON DELETE CASCADE,
	FOREIGN KEY(zeljenaDestinacijaId) REFERENCES putovanja(idDestinacije)
		ON DELETE CASCADE
);

CREATE TABLE shoppingCart(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	korisnikId BIGINT,
    rezervisanoPutovanjeId BIGINT,
    brojPutnika int,
    ukupnaCena DECIMAL(10, 2),
    pricesId BIGINT,
	FOREIGN KEY(korisnikId) REFERENCES korisnici(id)
		ON DELETE CASCADE,
    FOREIGN KEY(rezervisanoPutovanjeId) REFERENCES putovanja(id)
		ON DELETE CASCADE,
	FOREIGN KEY(pricesId) REFERENCES prices(id)
		ON DELETE CASCADE
);

CREATE TABLE loyaltyKartica (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	idKorisnika BIGINT,
    brojBodova int,
    CONSTRAINT proveriBodove check (brojBodova <= 10),
    FOREIGN KEY(idKorisnika) REFERENCES korisnici(id)
		ON DELETE CASCADE
);

CREATE TABLE zahtevLoyaltyKartice (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	idKorisnika BIGINT,
    FOREIGN KEY(idKorisnika) REFERENCES korisnici(id)
);


CREATE TABLE Prices (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    destinationId BIGINT,
    putovanjeId BIGINT,
    startDate DATETIME,
    endDate DATETIME,
    priceOfTravel DECIMAL(10, 2),
    FOREIGN KEY (destinationId) references destinacije(id)
    ON DELETE CASCADE,
    FOREIGN KEY (putovanjeId) references putovanja(id)
    ON DELETE CASCADE
);

drop table prices;

INSERT INTO Prices (destinationId, putovanjeId, startDate, endDate, priceOfTravel)
VALUES
	(1, 3, '2024-02-12 19:00:00', '2024-02-16 19:00:00', 500.00),
    (1, 3, '2024-02-15 19:00:00', '2024-02-23 19:00:00', 600.00),
    (1, 3, '2024-02-21 19:00:00', '2024-02-27 19:00:00', 550.00),
    (2, 2, '2024-03-12 19:00:00', '2024-03-18 19:00:00', 800.00),
    (2, 2, '2024-03-15 19:00:00', '2024-03-21 19:00:00', 900.00),
    (2, 2, '2024-03-21 19:00:00', '2024-03-22 19:00:00', 1050.00),
    (3, 1, '2024-04-12 19:00:00', '2024-04-13 19:00:00', 300.00),
    (3, 1, '2024-04-15 19:00:00', '2024-04-24 19:00:00', 200.00),
    (3, 1, '2024-04-21 19:00:00', '2024-04-29 19:00:00', 250.00);


SELECT
    p.id AS putovanje_id,
    p.sifraPutovanja,
    p.idDestinacije AS destination_id,
    ps.tipSredstva AS prevoznoSredstvo,
    sj.nazivJedinice AS smestajnaJedinica,
    p.kategorija,
    p.datumPolaska,
    p.datumPovratka,
    p.brojNocenja,
    COALESCE(pr.price, p.cena) AS cena
FROM
    putovanja p
    LEFT JOIN prevoznoSredstvo ps ON ps.id = p.idPrevoznoSredstvo
    LEFT JOIN smestajnaJedinica sj ON sj.id = p.idSmestajnaJedinica
    LEFT JOIN Prices pr ON pr.destination_id = p.idDestinacije AND p.datumPolaska BETWEEN pr.start_date AND pr.end_date
ORDER BY
    p.id;
    
    
SELECT p.id, p.destinationId, p.startDate, p.endDate, p.priceOfTravel
FROM prices p 
WHERE p.destinationId = 3
ORDER BY p.id
