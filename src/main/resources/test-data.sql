INSERT IGNORE INTO Users(id, first_name, last_name, email, created_at) VALUES
(1, 'Pepe', 'Argento', 'test@app.com.ar', now()),
(2, 'Monica', 'Argento', 'test_moni@app.com.ar', now());



INSERT IGNORE INTO Loans(id, user_id, amount, created_at) VALUES
(1, 1, 2500, now()),
(2, 1, 65120.75, now());