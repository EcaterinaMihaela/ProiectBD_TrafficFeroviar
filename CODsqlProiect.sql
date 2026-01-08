--CREARE BAZA DE DATE
create DataBase traficFeroviarProiect

--CREARE TABELA Tren
CREATE TABLE Tren (
    idTren INT NOT NULL PRIMARY KEY,
    tip VARCHAR(30),
    numar INT,
    clase VARCHAR(30),
    nrVagoane INT,
    NumeOperator VARCHAR(50),
    NumeRuta VARCHAR(50)
	 --FOREIGN KEY (NumeOperator) REFERENCES Operator(NumeOperator)
    --FOREIGN KEY (NumeRuta) REFERENCES Ruta(NumeRuta)
);

--CREARE TABELA OPERATOR
CREATE TABLE Operator (
    NumeOperator VARCHAR(50) NOT NULL PRIMARY KEY,
    Contact VARCHAR(100)
);

--Constarngere Tren-Operator
ALTER TABLE Tren
ADD CONSTRAINT fk_tren_operator
FOREIGN KEY (NumeOperator) REFERENCES Operator(NumeOperator);

--Creare Tabela Ruta
CREATE TABLE Ruta (
    NumeRuta VARCHAR(50) NOT NULL PRIMARY KEY,
    nrStatii INT,
    durataCalatoriei INTERVAL,
    intervalOrar VARCHAR(50),
    intarziereAproximativa VARCHAR(50)
);

--Creare constrangere Tren-Ruta
ALTER TABLE Tren
ADD CONSTRAINT fk_tren_ruta
FOREIGN KEY (NumeRuta) REFERENCES Ruta(NumeRuta);

--Creare Tabela Gara
CREATE TABLE Gara (
    NumeGara VARCHAR(50) PRIMARY KEY,
    nrLinii INT
);
ALTER TABLE Gara ALTER COLUMN NumeGara SET NOT NULL;

--Creare Tabela Angajat
CREATE TABLE Angajat (
    idAngajat INT NOT NULL PRIMARY KEY,
    NumeAngajat VARCHAR(50),
    PrenumeAngajat VARCHAR(50),
    Rol VARCHAR(50),
    NumeOperator VARCHAR(50),
    CNP CHAR(13) NOT NULL UNIQUE,
    FOREIGN KEY (NumeOperator) REFERENCES Operator(NumeOperator)
);

--Creare Tabela Vagon
CREATE TABLE Vagon (
    NumarVagon INT PRIMARY KEY,
    clasa VARCHAR(30),
    nrLocuriLibere INT,
    nrLocuriTotal INT,
    tip VARCHAR(30),
    idTren INT,
    FOREIGN KEY (idTren) REFERENCES Tren(idTren)
);

--Creare Tabela Calator
CREATE TABLE Calator (
    nrLegitimatie INT NOT NULL PRIMARY KEY,
    nume VARCHAR(50),
    prenume VARCHAR(50),
    tip VARCHAR(30),
    contact VARCHAR(100)
);

--creare tabela Bilet
CREATE TABLE Bilet (
    idBilet INT NOT NULL PRIMARY KEY,
    detalii TEXT,
    pret NUMERIC(8,2),
    tipReducere VARCHAR(30),
    idTren INT,
    status VARCHAR(20),
    dataCumpararii DATE,
    locVagon INT,
    nrLegitimatie INT,
    NumeGara VARCHAR(50),
    NrVagon INT,
    
    FOREIGN KEY (idTren) REFERENCES Tren(idTren),
    FOREIGN KEY (nrLegitimatie) REFERENCES Calator(nrLegitimatie),
    FOREIGN KEY (NumeGara) REFERENCES Gara(NumeGara),
    FOREIGN KEY (NrVagon) REFERENCES Vagon(NumarVagon)
);

--Creare tabela Opreste 
CREATE TABLE Opreste (
    NrOprire INT PRIMARY KEY,
    idTren INT NOT NULL,
    NumeGara VARCHAR(50) NOT NULL,
    minuteStationare INT,
    oraSosire TIME,
    oraPlecare TIME,

    FOREIGN KEY (idTren) REFERENCES Tren(idTren),
    FOREIGN KEY (NumeGara) REFERENCES Gara(NumeGara)
);

