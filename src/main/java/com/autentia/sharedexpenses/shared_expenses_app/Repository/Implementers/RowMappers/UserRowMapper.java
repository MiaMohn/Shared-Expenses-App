package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

}
