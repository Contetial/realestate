CREATE TABLE video_details (
	video_id BIGINT NOT NULL AUTO_INCREMENT,
	video_name VARCHAR(100) NOT NULL,
	video_path VARCHAR(100) NOT NULL,
	vthumb_address VARCHAR(225),
	gallery_id BIGINT NOT NULL,
	PRIMARY KEY (video_id),		
	CONSTRAINT `FK_VIDEO_GALLERY` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`)
);


INSERT INTO `video_details` VALUES (1,'vid1','C:\Users\Public\Videos\Sample Videos','vadd1',1);

alter table video_details change gallary_id gallery_id BIGINT NOT NULL;

alter table video_details drop foreign key `FK_VIDEO_GALLARY`;

alter table video_details add CONSTRAINT `FK_VIDEO_GALLERY` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`);