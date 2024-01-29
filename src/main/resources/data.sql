INSERT INTO roles(role_name)
VALUES
    ('ROLE_EMPLOYEE'), ('ROLE_MANAGER'), ('ROLE_CUSTOMER');

-- all users have the same passwords.
INSERT INTO users (id, username, password, enabled, f_name, m_name, l_name, email, dob, address, zipcode, residence, p_number, house_number)
VALUES
    (1, 'kay', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Kay-Arne', 'Fratheo', 'Heskes', 'kay@example.com', '1991-11-22', 'Hoofdstraat 100', '1234 ZX', 'Haarlem', '0612345678', '100'),
    (2, 'nancy', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Nancy', '', 'Prins', 'nancy@example.com', '1995-10-25', 'Hoofddorpplein 66', '4578 CD', 'Stad', '0646789856', '66'),
    (3, 'Hans', '$2a$12$gIM5QtvhuxzXi7NeyTov7.UediBWZN8yp/fTmsLvqSIpjOHX2HcCu', TRUE, 'Hans', 'Klaas', 'Dijk', 'hans@example.com', '1985-05-15', 'Kerkstraat 42', '1234 AB', 'Dorp', '0612345678', '42');

INSERT INTO users_roles(user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE username = 'kay'), (SELECT id FROM roles WHERE role_name = 'ROLE_MANAGER')),
    ((SELECT id FROM users WHERE username = 'nancy'), (SELECT id FROM roles WHERE role_name = 'ROLE_EMPLOYEE')),
    ((SELECT id FROM users WHERE username = 'Hans'), (SELECT id FROM roles WHERE role_name = 'ROLE_CUSTOMER'));

INSERT INTO customer_accounts (id, company_name, contract, balans)
VALUES
    (1, 'Jans Bloemen', 'Jaarcontract', 1000.50);

INSERT INTO employee_accounts (id, contract_h, start_contract)
VALUES
    (1, 38.0, '1991-02-25');

INSERT INTO manager_accounts (id, responsibilities)
VALUES
    (1, 'Algemeen management');

INSERT INTO invoices ( type_of_work, price, type_of_product, business_tax_number, amount, tax_amount, date, business_address, customer_address, term_of_payment)
VALUES
    ( 'Kantoorreiniging', 250.00, 'Dienst', 'BT1234567', 1, 50, '10-01-2024', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorcomplex A, 5678 CD Dorp', '30 dagen'),
    ( 'Industriële reiniging', 1500.00, 'Dienst', 'BT7654321', 1, 300, '2024-01-20', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Fabrieksweg 10, 6789 EF Dorp', '60 dagen'),
    ( 'Raamreiniging', 100.00, 'Dienst', 'BT1928374', 1, 20, '2024-02-05', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorgebouw B, 8901 GH Dorp', '30 dagen');

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


