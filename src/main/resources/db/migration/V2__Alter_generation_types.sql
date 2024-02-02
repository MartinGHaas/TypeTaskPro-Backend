CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE tasks
ALTER COLUMN id TYPE VARCHAR(36),
ALTER COLUMN id SET DEFAULT (uuid_generate_v4()::varchar);

ALTER TABLE devices
ALTER COLUMN id TYPE VARCHAR(36),
ALTER COLUMN id SET DEFAULT (uuid_generate_v4()::varchar);

ALTER TABLE projects
ALTER COLUMN id SET DEFAULT nextval('project_id_generator'),
ALTER COLUMN device TYPE VARCHAR(36);

ALTER TABLE projects
RENAME COLUMN device TO device_id;

ALTER TABLE users
ALTER COLUMN id SET DEFAULT nextval('user_id_generator');