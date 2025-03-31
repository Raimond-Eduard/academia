-- Script de populare baza de date in versiune beta

-- Pentru studenti

INSERT INTO students VALUES 
	(1, 'Popescu', 'Andrei', 'andrei.popescu@student.tuiasi.ro', 'licenta', 1, 1107);

INSERT INTO students(nume, prenume, email, ciclu_studii, an_studiu, grupa)
	VALUES 
		('Cioran', 'Emilian', 'emilian.cioran@student.tuiasi.ro', 'licenta', 2,1208),
		('Ion', 'Creanga', 'creanga.ion@student.tuiasi.ro', 'licenta', 3,1309),
		('Ivanovici', 'Mihai', 'mihai.ivanovici@student.tuiasi.ro','licenta', 4, 1410),
		('Baloz', 'Tom', 'tom.baloz@student.tuiasi.ro', 'master', 1, 2104),
		('Smurdui', 'Georgian', 'georgian.smurdui@student.tuiasi.ro', 'master', 2, 2206);
		
-- Pentru cadre didactire
	
INSERT INTO cadre_didactice VALUES 
	(1, 'Puscasu', 'Marin', 'marin.puscasu@academic.tuiasi.ro', 'prof', 'titular', NULL);
	

INSERT INTO cadre_didactice (nume, prenume, email, grad_didactic, tip_asociere)
	VALUES
		('Mogosoi', 'Umbrarescu', 'umbrarescu.mogosoi@academic.tuiasi.ro', 'conf', 'titular'),
		('Virgil', 'Dumitru', 'dumitru.virgil@academic.tuiasi.ro', 'sef lucr', 'asociat'),
		('Bratan', 'John', 'john.bratan@academic.tuiasi.ro', 'asist', 'asociat');
		
-- Pentru discipline
-- TBD
	
commit;