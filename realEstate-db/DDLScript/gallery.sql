CREATE TABLE gallery (
	gallery_id BIGINT NOT NULL AUTO_INCREMENT,
	property_id BIGINT NOT NULL,	
	PRIMARY KEY (gallery_id),
	CONSTRAINT `FK_GALLERY_PROPERTY` FOREIGN KEY (`property_id`) REFERENCES `property` (`property_id`)	
);


INSERT INTO `gallery` VALUES (1,1);

alter table gallery change gallary_id gallery_id BIGINT not null auto_increment;
alter table gallery drop primary key, add primary key(gallery_id);
alter table gallery add primary key(gallery_id);

select * from gallery;
--FOREIGN KEY (prop_id) REFERENCES gallery_property(prop_id),
	--FOREIGN KEY (img_id) REFERENCES image_details(img_id),
	--FOREIGN KEY (video_id) REFERENCES video_details(video_id)

--CREATE INDEX prop_id ON gallery (prop_id ASC);

--CREATE INDEX img_id ON gallery (img_id ASC);

--CREATE INDEX video_id ON gallery (video_id ASC);
