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

    PRIMARY KEY (numeroOrdine),
    FOREIGN KEY (email) REFERENCES UTENTE (email)
);

CREATE TABLE WISHLIST (
    idWishlist int AUTO_INCREMENT,
    email varchar(20) NOT NULL,

    PRIMARY KEY (idWishlist),
    FOREIGN KEY (email) REFERENCES UTENTE (email)
);

CREATE TABLE RIGAORDINE  (
    idRiga int AUTO_INCREMENT,
    nomeProdotto varchar(50),
    numeroOrdine int,
    quantità int,
    prezzo float,

    PRIMARY KEY (idRiga),
    FOREIGN KEY (nomeProdotto) REFERENCES PRODOTTO (nome),
    FOREIGN KEY (numeroOrdine) REFERENCES ORDINE (numeroOrdine)
);

CREATE TABLE ITEMWISHLIST (
    idItem int AUTO_INCREMENT,
    idWishlist int,
    nomeProdotto varchar(50),

    PRIMARY KEY (idItem),
    FOREIGN KEY (idWishlist) REFERENCES WISHLIST (idWishlist),
    FOREIGN KEY (nomeProdotto) REFERENCES PRODOTTO (nome)
);

INSERT INTO UTENTE (email, nome, cognome, password, tipo) VALUES
('mario.rossi@gmail.com', 'Mario', 'Rossi', '12345', 'user'),
('luigi.montuori@gmail.com', 'Luigi', 'Montuori', '12345', 'seller');