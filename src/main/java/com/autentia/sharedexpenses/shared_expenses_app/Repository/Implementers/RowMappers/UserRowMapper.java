package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.UserEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserEntity> {

    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserEntity(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

}
