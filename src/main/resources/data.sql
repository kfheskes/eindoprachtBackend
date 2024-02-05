INSERT INTO roles(role_name)
VALUES
    ('ROLE_EMPLOYEE'), ('ROLE_MANAGER'), ('ROLE_CUSTOMER');

-- all users have the same passwords.
INSERT INTO users (id, username, password, enabled, f_name, m_name, l_name, email, dob, address, zipcode, residence, p_number, house_number)
VALUES
    (1, 'Kay', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Kay-Arne', 'Fratheo', 'Heskes', 'kay@example.com', '1991-11-22', 'Hoofdstraat 100', '1234 ZX', 'Haarlem', '0612345678', '100'),
    (2, 'Nancy', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Nancy', '', 'Prins', 'nancy@example.com', '1995-10-25', 'Hoofddorpplein 66', '4578 CD', 'Stad', '0646789856', '66'),
    (3, 'Hans', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Hans', 'Klaas', 'Dijk', 'hans@example.com', '1985-05-15', 'Kerkstraat 42', '1234 AB', 'Dorp', '0612345678', '42'),
    (4, 'Marie', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Marie', 'Alice', 'Smith', 'marie@example.com', '1990-08-15', 'Parkstraat 5', '2345 YZ', 'Amsterdam', '0623456789', '5'),
    (5, 'Peter', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Peter', 'Robert', 'Brown', 'peter@example.com', '1988-03-28', 'Kerkplein 12', '6789 AB', 'Rotterdam', '0634567890', '12'),
    (6, 'Sarah', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Sarah', '', 'Johnson', 'sarah@example.com', '1993-05-20', 'Dorpstraat 8', '3456 CD', 'Utrecht', '0645678901', '8');

INSERT INTO users_roles(user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'Kay'), (SELECT id FROM roles WHERE role_name = 'ROLE_MANAGER')),
    ((SELECT id FROM users WHERE username = 'Nancy'), (SELECT id FROM roles WHERE role_name = 'ROLE_EMPLOYEE')),
    ((SELECT id FROM users WHERE username = 'Hans'), (SELECT id FROM roles WHERE role_name = 'ROLE_CUSTOMER')),
    ((SELECT id FROM users WHERE username = 'Marie'), (SELECT id FROM roles WHERE role_name = 'ROLE_MANAGER')),
    ((SELECT id FROM users WHERE username = 'Peter'), (SELECT id FROM roles WHERE role_name = 'ROLE_EMPLOYEE')),
    ((SELECT id FROM users WHERE username = 'Sarah'), (SELECT id FROM roles WHERE role_name = 'ROLE_CUSTOMER'));

INSERT INTO customer_accounts (id, company_name, contract, balans)
VALUES
    (1, 'Jans Bloemen', 'Jaarcontract', 1000.50),
    (2, 'Van Houten BV', 'Maandcontract', 1500.75);

INSERT INTO employee_accounts (id, contract_h, start_contract)
VALUES
    (1, 38.0, '1991-02-25'),
    (2, 40.0, '1995-09-12');

INSERT INTO manager_accounts (id, responsibilities)
VALUES
    (1, 'Algemeen management'),
    (2, 'Operationeel management');


INSERT INTO invoices ( type_of_work, price, type_of_product, business_tax_number, amount, tax_amount, date, business_address, customer_address, term_of_payment)
VALUES
    ( '1', 250.00, 'Dienst', 'BT1234567', 1, 50, '2024-01-10', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorcomplex A, 5678 CD Dorp','DAGEN_30' ),
    ( '2', 1500.00, 'Dienst', 'BT7654321', 1, 300, '2024-01-20', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Fabrieksweg 10, 6789 EF Dorp','DAGEN_60' ),
    ( '3', 100.00, 'Dienst', 'BT1928374', 1, 20, '2024-12-10', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorgebouw B, 8901 GH Dorp', 'DAGEN_90' ),
    ( '2', 750.00, 'Dienst', 'BT5678901', 2, 150, '2024-05-05', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Winkelstraat 20, 1234 AB Dorp', 'DAGEN_60' ),
    ( '3', 300.00, 'Dienst', 'BT3456789', 3, 60, '2024-06-20', 'Industrieweg 3, 9012 GH Dorp', 'Kantorenplein 7, 3456 XY Stad', 'DAGEN_60' ),
    ( '1', 1200.00, 'Dienst', 'BT9876543', 5, 300, '2024-07-15', 'Magazijnweg 8, 6789 JK Stad', 'Winkelstraat 20, 1234 AB Dorp', 'DAGEN_30' );

INSERT INTO orders (type_of_work, amount, price, product_name, status, date_created, time, work_address, work_zipcode)
VALUES
    ('1', 10, 150.00, 'Schoonmaakmiddelen',  'In behandeling', '2024-10-01', '08:00', 'Hoofdstraat 1', '1234 AB'),
    ('2', 20, 200.00,  'Raamreiniger',  'Voltooid', '2024-01-15', '09:00', 'Zijstraat 2', '5678 CD'),
    ('3', 5, 100.00,  'Vloerreiniger',  'Gepland', '2024-03-01', '10:00', 'Achterweg 3', '9012 EF'),
    ('1', 2, 150.00, 'Schoonmaakmiddelen',  'Voltooid', '2024-02-05', '14:00', 'Hoofdstraat 1', '1234 AB'),
    ('2', 5, 200.00, 'Glasreiniger',  'In behandeling', '2024-07-03', '11:30', 'Zijstraat 2', '5678 CD'),
    ('3', 3, 100.00, 'Dweil',  'Gepland', '2024-04-20', '13:45', 'Achterweg 3', '9012 EF'),
    ('1', 4, 150.00, 'Bezem',  'Voltooid', '2024-10-05', '10:15', 'Hoofdstraat 1', '1234 AB'),
    ('2', 8, 200.00, 'Spons',  'In behandeling', '2024-06-06', '09:30', 'Zijstraat 2', '5678 CD'),
    ('3', 6, 100.00, 'Stofzuiger',  'Gepland', '2024-03-07', '12:00', 'Achterweg 3', '9012 EF');

INSERT INTO work_schedule (date, time, available, absence, sick, manager_available)
VALUES
    ('2024-01-10', '08:00', '2024-01-10 08:00', NULL, NULL, 'Kay'),
    ('2024-01-15', '09:00', NULL, '2024-01-15 09:00', NULL, 'Marie'),
    ('2024-01-20', '10:00', '2024-01-20 10:00', NULL, '2024-01-20', 'Kay'),
    ('2024-05-10', '10:15', NULL, '2024-05-10 10:15', NULL, 'Kay'),
    ('2024-06-25', '09:30', '2024-06-25 09:30', NULL, '2024-06-25', 'Marie'),
    ('2024-07-30', '12:00', NULL, '2024-07-30 12:00', NULL, 'Marie');



