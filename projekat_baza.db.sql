BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "EQUIPMENT" (
	"equipment_id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"info"	TEXT,
	PRIMARY KEY("equipment_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET_STORAGE" (
	"motor_fleet_id"	INTEGER NOT NULL,
	"storage_id"	INTEGER NOT NULL,
	PRIMARY KEY("motor_fleet_id","storage_id"),
	FOREIGN KEY("motor_fleet_id") REFERENCES "MOTOR_FLEET"("motor_fleet_id"),
	FOREIGN KEY("storage_id") REFERENCES "STORAGE"("storage_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET_USER" (
	"motor_fleet_id"	INTEGER NOT NULL,
	"user_id"	INTEGER NOT NULL,
	PRIMARY KEY("motor_fleet_id","user_id"),
	FOREIGN KEY("motor_fleet_id") REFERENCES "MOTOR_FLEET"("motor_fleet_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET_VEHICLE" (
	"motor_fleet_id"	INTEGER NOT NULL,
	"vehicle_id"	INTEGER NOT NULL,
	PRIMARY KEY("motor_fleet_id","vehicle_id"),
	FOREIGN KEY("motor_fleet_id") REFERENCES "MOTOR_FLEET"("motor_fleet_id")
);
CREATE TABLE IF NOT EXISTS "PART" (
	"part_id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"is_useful"	INTEGER NOT NULL,
	PRIMARY KEY("part_id")
);
CREATE TABLE IF NOT EXISTS "SERVICE" (
	"service_id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"date"	TEXT NOT NULL,
	"service_persone"	TEXT NOT NULL,
	"info"	TEXT,
	PRIMARY KEY("service_id")
);
CREATE TABLE IF NOT EXISTS "USER_EMPLOYEE" (
	"user_id"	INTEGER NOT NULL,
	"first_name"	TEXT NOT NULL,
	"last_name"	TEXT NOT NULL,
	"telephone_number"	TEXT NOT NULL,
	"address"	TEXT NOT NULL,
	"username"	TEXT NOT NULL,
	"password"	TEXT NOT NULL,
	"is_administrator"	INTEGER NOT NULL,
	PRIMARY KEY("user_id")
);
CREATE TABLE IF NOT EXISTS "VEHICLE" (
	"vehicle_id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"type"	INTEGER NOT NULL,
	"licence_plate"	TEXT NOT NULL,
	"color"	TEXT,
	PRIMARY KEY("vehicle_id")
);
CREATE TABLE IF NOT EXISTS "SERVICE_MANAGEMENT" (
	"service_management_id"	INTEGER,
	"vehicle_id"	INTEGER,
	"service_id"	INTEGER,
	PRIMARY KEY("service_management_id"),
	FOREIGN KEY("service_id") REFERENCES "SERVICE"("service_id"),
	FOREIGN KEY("vehicle_id") REFERENCES "VEHICLE"("vehicle_id")
);
CREATE TABLE IF NOT EXISTS "STORAGE" (
	"storage_id"	INTEGER NOT NULL,
	"name"	TEXT NOT NULL,
	"manager_id"	INTEGER,
	PRIMARY KEY("storage_id"),
	FOREIGN KEY("manager_id") REFERENCES "USER_EMPLOYEE"("user_id")
);
CREATE TABLE IF NOT EXISTS "STORAGE_PARTS" (
	"storage_id"	INTEGER,
	"part_id"	INTEGER,
	PRIMARY KEY("storage_id","part_id"),
	FOREIGN KEY("storage_id") REFERENCES "STORAGE"("storage_id"),
	FOREIGN KEY("part_id") REFERENCES "PART"("part_id")
);
CREATE TABLE IF NOT EXISTS "VEHICLE_EQUIPMENT" (
	"vehicle_id"	INTEGER,
	"equipment_id"	INTEGER,
	PRIMARY KEY("vehicle_id","equipment_id"),
	FOREIGN KEY("equipment_id") REFERENCES "EQUIPMENT"("equipment_id"),
	FOREIGN KEY("vehicle_id") REFERENCES "VEHICLE"("vehicle_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET" (
	"motor_fleet_id"	INTEGER,
	"name"	TEXT,
	"manager_id"	INTEGER,
	PRIMARY KEY("motor_fleet_id"),
	FOREIGN KEY("manager_id") REFERENCES "USER_EMPLOYEE"("user_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET_PART" (
	"motor_fleet_id"	INTEGER NOT NULL,
	"part_id"	INTEGER NOT NULL,
	PRIMARY KEY("motor_fleet_id","part_id"),
	FOREIGN KEY("part_id") REFERENCES "PART"("part_id"),
	FOREIGN KEY("motor_fleet_id") REFERENCES "MOTOR_FLEET"("motor_fleet_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET_SERVICE" (
	"motor_fleet_id"	INTEGER NOT NULL,
	"service_id"	INTEGER NOT NULL,
	PRIMARY KEY("motor_fleet_id","service_id"),
	FOREIGN KEY("motor_fleet_id") REFERENCES "MOTOR_FLEET"("motor_fleet_id"),
	FOREIGN KEY("service_id") REFERENCES "SERVICE"("service_id")
);
CREATE TABLE IF NOT EXISTS "MOTOR_FLEET_EQUIPMENT" (
	"motor_fleet_id"	INTEGER NOT NULL,
	"equipment_id"	INTEGER NOT NULL,
	PRIMARY KEY("motor_fleet_id","equipment_id"),
	FOREIGN KEY("equipment_id") REFERENCES "EQUIPMENT"("equipment_id"),
	FOREIGN KEY("motor_fleet_id") REFERENCES "MOTOR_FLEET"("motor_fleet_id")
);
INSERT INTO "EQUIPMENT" ("equipment_id","name","info") VALUES (1,'Equipment 1','Information 1');
INSERT INTO "MOTOR_FLEET_STORAGE" ("motor_fleet_id","storage_id") VALUES (1,1);
INSERT INTO "MOTOR_FLEET_USER" ("motor_fleet_id","user_id") VALUES (1,2);
INSERT INTO "MOTOR_FLEET_USER" ("motor_fleet_id","user_id") VALUES (1,3);
INSERT INTO "MOTOR_FLEET_VEHICLE" ("motor_fleet_id","vehicle_id") VALUES (1,1);
INSERT INTO "MOTOR_FLEET_VEHICLE" ("motor_fleet_id","vehicle_id") VALUES (1,2);
INSERT INTO "MOTOR_FLEET_VEHICLE" ("motor_fleet_id","vehicle_id") VALUES (1,3);
INSERT INTO "PART" ("part_id","name","is_useful") VALUES (1,'Part 1',1);
INSERT INTO "PART" ("part_id","name","is_useful") VALUES (2,'Part 2',0);
INSERT INTO "SERVICE" ("service_id","name","date","service_persone","info") VALUES (1,'Service 1','2020-09-10','Person 1','Information 1');
INSERT INTO "USER_EMPLOYEE" ("user_id","first_name","last_name","telephone_number","address","username","password","is_administrator") VALUES (1,'Direktor','Direktor','000/000-001','Fakultet br. 1','dir123','dir123',7);
INSERT INTO "USER_EMPLOYEE" ("user_id","first_name","last_name","telephone_number","address","username","password","is_administrator") VALUES (2,'Vedran','Ljubovic','000/000-000','Fakultet br.1','v123','v123',1);
INSERT INTO "USER_EMPLOYEE" ("user_id","first_name","last_name","telephone_number","address","username","password","is_administrator") VALUES (3,'User 2','User 2','000/000-001','Address 1','user2','user2',3);
INSERT INTO "VEHICLE" ("vehicle_id","name","type","licence_plate","color") VALUES (1,'Motor vehicle',1,'000-0-001','Color1');
INSERT INTO "VEHICLE" ("vehicle_id","name","type","licence_plate","color") VALUES (2,'Heavy vehicle',2,'000-002','Color 2');
INSERT INTO "VEHICLE" ("vehicle_id","name","type","licence_plate","color") VALUES (3,'Trailer vehicle',3,'000-003','Color 3');
INSERT INTO "SERVICE_MANAGEMENT" ("service_management_id","vehicle_id","service_id") VALUES (1,1,1);
INSERT INTO "STORAGE" ("storage_id","name","manager_id") VALUES (1,'Storage 1',3);
INSERT INTO "STORAGE_PARTS" ("storage_id","part_id") VALUES (1,1);
INSERT INTO "STORAGE_PARTS" ("storage_id","part_id") VALUES (1,2);
INSERT INTO "VEHICLE_EQUIPMENT" ("vehicle_id","equipment_id") VALUES (1,1);
INSERT INTO "MOTOR_FLEET" ("motor_fleet_id","name","manager_id") VALUES (1,'First motor fleet',2);
INSERT INTO "MOTOR_FLEET_PART" ("motor_fleet_id","part_id") VALUES (1,1);
INSERT INTO "MOTOR_FLEET_PART" ("motor_fleet_id","part_id") VALUES (1,2);
INSERT INTO "MOTOR_FLEET_SERVICE" ("motor_fleet_id","service_id") VALUES (1,1);
INSERT INTO "MOTOR_FLEET_EQUIPMENT" ("motor_fleet_id","equipment_id") VALUES (1,1);
COMMIT;
