package com.example.demopollingdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class TweetListener {
    private static final Logger log = LoggerFactory.getLogger(TweetListener.class);

    @JmsListener(destination = "tweet")
    void handleMessage(Message<String> message) {
        String text = message.getPayload();
        log.info("Received {}", text);
    }
}
