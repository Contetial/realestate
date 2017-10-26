
CREATE TABLE deal_schedule (
	deal_schedule_id BIGINT NOT NULL Auto_increment,
	deal_id BIGINT NOT NULL,
	deal_date timestamp,
	deal_from timestamp,
	deal_to timestamp,
	schedule_type varchar(100),
	PRIMARY KEY (deal_schedule_id),
	CONSTRAINT `FK_Schedule_DEAL` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`)	
);

INSERT INTO `deal_schedule` VALUES (1,1,'2015-4-22',null,null);

Alter table deal_schedule add column schedule_type varchar(100);

Alter table deal_schedule update column deal_date DATE;
