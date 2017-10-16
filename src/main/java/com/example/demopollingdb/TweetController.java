package com.example.demopollingdb;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@RestController
public class TweetController {
    private final JdbcTemplate jdbcTemplate;

    public TweetController(DataSource dataSource, SchedulerProperties props) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("tweets")
    public List<Map<String, Object>> getFinishedJobs() {
        return this.jdbcTemplate.queryForList("SELECT * FROM tweet ORDER BY created_at DESC LIMIT 10");
    }
}
