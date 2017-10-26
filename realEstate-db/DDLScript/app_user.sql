--<ScriptOptions statementTerminator=";"/>

CREATE TABLE app_user (
	user_id BIGINT NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL default 'password',
	address VARCHAR(225),
	user_role VARCHAR(50) default 'appuser',
	manager_id BIGINT NOT NULL,
	contactNo BIGINT(13),
	email varchar(100),
	PRIMARY KEY (user_id),
	CONSTRAINT `FK_APPUSER_MANAGER` FOREIGN KEY (`manager_id`) REFERENCES `app_user` (`user_id`)	
);

INSERT INTO `app_user` VALUES (1,'admin','$2a$10$uaHFSzYMgEKQo7kRIKTcO.rADdaIBNdGw0pbCzHRTbMjJOZRDgFH2','Nagpur','admin','1','123456789','support@contetial.com');

--Alter table app_user modify user_id BIGINT NOT NULL Auto_increment;

Alter table app_user add column manager_id BIGINT NOT NULL default 1;
Alter table app_user add CONSTRAINT `FK_APPUSER_MANAGER` FOREIGN KEY (`manager_id`) REFERENCES `app_user` (`user_id`);

Alter table app_user add column contactNo BIGINT(13);
Alter table app_user add column email varchar(100);
