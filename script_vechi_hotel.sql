
DROP DATABASE IF EXISTS hotelDB;

CREATE DATABASE hotelDB;

USE hotelDB;

CREATE TABLE tblOaspeti (
	  idOaspete int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	  nume VARCHAR(30) NOT NULL,
	  prenume VARCHAR(30) NOT NULL,
	  strada VARCHAR(70) DEFAULT NULL,
	  oras VARCHAR(50) DEFAULT NULL,
	  tara CHAR(2) DEFAULT NULL,
	  telefon VARCHAR(14) DEFAULT NULL
) ENGINE=InnoDB;

ALTER TABLE tblOaspeti AUTO_INCREMENT=1;


INSERT INTO tblOaspeti (nume, prenume, strada, oras, tara, telefon) VALUES
('Popescu', 'Ion', 'Str. Victoriei 12', 'București', 'RO', '0712345678'),
('Ionescu', 'Maria', 'Str. Lalelelor 7', 'Cluj-Napoca', 'RO', '0723456789'),
('Vasilescu', 'Andrei', 'Str. Mihai Eminescu 34', 'Iași', 'RO', '0734567890'),
('Georgescu', 'Elena', 'Str. Moșilor 21', 'Timișoara', 'RO', '0745678901'),
('Dumitrescu', 'Gheorghe', 'Str. Unirii 8', 'Brașov', 'RO', '0756789012'),
('Marin', 'Alina', 'Str. Muncii 14', 'Sibiu', 'RO', '0767890123'),
('Stanescu', 'Ioana', 'Str. Libertății 20', 'Ploiești', 'RO', '0778901234'),
('Radu', 'Adrian', 'Str. Coltei 3', 'Constanța', 'RO', '0789012345'),
('Ciobanu', 'Lucian', 'Str. Gării 15', 'Brăila', 'RO', '0790123456'),
('Neagu', 'Anca', 'Str. Nouă 10', 'Bacău', 'RO', '0710123456');


CREATE TABLE tblRezervari(
	  idRezervare int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	  persoane int(11) NOT NULL,
	  dataCheckIn date DEFAULT NULL,
	  dataCheckOut date DEFAULT NULL,
	  total DOUBLE NOT NULL
) ENGINE=InnoDB;

ALTER TABLE tblRezervari AUTO_INCREMENT=1;


INSERT INTO tblRezervari (persoane, dataCheckIn, dataCheckOut, total) VALUES
(2,'2025-05-01', '2025-05-05', 850.00),
(1,'2025-06-10', '2025-06-12', 400.00),
(3,'2025-07-15', '2025-07-20', 1250.00),
(2,'2025-04-20', '2025-04-22', 500.00),
(4,'2025-08-01', '2025-08-07', 2200.00),
(1, '2025-05-18', '2025-05-21', 600.00),
(2, '2025-09-05', '2025-09-10', 980.00),
(3, '2025-10-12', '2025-10-15', 750.00),
(2, '2025-11-01', '2025-11-03', 420.00),
(1, '2025-12-20', '2025-12-25', 890.00);


CREATE TABLE tblRezervariOaspeti (
	  idOaspete int(11) NOT NULL,
	  idRezervare int(11) NOT NULL,
	  PRIMARY KEY(idOaspete,idRezervare),
      CONSTRAINT fk_idOaspete FOREIGN KEY (idOaspete) REFERENCES tblOaspeti(idOaspete) 
      ON DELETE CASCADE ON UPDATE CASCADE,
      CONSTRAINT fk_idRezervareOaspete FOREIGN KEY (idRezervare) REFERENCES tblRezervari(idRezervare) 
      ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;


INSERT INTO tblRezervariOaspeti(idOaspete, idRezervare) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(5,6),
(7,7),
(7,8),
(9,9),
(9,10)
;


CREATE TABLE tblCamere (
	  nrCamera int(11) NOT NULL PRIMARY KEY,
	  tipCamera varchar(10) NOT NULL,
	  nrPersoaneStandard int(11) NOT NULL,
	  pretPeNoapte double NOT NULL
) ENGINE=InnoDB;



INSERT INTO  tblCamere (nrCamera, tipCamera, nrPersoaneStandard, pretPeNoapte) VALUES
(201, 'Double',2, 199.99),
(202, 'Double',2, 174.99),
(203, 'Double',2, 199.99),
(204, 'Double',2, 174.99),
(205, 'Single',2, 174.99),
(206, 'Single',2, 149.99),
(207, 'Single',2, 174.99),
(208, 'Single',2, 149.99),
(301, 'Double',2, 199.99),
(302, 'Double',2, 174.99),
(303, 'Double', 2, 199.99),
(304, 'Double', 2, 174.99),
(305, 'Single',2, 174.99),
(306, 'Single',2, 149.99),
(307, 'Single',2, 174.99),
(308, 'Single',2, 149.99),
(401, 'Suite',3, 399.99),
(402, 'Suite', 3, 399.99),
(501, 'Family', 4, 399.99),
(502, 'Family', 4, 399.99);



CREATE TABLE tblRezervariCamere (
	  nrCamera int(11) NOT NULL,
	  idRezervare int(11) NOT NULL,
      PRIMARY KEY(nrCamera,idRezervare),
      CONSTRAINT fk_idRezervareCamera FOREIGN KEY (idRezervare) REFERENCES tblRezervari(idRezervare) 
      ON DELETE CASCADE ON UPDATE CASCADE,
      CONSTRAINT fk_nrCamera FOREIGN KEY (nrCamera) REFERENCES tblCamere(nrCamera) 
      ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

INSERT INTO tblRezervariCamere (nrCamera, idRezervare) VALUES
(201,1),
(204,2),
(205,3),
(301,4),
(303,5),
(306,6),
(308,7),
(401,8),
(501,9),
(502,10);