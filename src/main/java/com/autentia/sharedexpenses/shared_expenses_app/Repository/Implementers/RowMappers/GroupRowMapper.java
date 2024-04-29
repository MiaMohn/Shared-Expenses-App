package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Group;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Group(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description")
        );
    }
}
