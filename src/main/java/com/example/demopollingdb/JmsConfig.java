package com.example.demopollingdb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {
    @Bean
    public JmsTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
        return new JmsTransactionManager(connectionFactory);
    }

    @Bean
    public ChainedTransactionManager chainedTransactionManager(DataSourceTransactionManager transactionManager) {
        JmsTransactionManager jmsTransactionManager = jmsTransactionManager(null);
        return new ChainedTransactionManager(jmsTransactionManager, transactionManager);
    }
}
