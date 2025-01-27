CREATE SCHEMA EdenJewelryDB;
USE EdenJewelryDB;

CREATE TABLE UTENTE (
    email varchar(50) NOT NULL,
    nome varchar(15) NOT NULL,
    cognome varchar(15) NOT NULL,
    password varchar(200) NOT NULL,
    tipo varchar(6) NOT NULL,

    PRIMARY KEY (email)
);

CREATE TABLE PRODOTTO (
    nome varchar(50) NOT NULL,
    prezzo float NOT NULL,
    quantità int NOT NULL,
    categoria varchar(20),
    immagine varchar(255),

    PRIMARY KEY (nome)
);

CREATE TABLE ORDINE (
    numeroOrdine int AUTO_INCREMENT,
    email varchar(20) NOT NULL,
    totale float NOT NULL,
    metodoPagamento varchar(20) NOT NULL,
    indirizzo varchar(50),
    prodotti varchar(255),

    PRIMARY KEY (numeroOrdine),
    FOREIGN KEY (email) REFERENCES UTENTE (email)
);

CREATE TABLE WISHLIST (
    email varchar(20) NOT NULL,
    prodotti varchar(255),

    PRIMARY KEY (email),
    FOREIGN KEY (email) REFERENCES UTENTE (email)
);

INSERT INTO UTENTE (email, nome, cognome, password, tipo) VALUES
('mario.rossi@gmail.com', 'Mario', 'Rossi', '12345', 'user'),
('luigi.montuori@gmail.com', 'Luigi', 'Montuori', '12345', 'seller');