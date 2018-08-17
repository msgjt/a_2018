-- User test data
/*INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Dorel1', '1', '07414141', 'dorel1@a.com','doreld', '1234', 1);
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Dore2', '1', '07414141', 'dorel2@a.com','doreld1', '1234', 1 );
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Dore3', '1', '07414141', 'dore3@a.com','ddorel', '1234', 1);
*/
-- other tables TODO


-- User test data
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Ioan', 'Ioan', '07414141', 'ioan@msggroup.com','ioani', '1234', 1);
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Gigi', 'Gigi', '07414141', 'gigi@msggroup.com','gigig', '1234', 1 );
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Titus', 'Titus', '07414141', 'titus3@msggroup.com','titust', '1234', 1);
-- other tables TODO
INSERT INTO ROLES (type) VALUES ('ADM');
INSERT INTO ROLES (type) VALUES ('PM');
INSERT INTO ROLES (type) VALUES ('TM');
INSERT INTO ROLES (type) VALUES ('DEV');
INSERT INTO ROLES (type) VALUES ('TEST');

-- permisions

INSERT  INTO permissions (type, description) VALUE ('PERMISSION_MANAGEMENT','test');
INSERT  INTO permissions (type, description) VALUE ('USER_MANAGEMENT','test');
INSERT  INTO permissions (type, description) VALUE ('BUG_MANAGEMENT','test');
INSERT  INTO permissions (type, description) VALUE ('BUG_CLOSE','test');
INSERT  INTO permissions (type, description) VALUE ('BUG_EXPORT_PDF','test');
INSERT  INTO permissions (type, description) VALUE ('ADRESSED_USER','test');