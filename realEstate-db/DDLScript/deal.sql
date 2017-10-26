
CREATE TABLE deal (
	deal_id BIGINT NOT NULL Auto_increment,
	user_id BIGINT NOT NULL,
	customer_id BIGINT NOT NULL,
	property_id BIGINT NOT NULL,
	deal_status varchar(20),
	PRIMARY KEY (deal_id)
);

INSERT INTO `deal` VALUES (1,1,1,'in-progress');

Alter table deal add column property_id BIGINT NOT NULL default 1;
