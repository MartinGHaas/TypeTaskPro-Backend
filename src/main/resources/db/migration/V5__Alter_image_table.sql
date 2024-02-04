DROP TABLE images;

CREATE TABLE profile_pictures (
  id VARCHAR(36) NOT NULL DEFAULT (uuid_generate_v4()::varchar),
  data OID NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE tasks_image (
  id VARCHAR(36) NOT NULL DEFAULT (uuid_generate_v4()::varchar),
  data OID NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE devices_image (
  id VARCHAR(36) NOT NULL DEFAULT (uuid_generate_v4()::varchar),
  data OID NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE users
ADD COLUMN profile_picture_id VARCHAR(36),
ADD CONSTRAINT FK_Users_Images FOREIGN KEY (profile_picture_id) REFERENCES profile_pictures;

ALTER TABLE tasks
ADD COLUMN image_id VARCHAR(36),
ADD CONSTRAINT FK_Tasks_Images FOREIGN KEY (image_id) REFERENCES tasks_image;

ALTER TABLE devices
ADD COLUMN image_id VARCHAR(36),
ADD CONSTRAINT FK_Devices_Images FOREIGN KEY (image_id) REFERENCES devices_image;