--creare tabela Lucreaza
CREATE TABLE Lucreaza (
    idContract INT PRIMARY KEY,
    idAngajat INT NOT NULL,
    NumeGara VARCHAR(50) NOT NULL,
    salariu NUMERIC(10,2),
    
    FOREIGN KEY (idAngajat) REFERENCES Angajat(idAngajat),
    FOREIGN KEY (NumeGara) REFERENCES Gara(NumeGara)
);


--crerea tabelei Ruta-Gara
CREATE TABLE Ruta_Gara (
    idRutaGara INT PRIMARY KEY,
    NumeRuta VARCHAR(50) NOT NULL,
    NumeGara VARCHAR(50) NOT NULL,
    TimpTeoretic TIME,
    Parcurgere INT,

    FOREIGN KEY (NumeRuta) REFERENCES Ruta(NumeRuta),
    FOREIGN KEY (NumeGara) REFERENCES Gara(NumeGara)
);

--Setare status Bilet implicit valid
ALTER TABLE Bilet
ALTER COLUMN status SET DEFAULT 'valid';

----------------------Operatii de inserare,modificare,stergere inregistrari------------
--Adaugare Operatori
INSERT INTO Operator (NumeOperator, Contact) VALUES
('CFR Calatori', 'contact@cfrcalatori.ro'),
('RegioTrans', 'info@regiotrans.ro'),
('Transferoviar Calatori', 'info@tfc.ro'),
('Softrans', 'office@softrans.ro'),
('InterRegional Calatori', 'contact@interregional.ro'),
('TransRail', 'office@transrail.ro'),
('NorthExpress', 'support@northexpress.ro'),
('SouthLines', 'contact@southlines.ro'),
('FastTrain', 'fasttrain@email.com');

--Adaugare Rute
INSERT INTO Ruta (NumeRuta, nrStatii, durataCalatoriei, intervalOrar, intarziereAproximativa) VALUES
('Bucuresti-Cluj', 6, INTERVAL '8 hours', '08:00-16:00', '10 minute'),
('Iasi-Brasov', 5, INTERVAL '7 hours', '07:00-14:00', '15 minute'),
('Constanta-Bucuresti', 4, INTERVAL '3 hours', '10:00-13:00', '5 minute'),
('Timisoara-Oradea', 3, INTERVAL '4 hours', '12:00-16:00', '8 minute'),
('Sibiu-Brasov', 4, INTERVAL '2 hours', '09:00-11:00', '3 minute'),
('Drobeta Turnu-Severin-Craiova', 10, INTERVAL '3 hours', '13:50-16:50', '21 minute');

--Adaugare trenuri
INSERT INTO Tren (idTren, tip, numar, clase, nrVagoane, NumeOperator, NumeRuta) VALUES
(5050, 'InterRegio', 1745, 'Clasa I, Clasa II', 8, 'CFR Calatori', 'Bucuresti-Cluj'),
(1590, 'Regio', 8231, 'Clasa II', 5, 'RegioTrans', 'Iasi-Brasov'),
(1591, 'InterCity', 945, 'Clasa I, Clasa II', 6, 'TransRail', 'Timisoara-Oradea'),
(12345, 'Regio', 781, 'Clasa II', 4, 'NorthExpress', 'Constanta-Bucuresti'),
(73, 'InterRegio', 1102, 'Clasa I, Clasa II', 7, 'CFR Calatori', 'Sibiu-Brasov'),
(1006, 'Regional', 332, 'Clasa II', 3, 'SouthLines', 'Bucuresti-Cluj'),
(2001, 'High-Speed', 5001, 'Clasa I, Clasa II', 10, 'FastTrain', 'Drobeta Turnu-Severin-Craiova'),
(2002, 'Regional', 3107, 'Clasa II', 6, 'TransRail', 'Bucuresti-Cluj'),
(2003, 'InterCity', 4012, 'Clasa I, Clasa II', 8, 'CFR Calatori', 'Bucuresti-Cluj'),
(2004, 'High-Speed', 5503, 'Clasa I, Clasa II', 9, 'FastTrain', 'Bucuresti-Cluj'),
(2005, 'RegionalExpress', 6204, 'Clasa II', 5, 'TransRail', 'Drobeta Turnu-Severin-Craiova');

