
INSERT INTO kategorija (kategorija_ime, kategorija_opis) VALUES ('Pisala', 'Kemicni svincniki in nalivna peresa.');
INSERT INTO izdelek (izdelek_ime, izdelek_opis, kategorija_id,stevilo_nakupov) VALUES ('Nalivno pero', 'Kakovostno nalivno pero zelene barve.', 1,0);
INSERT INTO izdelek (izdelek_ime, izdelek_opis, kategorija_id,stevilo_nakupov) VALUES ('Crnilo', 'Nalivno crnilo modre barve.', 1,0);
INSERT INTO trgovina(trgovina_ime,trgovina_lokacija) VALUES ('Spar','Maribor');
INSERT INTO trgovina(trgovina_ime,trgovina_lokacija) VALUES ('Mercator','Maribor');
INSERT INTO ceneVTrgovinah(cena,izdelek_id,trgovina_id) VALUES (10,1,1);
INSERT INTO ceneVTrgovinah(cena,izdelek_id,trgovina_id) VALUES (5,2,1);
INSERT INTO ceneVTrgovinah(cena,izdelek_id,trgovina_id) VALUES (7,1,2);
--INSERT INTO ceneTrgovinah(cena,izdelek_id,trgovina_id) VALUES (3,2,2);


