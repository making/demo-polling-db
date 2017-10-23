CREATE TABLE IF NOT EXISTS tweet (
  tweet_id   VARCHAR(36) PRIMARY KEY,
  text       VARCHAR(120),
  created_at TIMESTAMP DEFAULT now()
)