--Adaugare gari
INSERT INTO Gara (NumeGara, nrLinii) VALUES
('Gara de Nord', 14),
('Gara Cluj-Napoca', 10),
('Gara Iasi', 9),
('Gara Timisoara', 8),
('Gara Brasov', 7),
('Gara Sibiu', 6),
('Gara Constanta', 5),
('Gara Drobeta Turnu-Severin', 4);

--Adaugare vagoane
INSERT INTO Vagon (NumarVagon, clasa, nrLocuriLibere, nrLocuriTotal, tip, idTren) VALUES
(1, 'Clasa I', 10, 30, 'Pasageri', 5050),
(2, 'Clasa II', 25, 50, 'Pasageri', 5050),
(3, 'Clasa II', 40, 50, 'Pasageri', 1590),
(4, 'Clasa I', 5, 20, 'Pasageri', 1591),
(5, 'Clasa II', 35, 50, 'Pasageri', 12345),
(6, 'Clasa II', 20, 50, 'Pasageri', 73),
(7, 'Clasa I', 8, 25, 'Pasageri', 1006),
(8, 'Clasa I', 15, 35, 'Pasageri', 2001),
(9, 'Clasa II', 30, 50, 'Pasageri', 2002),
(10, 'Clasa I', 12, 30, 'Pasageri', 2003);

--Adaugare calatori
INSERT INTO Calator (nrLegitimatie, nume, prenume, tip, contact) VALUES
(501, 'Popescu', 'Andrei', 'Adult', 'andrei.popescu@email.com'),
(502, 'Ionescu', 'Maria', 'Student', 'maria.ionescu@email.com'),
(503, 'Georgescu', 'Alina', 'Copil', 'alina.georgescu@email.com'),
(504, 'Radu', 'Mihai', 'Adult', 'mihai.radu@email.com'),
(505, 'Stan', 'Elena', 'Pensionar', 'elena.stan@email.com'),
(506, 'Dumitrescu', 'Ion', 'Adult', 'ion.dumitrescu@email.com'),
(507, 'Marin', 'Laura', 'Adult', 'laura.marin@email.com'),
(508, 'Vasilescu', 'Robert', 'Student', 'robert.vasilescu@email.com');


--Adaugare bilete
INSERT INTO Bilet (idBilet, detalii, pret, tipReducere, idTren, status, dataCumpararii, locVagon, nrLegitimatie, NumeGara, NrVagon) VALUES
(1, 'Bilet dus Bucuresti-Cluj', 120.50, 'None', 5050, 'valid', '2025-11-08', 12, 501, 'Gara de Nord', 1),
(2, 'Bilet dus Iasi-Brasov', 80.00, 'Student', 1590, 'valid', '2025-11-08', 34, 502, 'Gara Iasi', 3),
(3, 'Bilet dus Timisoara-Oradea', 60.00, 'None', 1591, 'valid', '2025-11-08', 4, 503, 'Gara Timisoara', 4),
(4, 'Bilet dus Constanta-Bucuresti', 50.00, 'Pensionar', 12345, 'valid', '2025-11-08', 20, 504, 'Gara Constanta', 5),
(5, 'Bilet dus Sibiu-Brasov', 70.00, 'None', 73, 'valid', '2025-11-08', 5, 505, 'Gara Sibiu', 6),
(6, 'Bilet dus Bucuresti-Drobeta', 90.00, 'None', 2001, 'valid', '2025-11-08', 7, 506, 'Gara Drobeta Turnu-Severin', 8);

