
CREATE TABLE customer (
	customer_id BIGINT NOT NULL Auto_increment,
	customer_name VARCHAR(100) NOT NULL,
	address VARCHAR(225),
	DOB timestamp,
	DOA timestamp,
	contactNo BIGINT(13),
	email varchar(100),
	status varchar(20),
	refered_by BIGINT NOT NULL,
	PRIMARY KEY (customer_id),
	CONSTRAINT `FK_USER_CUSTOMER` FOREIGN KEY (`refered_by`) REFERENCES `app_user` (`user_id`)
);

INSERT INTO `customer` VALUES (1,'John','Nagpur','1990/10/10','2010/10/10',1234567890,'new');

alter table customer add column refered_by BIGINT NOT NULL default 1;

alter table customer add CONSTRAINT `FK_USER_CUSTOMER` FOREIGN KEY (`refered_by`) REFERENCES `app_user` (`user_id`);
