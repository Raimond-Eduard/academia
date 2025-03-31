CREATE TABLE students (
	ID INT Primary Key AUTO_INCREMENT,
	nume 	VARCHAR(50) NOT NULL,
	prenume VARCHAR(50) NOT NULL,
	email VARCHAR(50) UNIQUE,
	ciclu_studii ENUM('licenta', 'master'),
	an_studiu INT NOT NULL,
	grupa INT NOT NULL
); 

CREATE TABLE cadre_didactice(
	ID INT PRIMARY KEY AUTO_INCREMENT,
	nume VARCHAR(50) NOT NULL,
	prenume VARCHAR(50) NOT NULL,
	email VARCHAR(50) UNIQUE,
	grad_didactic ENUM('asist', 'sef lucr', 'conf', 'prof'),
	tip_asociere ENUM('titular', 'asociat', 'extern') NOT NULL,
	afiliere VARCHAR(30)
	
);

CREATE TABLE discipline (
    COD VARCHAR(255) PRIMARY KEY,
    ID_titular INT,
    nume_disciplina VARCHAR(100) NOT NULL,
    an_studiu INT NOT NULL,
    tip_disciplina ENUM('impusa', 'optionala', 'liber_aleasa') NOT NULL,
    categorie_disciplina ENUM('domeniu', 'specialitate', 'adiacenta') NOT NULL,
    tip_examinare ENUM('examen', 'colocviu') NOT NULL,
    CONSTRAINT FK_ProfesorID_Disciplina
        FOREIGN KEY (ID_titular) REFERENCES cadre_didactice (ID)
);