--Adaugare Angajati
INSERT INTO Angajat (idAngajat, NumeAngajat, PrenumeAngajat, Rol, NumeOperator, CNP) VALUES
(1, 'Dumitrescu', 'Ion', 'Mecanic', 'CFR Calatori', '1980509123456'),
(2, 'Stan', 'Elena', 'Casier', 'RegioTrans', '2991208123456'),
(3, 'Popa', 'Mihai', 'Inspector', 'TransRail', '1960705123456'),
(4, 'Ionescu', 'Ana', 'Controlor', 'NorthExpress', '2900303123456'),
(5, 'Georgescu', 'Radu', 'Casier', 'SouthLines', '1971112123456'),
(6, 'Marin', 'Alina', 'Mecanic', 'FastTrain', '1980606123456');

--Adaugare inserari in tabela opreste
INSERT INTO Opreste (NrOprire, idTren, NumeGara, minuteStationare, oraSosire, oraPlecare) VALUES
(1, 5050, 'Gara de Nord', 15, '08:00', '08:15'),
(2, 5050, 'Gara Cluj-Napoca', 0, '16:00', NULL),

(3, 1590, 'Gara Iasi', 10, '07:00', '07:10'),
(4, 1590, 'Gara Brasov', 10, '11:30', '11:40'),

(5, 1591, 'Gara Timisoara', 10, '06:30', '06:40'),
(6, 1591, 'Gara Sibiu', 0, '12:30', NULL),

(7, 12345, 'Gara Constanta', 15, '09:00', '09:15'),
(8, 73, 'Gara Sibiu', 10, '10:00', '10:10'),
(9, 2001, 'Gara Drobeta Turnu-Severin', 10, '14:00', '14:10');

--Adaugare inserari in tabela Lucreaza
INSERT INTO Lucreaza (idContract, idAngajat, NumeGara, salariu) VALUES
(1, 1, 'Gara de Nord', 4500),
(2, 2, 'Gara Iasi', 3800),
(3, 3, 'Gara Timisoara', 4200),
(4, 4, 'Gara Constanta', 3700),
(5, 5, 'Gara Sibiu', 3600),
(6, 6, 'Gara Drobeta Turnu-Severin', 4800);

--Adaugare inserari in tabelaa Ruta-Gara
INSERT INTO Ruta_Gara (idRutaGara, NumeRuta, NumeGara, TimpTeoretic, Parcurgere) VALUES
(1, 'Bucuresti-Cluj', 'Gara de Nord', '08:00', 0),
(2, 'Bucuresti-Cluj', 'Gara Cluj-Napoca', '16:00', 480),

(3, 'Iasi-Brasov', 'Gara Iasi', '07:00', 0),
(4, 'Iasi-Brasov', 'Gara Brasov', '14:00', 420),

(5, 'Constanta-Bucuresti', 'Gara Constanta', '10:00', 0),
(6, 'Sibiu-Brasov', 'Gara Sibiu', '09:00', 0),
(7, 'Drobeta Turnu-Severin-Craiova', 'Gara Drobeta Turnu-Severin', '13:50', 0);

--OPERATII de MODIFICARE
--Modificare contact operator
UPDATE Operator
SET Contact = 'contact@cfr.ro'
WHERE NumeOperator = 'CFR Calatori';

-- Modificare numărul de vagoane pentru un tren
UPDATE Tren
SET nrVagoane = 12
WHERE idTren = 5050;

-- Modificare ruta unui tren
UPDATE Tren
SET NumeRuta = 'Sibiu-Brasov'
WHERE idTren = 73;

-- Modificare prețul unui bilet
UPDATE Bilet
SET pret = 13.00
WHERE idBilet = 1;

-- Modificare statusul unui bilet
UPDATE Bilet
SET status = 'anulat'
WHERE idBilet = 2;


------------------------Modificari Pentru stergerea unui calator din 2 tabele si a biletului asociat--------------------------
-- Pentru tabela Bilet
ALTER TABLE Bilet DROP CONSTRAINT IF EXISTS bilet_nrlegitimatie_fkey;

ALTER TABLE Bilet
ADD CONSTRAINT bilet_nrlegitimatie_fkey
FOREIGN KEY (nrLegitimatie) REFERENCES Calator(nrLegitimatie)
ON DELETE CASCADE;

--Stergere Calator
DELETE FROM Calator
WHERE nrLegitimatie = 503;

