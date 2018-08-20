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

INSERT  INTO permissions (type, description) VALUES ('PERMISSION_MANAGEMENT','test');
INSERT  INTO permissions (type, description) VALUES ('USER_MANAGEMENT','test');
INSERT  INTO permissions (type, description) VALUES ('BUG_MANAGEMENT','test');
INSERT  INTO permissions (type, description) VALUES ('BUG_CLOSE','test');
INSERT  INTO permissions (type, description) VALUES ('BUG_EXPORT_PDF','test');
INSERT  INTO permissions (type, description) VALUES ('ADRESSED_USER','test');

INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (1,1);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (1,2);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (1,3);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (1,4);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (1,5);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (1,6);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (2,1);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (3,1);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (4,3);
INSERT INTO roles_permissions (Role_ID, permissions_ID) VALUES (4,4);