package com.example.demopollingdb;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class JobController {
    private final JmsTemplate jmsTemplate;
    private static final AtomicLong counter = new AtomicLong(0);
    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    public JobController(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping("jobs")
    @Transactional(transactionManager = "jmsTransactionManager")
    public void addJob(@RequestBody JsonNode body) {
        List<String> args = StreamSupport.stream(body.get("args").spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.toList());
        Instant now = Instant.now();
        Job job = new Job(counter.incrementAndGet(), args, now, now);
        log.info("Send {}", job);
        jmsTemplate.convertAndSend("jobs", job);
    }
}
