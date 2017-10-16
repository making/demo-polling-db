package com.example.demopollingdb;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class JobRowMapper implements RowMapper<Job> {
    @Override
    public Job mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String args = rs.getString("args");
        Instant createdAt = rs.getTimestamp("created_at").toInstant();
        Instant updatedAt = rs.getTimestamp("updated_at").toInstant();
        return new Job(id, args, createdAt, updatedAt);
    }
}
