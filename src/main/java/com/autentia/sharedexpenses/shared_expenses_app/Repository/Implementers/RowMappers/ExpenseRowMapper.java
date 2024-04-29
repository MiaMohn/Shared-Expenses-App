package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers;

import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseRowMapper implements RowMapper<ExpenseEntity> {

    @Override
    public ExpenseEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new ExpenseEntity(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getDouble("amount"),
                rs.getLong("user_id"),
                rs.getTimestamp("expense_date")
        );
    }
}
