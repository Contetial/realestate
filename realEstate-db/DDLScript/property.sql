
CREATE TABLE property (
	property_id BIGINT NOT NULL Auto_increment,
	property_name VARCHAR(100) NOT NULL,
	property_address VARCHAR(225),
	property_detailes VARCHAR(225),
	status varchar(20),
	PRIMARY KEY (property_id)
);

INSERT INTO `property` VALUES (1,'Flat 101, 1st Floor, Enclave',
'London Street, Jayprakash Nagar, Nagpur',
'3BHK, 1200 sq. ft., @4000/sq.ft, Lift, Shade Parking','new');
