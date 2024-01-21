INSERT INTO customer_accounts (f_name, m_name, l_name, address, zipcode, p_number, company_name, contract, balans)
VALUES
    ('kay', 'theo', 'heskes', 'dintelstraat 106-2', '1078VZ', '0646714350', 'flowers', 'unknown', 1000.00 ),
    ('Jan', 'Klaas', 'Dijk', 'Kerkstraat 42', '1234 AB', '0612345678', 'Jans Bloemen', 'Jaarcontract', 1000.50);

INSERT INTO employee_accounts( f_name, m_name, l_name, dob, address, zipcode, p_number, contract_h, start_contract )
VALUES
    ( 'Jeroen', '-', 'dijk', '1975-10-25', 'Hoofddorpplein 66', '4578 CD', '0646789856', 38.0, '1991-02-25'),
    ( 'Marie', 'van', 'Bergen', '1980-04-15', 'Kerkstraat 12', '1234 AB', '0612345678', 40.0, '2005-06-01');

INSERT INTO invoices ( type_of_work, price, type_of_product, business_tax_number, amount, tax_amount, date, business_address, customer_address, term_of_payment)
VALUES
    ( 'Kantoorreiniging', 250.00, 'Dienst', 'BT1234567', 1, 50, '10-01-2024', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorcomplex A, 5678 CD Dorp', '30 dagen'),
    ( 'Industriële reiniging', 1500.00, 'Dienst', 'BT7654321', 1, 300, '2024-01-20', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Fabrieksweg 10, 6789 EF Dorp', '60 dagen'),
    ( 'Raamreiniging', 100.00, 'Dienst', 'BT1928374', 1, 20, '2024-02-05', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorgebouw B, 8901 GH Dorp', '30 dagen');

INSERT INTO manager_accounts ( f_name, m_name, l_name, dob, address, zipcode, p_number, responsibilities)
VALUES
    ( 'Emma', '', 'Smith', '1985-06-15', 'Hoofdstraat 100 Haarlem', '1234 ZX', '0612345678', 'Algemeen management'),
    ( 'Liam', '', 'Johnson', '1978-04-22', 'Zijstraat 25 Hoofddorp', '5678 AB', '0698765432', 'Financieel management'),
    ( 'Olivia', '', 'Williams', '1990-11-30', 'Achterweg 40 Groningen', '9012 CD', '0678901234', 'Operationeel management');

INSERT INTO orders (type_of_work, amount, price, product_id, product_name, customer_name, status, date_created, time, work_address, work_zipcode)
VALUES
    ('Schoonmaken', 10, 150.00, 1, 'Schoonmaakmiddelen', 'Bedrijf A', 'In behandeling', '2024-01-10', '08:00', 'Hoofdstraat 1', '1234 AB'),
    ('Ramen lappen', 20, 200.00, 2, 'Raamreiniger', 'Bedrijf B', 'Voltooid', '2024-01-15', '09:00', 'Zijstraat 2', '5678 CD'),
    ('Vloerreiniging', 5, 100.00, 3, 'Vloerreiniger', 'Bedrijf C', 'Gepland', '2024-01-20', '10:00', 'Achterweg 3', '9012 EF');

INSERT INTO work_schedule (date, time, available, absence, sick, manager_available)
VALUES
    ('2024-01-10', '08:00', '2024-01-10 08:00', NULL, NULL, 'Manager A'),
    ('2024-01-15', '09:00', NULL, '2024-01-15 09:00', NULL, 'Manager B'),
    ('2024-01-20', '10:00', '2024-01-20 10:00', NULL, '2024-01-20', 'Manager C');

INSERT INTO users(username, password)
VALUES
    ('kay','$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu'),
    ('nancy', '$2a$12$t7BzVOLmQFSFzijkENoC7eQfRbnG.VuRea3HzIO8SSBJofJjqORA.');


INSERT INTO roles(rolename)
VALUES
    ('ROLE_EMPLOYEE'), ('ROLE_MANAGER'), ('ROLE_CUSTOMER');