package com.example.demopollingdb;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class JobController {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insert;

    public JobController(@JobDataSource DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("job")
                .usingColumns("args")
                .usingGeneratedKeyColumns("id");
    }

    @PostMapping("jobs")
    public Number addJob(@RequestBody JsonNode body) {
        String args = StreamSupport.stream(body.get("args").spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.joining(","));
        KeyHolder keyHolder = this.insert.executeAndReturnKeyHolder(Collections.singletonMap("args", args));
        return keyHolder.getKey();
    }

    @GetMapping("jobs")
    public List<Map<String, Object>> getFinishedJobs() {
        return this.jdbcTemplate.queryForList("SELECT * FROM job_counter ORDER BY created_at DESC LIMIT 10");
    }
}
