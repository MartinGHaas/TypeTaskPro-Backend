ALTER TABLE users
DROP COLUMN profile_picture_id;

ALTER TABLE tasks
DROP COLUMN image_id,
ADD COLUMN limit_date TIMESTAMP;

ALTER TABLE devices
DROP COLUMN image_id;

ALTER TABLE devices_image
ADD COLUMN device_id VARCHAR(36),
ADD CONSTRAINT FK_Devices_Images FOREIGN KEY (device_id) REFERENCES devices;

ALTER TABLE profile_pictures
ADD COLUMN owner BIGINT,
ADD CONSTRAINT FK_Users_Images FOREIGN KEY (owner) REFERENCES users;

ALTER TABLE tasks_image
ADD COLUMN task_id VARCHAR(36),
ADD CONSTRAINT FK_Tasks_Images FOREIGN KEY (task_id) REFERENCES tasks;
