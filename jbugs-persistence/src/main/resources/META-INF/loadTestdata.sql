

DELETE FROM USERS_ROLES;
DELETE FROM ROLES_PERMISSIONS;


-- User test data
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Ioan', 'Ioan', '0720512346', 'ioan@msggroup.com','ioani', 'ëØ[SŽL›@@í+<nS·', 1);
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Gigi', 'Gigi', '0720512346', 'gigi@msggroup.com','gigig', 'ëØ[SŽL›@@í+<nS·', 1 );
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Titus', 'Titus', '0720512346', 'titus3@msggroup.com','titust', 'ëØ[SŽL›@@í+<nS·', 1);
-- other tables TODO
INSERT INTO ROLES (type) VALUES ('ADM');
INSERT INTO ROLES (type) VALUES ('PM');
INSERT INTO ROLES (type) VALUES ('TM');
INSERT INTO ROLES (type) VALUES ('DEV');
INSERT INTO ROLES (type) VALUES ('TESTER');

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
INSERT INTO users_roles (User_ID, roles_ID) VALUES (3,5);


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

INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",2,"Open","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"In progress","2018-08-21","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-22","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",2,"Open","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"In progress","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",2,"Open","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"In progress","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",2,"Open","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"In progress","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-22","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",2,"Open","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"In progress","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-21","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-22","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-21","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",3,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) VALUES("screen flickers","version 1",1,"Rejected","2018-08-23","Bug in Screen","version 2",3,"file");
INSERT INTO bugs(description,fixedVersion,severity,status,targetDate,title,version,assignedTo,attachment) values("screen flickers","version 1",2,"Info needed","2018-08-23","Bug in Screen","version 2",3,"file");

insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");
insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");
insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");
insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");
insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");
insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");
insert into notifications(URL,message,status,type) values("ulr.com","you have been notified","not_read","USER_UPDATED");

INSERT INTO users_notifications(date,notification_id,user_id) VALUES("2018-10-10",1,1);
INSERT INTO users_notifications(date,notification_id,user_id) VALUES("2018-10-12",2,1);
INSERT INTO users_notifications(date,notification_id,user_id) VALUES("2018-10-13",3,1);


INSERT INTO bugs_creators(bug_id,user_id) VALUES (1,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (2,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (3,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (4,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (5,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (6,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (7,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (8,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (9,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (10,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (11,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (12,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (13,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (14,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (15,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (16,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (17,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (18,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (19,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (20,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (21,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (22,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (23,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (24,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (25,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (26,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (27,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (28,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (29,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (30,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (31,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (32,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (33,1);
INSERT INTO bugs_creators(bug_id,user_id) VALUES (34,1);
