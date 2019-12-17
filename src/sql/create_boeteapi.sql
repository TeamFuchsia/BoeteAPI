CREATE DATABASE Boeteapi;

/* switch naar database Boeteapi in intelliJ */

CREATE TABLE PERSOON (
                         persoonNr SERIAL,
                         voornaam VARCHAR (30) NOT NULL,
                         achternaam  VARCHAR (30) NOT NULL,
                         straat VARCHAR (25) NOT NULL,
                         huisnummer VARCHAR (10) NOT NULL,
                         postcode VARCHAR(7) NOT NULL,
                         woonplaats VARCHAR (30) NOT NULL,
                         bsn VARCHAR (9) NOT NULL UNIQUE,
                         geboortedatum DATE NOT NULL,
                         PRIMARY KEY (persoonNr)
);
CREATE TABLE FEIT (
                      feitNr SERIAL,
                      feitcode VARCHAR (7) NOT NULL UNIQUE,
                      omschrijving  VARCHAR (5000) NOT NULL,
                      bedrag double precision NOT NULL,
                      PRIMARY KEY (feitNr)
);
CREATE TABLE ZAAK (
                      zaakNr SERIAL,
                      persoonNr Integer NOT NULL,
                      overtredingsdatum DATE NOT NULL,
                      pleegLocatie VARCHAR (100),
                      PRIMARY KEY (zaakNr),
                      FOREIGN KEY (persoonNr) REFERENCES persoon(persoonnr)
);
CREATE TABLE ZAAKREGEL (
                           zaakNr INTEGER,
                           feitNr INTEGER,
                           PRIMARY KEY (zaakNr,feitNr),
                           FOREIGN KEY (zaakNr) REFERENCES zaak(zaakNr),
                           FOREIGN KEY (feitNr) REFERENCES feit(feitNr)
);