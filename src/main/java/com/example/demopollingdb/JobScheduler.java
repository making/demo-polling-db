package com.example.demopollingdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobScheduler {
    private static final Logger log = LoggerFactory.getLogger(JobScheduler.class);
    private final DemoProcessor processor;
    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate tx;
    private final SchedulerProperties props;
    private static final RowMapper<Job> jobRowMapper = new JobRowMapper();

    public JobScheduler(DemoProcessor processor,
                        @JobDataSource DataSource jobDataSource,
                        @JobDataSource PlatformTransactionManager jobTransactionManager,
                        PlatformTransactionManager appTransactionManager,
                        SchedulerProperties props) {
        this.processor = processor;
        this.jdbcTemplate = new JdbcTemplate(jobDataSource);
        this.tx = new TransactionTemplate(jobTransactionManager);
        // If you want to commit job tx and app tx at the same time, use following line.
        //this.tx = new TransactionTemplate(new org.springframework.data.transaction.ChainedTransactionManager(jobTransactionManager, appTransactionManager));
        this.props = props;
    }

    @Scheduled(cron = "${scheduler.cron}")
    public void scheduler() {
        log.info("=============== Do scheduling ...");

        long lastJobId = getLastJobId();
        List<Job> jobs = this.jdbcTemplate.query(this.props.getSelectJobSql(), jobRowMapper, lastJobId);

        if (!jobs.isEmpty()) {
            tx.execute(status -> {
                log.info(">>>> Launch {}", jobs);
                // This process is synchronous and done in single thread.
                this.processor.process(jobs);
                this.updateJobCounter(jobs);
                log.info("<<<<< Finished {}", jobs);
                return null;
            });
        }

        log.info("=============== Done");
    }

    private void updateJobCounter(List<Job> jobs) {
        List<Object[]> jobIds = jobs.stream()
                .map(j -> new Object[]{j.getId()})
                .collect(Collectors.toList());
        this.jdbcTemplate.batchUpdate(this.props.getInsertJobCounterSql(), jobIds);
    }

    private long getLastJobId() {
        try {
            return this.jdbcTemplate.queryForObject(this.props.getSelectLastJobIdSql(), Long.class);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }
}