--------------modificare constrangeri tren si alte tabele cu ON DEALEATE CASCADE---------------------------------
--sterg fk urile care au legatura cu trenul
ALTER TABLE Vagon DROP CONSTRAINT IF EXISTS vagon_idtren_fkey;
ALTER TABLE Bilet DROP CONSTRAINT IF EXISTS bilet_idtren_fkey;
ALTER TABLE Opreste DROP CONSTRAINT IF EXISTS opreste_idtren_fkey;
--le adaug din nou modificate
ALTER TABLE Vagon
ADD CONSTRAINT vagon_idtren_fkey
FOREIGN KEY (idTren) REFERENCES Tren(idTren)
ON DELETE CASCADE;

ALTER TABLE Bilet
ADD CONSTRAINT bilet_idtren_fkey
FOREIGN KEY (idTren) REFERENCES Tren(idTren)
ON DELETE CASCADE;

ALTER TABLE Opreste
ADD CONSTRAINT opreste_idtren_fkey
FOREIGN KEY (idTren) REFERENCES Tren(idTren)
ON DELETE CASCADE;

--Stergere tren cu id 73
DELETE FROM Tren WHERE idTren = 73;

--sterge biletele calatoriilor stersi din baza de date
DELETE FROM Bilet
WHERE nrLegitimatie NOT IN (
    SELECT nrLegitimatie
    FROM Calator
);

--actualizare pret bilet pt trenurile operate de CFRCalatori
UPDATE Bilet
SET Pret = Pret * 1.15
WHERE idTren IN (
    SELECT idTren
    FROM Tren
    WHERE NumeOperator = 'CFR Calatori'
);

--modifică numărul de vagoane pentru trenurile cu mai mult de 100 bilete vândute
UPDATE Tren
SET nrVagoane = nrVagoane + 1
WHERE idTren IN (
    SELECT idTren
    FROM Bilet
    GROUP BY idTren
    HAVING COUNT(*) > 100
);

--schimbă tipul trenurilor operate de un anumit operator
UPDATE Tren
SET tip = 'InterRegio'
WHERE NumeOperator IN (
    SELECT NumeOperator
    FROM Operator
    WHERE NumeOperator = 'RegioTrans'
);

--Interogare 1
--Cerință: Pentru fiecare tren, să se afișeze tipul trenului și numărul total de vagoane disponibile.
SELECT t.idTren, t.tip AS TipTren, SUM(v.nrLocuriTotal) AS TotalLocuri
FROM tren t
JOIN vagon v ON t.idTren = v.idTren
GROUP BY t.idTren, t.tip;

select 

--Interogare 2
--Cerinta: Sa se afiseze numele si prenumele tuturor calatorilor care au cumparat bilete.
SELECT DISTINCT c.nume, c.prenumes
FROM Calator c
JOIN Bilet b ON c.nrLegitimatie = b.nrLegitimatie;

--Interogare 3
--Cerinta: Sa se afiseze numele calatorilor si id-ul trenului pentru fiecare bilet cumparat.
SELECT c.nume, c.prenume, b.idTren
FROM Calator c
JOIN Bilet b ON c.nrLegitimatie = b.nrLegitimatie;

--Interogare 4
--Cerinta: Sa se afiseze trenurile și operatorii care le opereaza
SELECT t.idTren, t.tip, o.NumeOperator
FROM Tren t
INNER JOIN Operator o ON t.NumeOperator = o.NumeOperator;

--Interogare 5
--Cerinta: Sa se afiseze biletele calatorilor+gara de plecare
SELECT b.idBilet, c.nume, c.prenume, g.NumeGara
FROM Bilet b
INNER JOIN Calator c ON b.nrLegitimatie = c.nrLegitimatie
INNER JOIN Gara g ON b.NumeGara = g.NumeGara;

--Interogare 6
--Cerinta: Sa se afiseze trenurile si toate vagoanele lor(inclusiv trenurille fara vagoane)
--Mai intai inserez un tren care nu are vagoane si o ruta dorita
INSERT INTO Ruta (NumeRuta, nrStatii, durataCalatoriei, intervalOrar, intarziereAproximativa) VALUES
('Drobeta Turnu-Severin-Bucuresti Nord', 10, INTERVAL '8 hours', '17:25-23:10', '80 minute');
INSERT INTO Tren (idTren, tip, numar, clase, nrVagoane, NumeOperator, NumeRuta) VALUES
(12590, 'InterRegio', 12590, 'Clasa I, Clasa II', 0, 'CFR Calatori', 'Drobeta Turnu-Severin-Bucuresti Nord');

