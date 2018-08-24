-- User test data
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Ioan', 'Ioan', '0720512346', 'ioan@msggroup.com','ioani', 'ëØ[SŽL›@@í+<nS·', 1);
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Gigi', 'Gigi', '0720512346', 'gigi@msggroup.com','gigig', 'ëØ[SŽL›@@í+<nS·', 1 );
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Titus', 'Titus', '0720512346', 'titus3@msggroup.com','titust', 'ëØ[SŽL›@@í+<nS·', 1);
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

INSERT INTO users_roles (User_ID, roles_ID) VALUES (1,1);
INSERT INTO users_roles (User_ID, roles_ID) VALUES (1,2);
INSERT INTO users_roles (User_ID, roles_ID) VALUES (1,3);
INSERT INTO users_roles (User_ID, roles_ID) VALUES (2,1);
INSERT INTO users_roles (User_ID, roles_ID) VALUES (2,2);
INSERT INTO users_roles (User_ID, roles_ID) VALUES (3,3);
INSERT INTO users_roles (User_ID, roles_ID) VALUES (3,4);


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

INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,createdBy) VALUES("screen flickers","version 1",2,"New","23-08-2018","Bug in Screen","version 2",3,1);
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,createdBy) VALUES("screen flickers","version 1",1,"In progress","21-08-2018","Bug in Screen","version 2",3,1);
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,createdBy) VALUES("screen flickers","version 1",3,"Info needed","22-08-2018","Bug in Screen","version 2",3,1);
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,createdBy) VALUES("screen flickers","version 1",1,"Rejected","23-08-2018","Bug in Screen","version 2",3,1);
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,createdBy) values("screen flickers","version 1",2,"Info needed","23-08-2018","Bug in Screen","version 2",3,1);