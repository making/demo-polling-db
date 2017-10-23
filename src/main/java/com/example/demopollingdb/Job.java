package com.example.demopollingdb;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Job implements Serializable {
    private final long id;
    private final List<String> args;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Job(long id, List<String> args, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.args = Collections.unmodifiableList(args);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Job(long id, String args, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.args = Arrays.asList(args.split(","));
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public List<String> getArgs() {
        return args;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", args=" + args +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