SELECT t.idTren, t.tip, v.NumarVagon
FROM Tren t
LEFT JOIN Vagon v ON t.idTren = v.idTren;

--Cerinta interogare 6':
--Afișați toate vagoanele și trenurile lor asociate, 
--chiar dacă există vagoane care nu corespund unui tren din tabelul Tren
SELECT t.idTren, t.tip, v.NumarVagon
FROM Tren t
RIGHT JOIN Vagon v ON t.idTren = v.idTren;


--Interogare 7
--Cerinta:Sa se afiseze toate garile si trenurile care orpesc in ele(chiar daca nu opreste niciun tren)
SELECT g.NumeGara, o.idTren, o.oraSosire
FROM Gara g
LEFT JOIN Opreste o ON g.NumeGara = o.NumeGara;

--Afisati toate opririle trenurilor si garile lor chiar dacă o oprire nu corespunde unei gări existente în tabelul Gara.
SELECT g.NumeGara, o.idTren, o.oraSosire
FROM Gara g
RIGHT JOIN Opreste o ON g.NumeGara = o.NumeGara;

--Afișați toate trenurile și toate vagoanele lor, chiar dacă 
--unele trenuri nu au vagoane și unele vagoane nu corespund niciunui tren.
SELECT t.idTren, t.tip, v.NumarVagon
FROM Tren t
FULL OUTER JOIN Vagon v ON t.idTren = v.idTren;


--Interogare 8
--Cerinta: Sa se afiseze numarul de trenuri pentru fiecare operator
SELECT NumeOperator, COUNT(*) AS NrTrenuri
FROM Tren
GROUP BY NumeOperator;

--Interogare 9:
--Cerinta: Sa se afiseze numarul de bilete vandute per tren:
SELECT idTren, COUNT(*) AS NrBilete
FROM Bilet
GROUP BY idTren;

--Interogare 10:
--Cerință: Sa se afiseze pretul mediu al biletelor pentru fiecare tren
SELECT idTren, AVG(pret) AS PretMediu
FROM Bilet
GROUP BY idTren;

--Interogare 11
--Sa se afiseze numarul total de locuri disponibile per tren
SELECT t.idTren, SUM(v.nrLocuriTotal) AS TotalLocuri
FROM Tren t
JOIN Vagon v ON t.idTren = v.idTren
GROUP BY t.idTren;

--Interogare 12
--Sa se afiseze salariul mediu al angajatilor per gara
SELECT NumeGara, AVG(salariu) AS SalariuMediu
FROM Lucreaza
GROUP BY NumeGara;

--Interogare 13
--Sa se afiseze numarul total de opriri pentru fiecare tren:
SELECT 
    t.idTren,
    COUNT(o.NrOprire) AS NrOpriri
FROM Tren t
LEFT JOIN Opreste o ON t.idTren = o.idTren
GROUP BY t.idTren
ORDER BY t.idTren;

--Interogare 14
--Sa se afiseze numarul de bilete pentru fiecare tip de tren
SELECT 
    t.tip,
    COUNT(b.idBilet) AS NrBilete
FROM Tren t
LEFT JOIN Bilet b ON t.idTren = b.idTren
GROUP BY t.tip
ORDER BY NrBilete DESC;

--Interogare 15
--Cerinta: Sa se afiseze calatorii care au cumparat bilete pentru trenurile operate de CFR Calatori
select c.nume,c.prenume
from calator C
where exists(select 1 
			from bilet b
			JOIN Tren t ON b.idTren = t.idTren
			JOIN Operator o ON t.NumeOperator = o.NumeOperator
			WHERE b.nrLegitimatie = c.nrLegitimatie
			AND o.NumeOperator = 'CFR Calatori' 
);

