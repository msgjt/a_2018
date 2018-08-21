-- other tables TODO


-- User test data
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Ioan', 'Ioan', '07414141', 'ioan@msggroup.com','ioani', 'ëØ[SŽL›@@í+<nS·', 1);
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Gigi', 'Gigi', '07414141', 'gigi@msggroup.com','gigig', 'ëØ[SŽL›@@í+<nS·', 1 );
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Titus', 'Titus', '07414141', 'titus3@msggroup.com','titust', 'ëØ[SŽL›@@í+<nS·', 1);
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

--roles permisions

insert into roles_permissions (Role_ID, permissions_ID) VALUES (1,1);
insert into roles_permissions (Role_ID, permissions_ID) VALUES (1,2);
insert into roles_permissions (Role_ID, permissions_ID) VALUES (1,3);
insert into roles_permissions (Role_ID, permissions_ID) VALUES (1,4);
insert into roles_permissions (Role_ID, permissions_ID) VALUES (1,5);
insert into roles_permissions (Role_ID, permissions_ID) VALUES (1,6);

--user roles

insert into users_roles (User_ID, roles_ID) VALUES (1,1);