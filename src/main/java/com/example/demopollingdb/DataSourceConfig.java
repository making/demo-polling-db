package com.example.demopollingdb;

import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @FlywayDataSource
    @JobDataSource
    @ConfigurationProperties(prefix = "job.datasource")
    public DataSource jobDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @JobDataSource
    public PlatformTransactionManager jobTransactionManager() {
        return new DataSourceTransactionManager(jobDataSource());
    }
}