--Interogare 16
--Cerinta: Sa se afiseze trenurile care au mai multe bilete vândute decât media tuturor trenurilor
select t.idTren, t.tip
from Tren t
where (select count(*)
    	from Bilet b
    	where b.idTren = t.idTren) > 
	  (select avg(NrBilete)
    from (select count(*) as NrBilete
          from Bilet b2
          group by b2.idTren) sub
);

--Interogare 17
--Cerinta: Sa se afiseze garile unde lucreaza angajati ce au salariul mai mare decat media angajatilor de la aceeasi gara
select g.numegara
from gara g
where exists(select 1
from lucreaza l,angajat a
where a.idangajat = l.idangajat
and l.numegara = g.numegara
and l.salariu>
(select avg(salariu) from lucreaza where numegara = g.numegara));


--Interogare 18
--Cerinta: Sa se afiseze vagoanele trenurilor care au vandut bilete de peste 50 lei.
select v.numarvagon, v.clasa, v.idTren
from vagon v
where exists(select 1
from bilet b 
join tren t on t.idtren = v.idtren
where b.idtren = t.idtren
and b.pret>50);

--Interogare 19
--Sa se afiseze trenurile care au numar de vagoane mai mare decat media tuturor trenurilor
select idTren,tip,nrVagoane
from tren 
where nrVagoane>(select avg(nrVagoane) from tren);
--aici implicat doar tabela tren

select t.idTren, t.tip
from Tren t
where t.nrVagoane > (
    select avg(t2.nrVagoane)
    from Tren t2
    join Bilet b ON t2.idTren = b.idTren
    join Vagon v ON t2.idTren = v.idTren
);

--Interogare 20
--Sa se afiseze operatorii care au trenuri pe rute cu mai mult de 5 statii
select numeoperator
from operator
where numeoperator in ( 
	select t.numeoperator
	from tren t
	join ruta r on r.numeruta = t.numeruta
	where r.nrstatii>5);	
	
--Interogare 21
--Sa se afiseze călătorii care au cumpărat bilete mai scumpe decât prețul mediu al tuturor biletelor
select nume,prenume,nrlegitimatie 
from calator
where nrlegitimatie in (select nrlegitimatie from bilet where pret>(select avg(pret) from bilet));

select c.nume, c.prenume
from Calator c
where c.nrLegitimatie IN (
    select b.nrLegitimatie
    from Bilet b
    join Tren t ON b.idTren = t.idTren
    where b.pret > (
        select AVG(b2.pret)
        from Bilet b2
        join Tren t2 ON b2.idTren = t2.idTren
    )
);

--Interogare 22
--Sa se afiseze gările care apar pe rute ce au durată mai mare de 6 ore
select numegara
from gara
where numegara in (select rg.numegara from ruta_gara rg
join ruta r on r.numeruta= rg.numeruta
and r.duratacalatoriei>interval '6 hours');

--Interogare 23:
--Cerinta: Sa se afiseze calatorii care au cumparat bilete pentru trenuri operate de operatori 
--care circula pe rute cu mai mult de 5 statii.
select c.nume,c.prenume
from calator c
where c.nrlegitimatie in(select b.nrlegitimatie from bilet b
join tren t on t.idtren = b.idtren
join ruta r on r.numeruta = t.numeruta
join operator o on o.numeoperator = t.numeoperator
where r.nrStatii>5);

--Interogare 24
--Sa se afiseze călătorii care au cumpărat bilete pentru trenuri operate de operatori 
--care circulă pe rute cu mai mult de 5 stații
select c.nume,c.prenume
from calator c 
where c.nrLegitimatie = ANY (
    select b.nrLegitimatie
    from Bilet b
    join Tren t ON b.idTren = t.idTren
    join Ruta r ON t.NumeRuta = r.NumeRuta
    join operator o ON t.NumeOperator = o.NumeOperator
    where r.nrStatii > 5
);

--Interogare 25
--Sa se afiseze numele operatorilor cu majuscule
SELECT UPPER(NumeOperator) AS Operator
FROM Operator;


--Interogare 26
--Sa se afiseze lungimea numelui garilor
SELECT NumeGara, LENGTH(NumeGara) AS LungimeNume
FROM Gara;

