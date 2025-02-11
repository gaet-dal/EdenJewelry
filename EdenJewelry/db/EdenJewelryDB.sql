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
    ('alevit@gmail.com', 'Alessandro', 'Vitagliano', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', 'seller');

INSERT INTO WISHLIST (idWishlist, email) VALUES
    (1, 'alevit@gmail.com');

INSERT INTO PRODOTTO (nome, prezzo, quantità, categoria, immagine) VALUES
    ('Collana argento', 20.0, 5, 'collane', 'placeholder');

INSERT INTO ITEMWISHLIST(idItem, idWishlist, nomeProdotto) VALUES
    (1, 1, 'Collana argento');