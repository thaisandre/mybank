-- clients
INSERT INTO Client (name, exclusive, balance, account_number, birth_date) VALUES ('John', false, 3000.0, '123-4', '1980-10-02');
INSERT INTO Client (name, exclusive, balance, account_number, birth_date) VALUES ('Maria', false, 2400.0, '123-5', '1985-08-20');
INSERT INTO Client (name, exclusive, balance, account_number, birth_date) VALUES ('David', false, 300.0, '123-6', '2001-05-03');
INSERT INTO Client (name, exclusive, balance, account_number, birth_date) VALUES ('Roger', false, 3000.0, '123-7', '1980-10-02');

-- transactions
INSERT INTO Transaction(created_at, client_id, type, value, administrative_tax) VALUES ('2019-06-19 10:00:00', 1, 'WITHDRAW', 50.0, 0.0);
INSERT INTO Transaction(created_at, client_id, type, value, administrative_tax) VALUES ('2020-04-15 09:30:00', 1, 'WITHDRAW', 101.0, 0.4);
INSERT INTO Transaction(created_at, client_id, type, value, administrative_tax) VALUES ('2020-05-03 20:00:00', 1, 'WITHDRAW', 301.0, 1.0);
INSERT INTO Transaction(created_at, client_id, type, value, administrative_tax) VALUES ('2020-05-03 20:00:00', 1, 'DEPOSIT', 301.0, 0.0);
