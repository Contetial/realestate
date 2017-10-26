CREATE TABLE image_details (
	img_id BIGINT NOT NULL AUTO_INCREMENT,
	img_name VARCHAR(100) NOT NULL,
	img_path VARCHAR(100) NOT NULL,
	ithumb_address VARCHAR(225),
	gallery_id BIGINT NOT NULL,
	PRIMARY KEY (img_id),
	CONSTRAINT `FK_IMAGE_GALLERY` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`)
);

drop table image_details;

INSERT INTO `image_details` VALUES (1,'img1','C:\Users\Public\Pictures\Sample Pictures','add1',1);

alter table image_details change gallary_id gallery_id BIGINT NOT NULL;

alter table image_details drop foreign key `FK_IMAGE_GALLARY`;

alter table image_details drop INDEX `FK_IMAGE_GALLARY`;

alter table image_details add CONSTRAINT `FK_IMAGE_GALLERY_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`);
