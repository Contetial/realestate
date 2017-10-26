
CREATE TABLE deal_logbook (
	logbook_id BIGINT NOT NULL Auto_increment,
	deal_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	creation_date timestamp,
	log_entry VARCHAR(200),
	log_status VARCHAR(30),
	PRIMARY KEY (logbook_id),
	CONSTRAINT `FK_logbook_DEAL` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`),
	CONSTRAINT `FK_logbook_USER` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`user_id`)
);

INSERT INTO `deal_logbook` VALUES (1,1,1,'2015-4-22','Test log 1','logged');
