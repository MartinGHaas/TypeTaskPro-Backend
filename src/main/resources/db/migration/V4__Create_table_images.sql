CREATE TABLE images (
  id VARCHAR(36) NOT NULL DEFAULT (uuid_generate_v4()::varchar),
  data OID NOT NULL,
  PRIMARY KEY (id)
);