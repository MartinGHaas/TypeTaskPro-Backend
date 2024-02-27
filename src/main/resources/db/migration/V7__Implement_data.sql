CREATE TABLE user_metadata (
  PRIMARY KEY (id),
  last_touched_project_id INTEGER,
  projects_completed INTEGER,
  projects_in_development INTEGER,
  tasks_completed INTEGER,
  tasks_in_progress INTEGER,
  tasks_todo INTEGER,
  total_projects INTEGER,
  total_tasks INTEGER,
  id BIGINT NOT NULL,
  last_touched_task_id VARCHAR(36)
);

ALTER TABLE users
ADD COLUMN metadata_id BIGINT UNIQUE,
ADD CONSTRAINT FK_Users_Metadata
FOREIGN KEY (metadata_id) REFERENCES user_metadata;