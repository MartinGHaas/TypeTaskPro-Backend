CREATE SEQUENCE project_id_generator MINVALUE 0 START WITH 0 INCREMENT BY 1;
CREATE SEQUENCE user_id_generator MINVALUE 0 START WITH 0 INCREMENT BY 1;

CREATE TABLE devices (
  name VARCHAR(15) NOT NULL UNIQUE,
  id VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE projects (
  id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  name VARCHAR(25) NOT NULL,
  device VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE projects_administrators (
  project_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (project_id, user_id)
);

CREATE TABLE projects_contributors (
  project_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  PRIMARY KEY (project_id, user_id)
);

CREATE TABLE tasks (
  project_id BIGINT,
  description VARCHAR(255),
  id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  role SMALLINT CHECK (role BETWEEN 0 AND 1),
  id BIGINT NOT NULL,
  username VARCHAR(15) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE projects ADD CONSTRAINT FK_Projects_Devices FOREIGN KEY (device) REFERENCES devices;
ALTER TABLE projects ADD CONSTRAINT FK_Projects_Users FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE projects_administrators ADD CONSTRAINT FK_ProjectAdmins_Users FOREIGN KEY (project_id) REFERENCES users;
ALTER TABLE projects_administrators ADD CONSTRAINT FK_ProjectAdmins_Projects FOREIGN KEY (user_id) REFERENCES projects;
ALTER TABLE projects_contributors ADD CONSTRAINT FK_ProjectContributors_Users FOREIGN KEY (project_id) REFERENCES users;
ALTER TABLE projects_contributors ADD CONSTRAINT FK_ProjectContributors_Projects FOREIGN KEY (user_id) REFERENCES projects;
ALTER TABLE tasks ADD CONSTRAINT FK_Tasks_Projects FOREIGN KEY (project_id) REFERENCES projects;
