package com.example.demopollingdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DemoProcessor {
    private static final Logger log = LoggerFactory.getLogger(DemoProcessor.class);
    private final JmsTemplate jmsTemplate;

    public DemoProcessor(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void process(List<Job> jobs) {
        for (Job job : jobs) {
            log.info("Hi job{}", job.getId());
            for (String arg : job.getArgs()) {
                jmsTemplate.convertAndSend("tweet", arg);
            }
            log.info("Done job{}", job.getId());
        }
    }
}
