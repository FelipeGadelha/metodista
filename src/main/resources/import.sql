SET session_replication_role = 'replica';

DELETE FROM cities;
DELETE FROM states;

SET session_replication_role = 'origin';

ALTER SEQUENCE cities_id_seq RESTART WITH 1;
ALTER SEQUENCE states_id_seq RESTART WITH 1;

INSERT INTO states (id, name) VALUES (1, 'Minas Gerais');
INSERT INTO states (id, name) VALUES (2, 'São Paulo');
INSERT INTO states (id, name) VALUES (3, 'Ceará');

SELECT nextval ('states_id_seq');
SELECT nextval ('states_id_seq');
SELECT nextval ('states_id_seq');

INSERT INTO cities (id, name, state_id) VALUES (1, 'Uberlândia', 1);
INSERT INTO cities (id, name, state_id) VALUES (2, 'Belo Horizonte', 1);
INSERT INTO cities (id, name, state_id) VALUES (3, 'São Paulo', 2);
INSERT INTO cities (id, name, state_id) VALUES (4, 'Campinas', 2);
INSERT INTO cities (id, name, state_id) VALUES (5, 'Fortaleza', 3);

SELECT nextval ('cities_id_seq');
SELECT nextval ('cities_id_seq');
SELECT nextval ('cities_id_seq');
SELECT nextval ('cities_id_seq');
SELECT nextval ('cities_id_seq');

INSERT INTO roles (id, name) VALUES (1, 'USER');
INSERT INTO roles (id, name) VALUES (2, 'ADMIN');

SELECT nextval ('roles_id_seq');
SELECT nextval ('roles_id_seq');
