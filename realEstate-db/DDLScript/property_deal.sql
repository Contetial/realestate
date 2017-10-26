
CREATE TABLE property_deal (
	`property_id` BIGINT NOT NULL,
    `deal_id` BIGINT NOT NULL,
    PRIMARY KEY (`property_id`, `deal_id`),
    CONSTRAINT `FK_PROPERTY` FOREIGN KEY (`property_id`) REFERENCES `property` (`property_id`),
    CONSTRAINT `FK_DEAL` FOREIGN KEY (`deal_id`) REFERENCES `deal` (`deal_id`)
);

INSERT INTO `property_deal` VALUES (1,1);
