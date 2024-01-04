INSERT INTO customer_accounts (id, f_name, m_name, l_name, address, zipcode, p_number, company_name, contract, balans)
VALUES
    (1, 'kay', 'theo', 'heskes', 'dintelstraat 106-2', '1078VZ', '0646714350', 'flowers', 'unknown', 1000.00 ),
    (2, 'Jan', 'Klaas', 'Dijk', 'Kerkstraat 42', '1234 AB', '0612345678', 'Jans Bloemen', 'Jaarcontract', 1000.50);



INSERT INTO employee_accounts(id, f_name, m_name, l_name, dob, address, zipcode, p_number, contract_h, start_contract )
VALUES
    (1, 'Jeroen', '-', 'dijk', '1975-10-25', 'Hoofddorpplein 66', '4578 CD', '0646789856', 38.0, '1991-02-25'),
    (2, 'Marie', 'van', 'Bergen', '1980-04-15', 'Kerkstraat 12', '1234 AB', '0612345678', 40.0, '2005-06-01');

INSERT INTO invoices (type_of_work, price, type_of_product, business_tax_number, amount, tax_amount, date, business_address, customer_address, term_of_payment)
VALUES
    ('Kantoorreiniging', 250.00, 'Dienst', 'BT1234567', 1, 50, '10-01-2024', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorcomplex A, 5678 CD Dorp', '30 dagen'),
    ('Industriële reiniging', 1500.00, 'Dienst', 'BT7654321', 1, 300, '2024-01-20', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Fabrieksweg 10, 6789 EF Dorp', '60 dagen'),
    ('Raamreiniging', 100.00, 'Dienst', 'BT1928374', 1, 20, '2024-02-05', 'Schoonmaakbedrijfstraat 1, 1234 AB Stad', 'Kantoorgebouw B, 8901 GH Dorp', '30 dagen');
