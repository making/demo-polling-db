CREATE TABLE job (
  id         LONG PRIMARY KEY            AUTO_INCREMENT,
  args       VARCHAR(1024) NOT NULL,
  created_at TIMESTAMP     NOT NULL      DEFAULT now(),
  updated_at TIMESTAMP     NOT NULL      DEFAULT now()
);


CREATE TABLE job_counter (
  id         LONG PRIMARY KEY        AUTO_INCREMENT,
  job_id     LONG      NOT NULL,
  created_at TIMESTAMP NOT NULL      DEFAULT now(),
  updated_at TIMESTAMP NOT NULL      DEFAULT now(),
  FOREIGN KEY (job_id) REFERENCES job (id)
);

CREATE INDEX idx_job_created_at
  ON job (created_at);
CREATE INDEX idx_job_updated_at
  ON job (updated_at);

CREATE INDEX idx_job_counter_created_at
  ON job_counter (created_at);
CREATE INDEX idx_job_counter_updated_at
  ON job_counter (updated_at);