--Interogare 27
--Sa se afiseze vechimea biletelor fata de data curenta
SELECT idBilet, AGE(CURRENT_DATE, dataCumpararii) AS VechimeBilet
FROM Bilet;

--Interogare 28
--Sa se afiseze anul cumpararii biletelor
SELECT idBilet, EXTRACT(YEAR FROM dataCumpararii) AS AnCumparare
FROM Bilet;

--Interogare 29
--Clasificarea pretului biletelor
SELECT 
    idBilet,
    pret,
    CASE
        WHEN pret < 50 THEN 'Ieftin'
        WHEN pret BETWEEN 50 AND 100 THEN 'Mediu'
        ELSE 'Scump'
    END AS CategoriaPret
FROM Bilet;

--Interogare 30
--Starea locurilor din vagoane
SELECT 
    NumarVagon,
    nrLocuriLibere,
    nrLocuriTotal,
    CASE
        WHEN nrLocuriLibere = 0 THEN 'Plin'
        WHEN nrLocuriLibere < nrLocuriTotal / 2 THEN 'Semi-Plin'
        ELSE 'Disponibil'
    END AS StareVagon
FROM Vagon;

--Interogare 31
--Case status bilet
SELECT 
    idBilet,
    tipReducere,
    status,
    CASE
        WHEN status = 'anulat' THEN 'Bilet anulat'
        WHEN tipReducere = 'Student' THEN 'Bilet cu reducere student'
        WHEN tipReducere = 'Pensionar' THEN 'Bilet cu reducere pensionar'
        ELSE 'Bilet standard'
    END AS DescriereBilet
FROM Bilet;

--Vedere bilete valabile
CREATE VIEW V_BileteValabile AS
SELECT idBilet, idTren, nrLegitimatie, pret, status
FROM Bilet
WHERE status = 'valid';

--Vedere trenuri si operatori
CREATE VIEW V_TrenOperator AS
SELECT t.idTren, t.tip, t.nrVagoane, o.NumeOperator
FROM Tren t
JOIN Operator o ON t.NumeOperator = o.NumeOperator;

--View:Locuri ocupate
CREATE VIEW V_VagoaneOcupare AS
SELECT NumarVagon, idTren, nrLocuriLibere, nrLocuriTotal,
       nrLocuriTotal - nrLocuriLibere AS LocuriOcupate
FROM Vagon;

--View: afișează informații despre bilete împreună cu numele călătorilor.
CREATE VIEW V_BileteCalatori AS
SELECT b.idBilet, b.pret, b.status, c.nume, c.prenume, c.tip AS TipCalator
FROM Bilet b
JOIN Calator c ON b.nrLegitimatie = c.nrLegitimatie;

--View:afișează trenurile care circulă pe rute lungi (mai mult de 5 stații).
CREATE VIEW V_TrenuriRuteLungi AS
SELECT t.idTren, t.tip, t.nrVagoane, t.NumeOperator, r.NumeRuta, r.nrStatii
FROM Tren t
JOIN Ruta r ON t.NumeRuta = r.NumeRuta
WHERE r.nrStatii > 5;

-- LMD: modificare status bilet prin vedere
UPDATE V_BileteValabile
SET status = 'anulat'
WHERE idBilet = 1;

-- Exemplu nepermis
UPDATE V_TrenOperator
SET tip = 'InterCity'
WHERE idTren = 1590;

--Exemplu-index pentru bilet pentru optimizarea interogarii:
SELECT *
FROM Bilet
WHERE status = 'valid' AND idTren = 5050;

--indexul v-a fii urmatorul
CREATE INDEX idx_bilet_status_tren
ON Bilet (status, idTren);


--cautare tren dupa ruta
SELECT t.idTren, t.tip, t.numar, t.clase, t.nrVagoane,
       o.NumeOperator, r.NumeRuta
FROM Tren t
JOIN Operator o ON t.NumeOperator = o.NumeOperator
JOIN Ruta r ON t.NumeRuta = r.NumeRuta
WHERE r.NumeRuta = 'Iasi-Brasov'; --de exemplu
