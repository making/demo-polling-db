package com.example.demopollingdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DemoProcessor {
    private static final Logger log = LoggerFactory.getLogger(DemoProcessor.class);
    private final JdbcTemplate jdbcTemplate;

    public DemoProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void process(List<Job> jobs) {
        for (Job job : jobs) {
            log.info("Hi job{}", job.getId());
            List<Object[]> args = job.getArgs().stream()
                    .map(arg -> new Object[]{UUID.randomUUID().toString(), arg})
                    .collect(Collectors.toList());
            int[] counts = this.jdbcTemplate.batchUpdate("INSERT INTO tweet(tweet_id, text) VALUES(?,?)", args);
            log.info("Tweet {}", Arrays.stream(counts).sum());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
