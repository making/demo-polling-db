package com.example.demopollingdb;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "scheduler")
@Validated
public class SchedulerProperties {
    @NotEmpty
    private String selectJobSql = "SELECT id, args, created_at, updated_at FROM job WHERE id > ? ORDER BY id ASC";
    @NotEmpty
    private String selectLastJobIdSql = "SELECT job_id FROM job_counter ORDER BY id DESC LIMIT 1";
    @NotEmpty
    private String insertJobSql = "INSERT INTO job(args) VALUES(?)";
    @NotEmpty
    private String insertJobCounterSql = "INSERT INTO job_counter(job_id) VALUES(?)";
    @NotEmpty
    private String cron = "0 * * * * *";

    public String getSelectJobSql() {
        return selectJobSql;
    }

    public void setSelectJobSql(String selectJobSql) {
        this.selectJobSql = selectJobSql;
    }

    public String getSelectLastJobIdSql() {
        return selectLastJobIdSql;
    }

    public void setSelectLastJobIdSql(String selectLastJobIdSql) {
        this.selectLastJobIdSql = selectLastJobIdSql;
    }

    public String getInsertJobSql() {
        return insertJobSql;
    }

    public void setInsertJobSql(String insertJobSql) {
        this.insertJobSql = insertJobSql;
    }

    public String getInsertJobCounterSql() {
        return insertJobCounterSql;
    }

    public void setInsertJobCounterSql(String insertJobCounterSql) {
        this.insertJobCounterSql = insertJobCounterSql;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
