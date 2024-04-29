package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong("user_id"),
                rs.getString("name")
        );

        return new Expense(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getDouble("amount"),
                user,
                rs.getTimestamp("expense_date")
        );
    }
}
