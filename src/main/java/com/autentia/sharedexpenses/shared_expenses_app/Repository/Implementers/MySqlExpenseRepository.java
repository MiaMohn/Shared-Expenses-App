package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IExpenseRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers.ExpenseRowMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MySqlExpenseRepository implements IExpenseRepository {

    //public static final String FIND_ALL_EXPENSES = "SELECT Expense.id AS id, Expense.description, Expense.amount, Expense.expense_date, User.id AS user_id, User.name FROM Expense JOIN User ON Expense.user_id = User.id ORDER BY Expense.expense_date DESC";
    public static final String FIND_ALL_EXPENSES = "SELECT Expense.id AS id, Expense.description, Expense.amount, Expense.expense_date, User.id AS user_id, User.name AS username FROM Expense JOIN User ON Expense.user_id = User.id ORDER BY Expense.expense_date DESC;";
    public static final String INSERT_EXPENSE = "INSERT INTO Expense(id, description, amount, expense_date, user_id) VALUES (?, ?, ?, ?, ?)";
    public static final String FIND_EXPENSE_BY_ID = "SELECT Expense.id AS id, Expense.description, Expense.amount, Expense.expense_date, User.id AS user_id, User.name FROM Expense JOIN User ON Expense.user_id = User.id WHERE Expense.id = ?";
    public static final String FIND_EXPENSES_BY_USER_ID = "SELECT Expense.id AS id, Expense.description, Expense.amount, Expense.expense_date, User.id AS user_id, User.name FROM Expense JOIN User ON Expense.user_id = User.id WHERE User.id = ?";
    public static final String UPDATE_EXPENSE = "UPDATE Expense SET description = ?, amount = ?, user_id = ? WHERE id = ? ";
    public static final String DELETE_EXPENSE_BY_ID = "DELETE FROM Expense WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MySqlExpenseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Expense> findAll() {
        return jdbcTemplate.query(FIND_ALL_EXPENSES, new ExpenseRowMapper());
    }

    @Override
    public void save(Expense expense) {
        jdbcTemplate.update(INSERT_EXPENSE, expense.getId(), expense.getDescription(), expense.getAmount(), expense.getExpenseDate(), expense.getUser().getId());
    }

    @Override
    public Optional<Expense> findById(long id) {
        try {
            Expense expense = jdbcTemplate.queryForObject(FIND_EXPENSE_BY_ID, new ExpenseRowMapper(), new Object[]{id});
            return Optional.ofNullable(expense);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Expense> findByUserId(long user_id) {
        return jdbcTemplate.query(FIND_EXPENSES_BY_USER_ID, new ExpenseRowMapper(), new Object[]{user_id});
    }


    @Override
    public void update(Expense expense, long id) {
        jdbcTemplate.update(UPDATE_EXPENSE, expense.getDescription(), expense.getAmount(), expense.getUser().getId(), id);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_EXPENSE_BY_ID, id);
    }
}
