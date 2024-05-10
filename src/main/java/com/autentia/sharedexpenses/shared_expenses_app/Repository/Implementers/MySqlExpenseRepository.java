package com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Entities.ExpenseEntity;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.IExpenseRepository;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.RowMappers.ExpenseRowMapper;


import com.autentia.sharedexpenses.shared_expenses_app.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MySqlExpenseRepository implements IExpenseRepository {

    //Queries:
    public static final String FIND_ALL_EXPENSES = "SELECT expense.id AS id, expense.description, expense.amount, expense.expense_date, user.id AS user_id, user.name FROM expense JOIN user ON expense.user_id = user.id ORDER BY expense.expense_date DESC";
    public static final String INSERT_EXPENSE = "INSERT INTO expense(id, description, amount, expense_date, user_id) VALUES (?, ?, ?, ?, ?)";
    public static final String FIND_EXPENSE_BY_ID = "SELECT expense.id AS id, expense.description, expense.amount, expense.expense_date, user.id AS user_id, user.name FROM expense JOIN user ON expense.user_id = user.id WHERE expense.id = ?";
    public static final String FIND_EXPENSES_BY_USER_ID = "SELECT expense.id AS id, expense.description, expense.amount, expense.expense_date, user.id AS user_id, user.name FROM expense JOIN user ON expense.user_id = user.id WHERE user.id = ?";
    public static final String UPDATE_EXPENSE = "UPDATE expense SET description = ?, amount = ?, user_id = ? WHERE id = ? ";
    public static final String DELETE_EXPENSE_BY_ID = "DELETE FROM expense WHERE id = ?";
    public static final String DOES_EXPENSE_EXISTS = "SELECT COUNT(*) FROM expense WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

    @Autowired
    public MySqlExpenseRepository(JdbcTemplate jdbcTemplate, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    @Override
    public List<Expense> findAll() {

        List<ExpenseEntity> expenseEntityList = jdbcTemplate.query(FIND_ALL_EXPENSES, new ExpenseRowMapper());

        return expenseEntityList.stream().map(entity -> {
            Optional<User> user = userService.getUserById(entity.getUser_id());
            return user.map(u -> toExpense(entity, u)).orElse(null);
        }).collect(Collectors.toList());

    }

    @Override
    public void save(Expense expense) {

        if (expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount can't be 0 or less");
        }

        jdbcTemplate.update(INSERT_EXPENSE, expense.getId(), expense.getDescription(), expense.getAmount(), expense.getExpenseDate(), expense.getUser().getId());
    }

    @Override
    public Optional<Expense> findById(long id) {

        try {
            ExpenseEntity entity = jdbcTemplate.queryForObject(FIND_EXPENSE_BY_ID, new ExpenseRowMapper(), id);
            User user = userService.getUserById(entity.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
            return Optional.of(toExpense(entity, user));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }

    }

    @Override
    public List<Expense> findByUserId(long user_id) {

        User user = userService.getUserById(user_id).orElseThrow(() -> new RuntimeException("User not found"));

        List<ExpenseEntity> entities = jdbcTemplate.query(FIND_EXPENSES_BY_USER_ID, new ExpenseRowMapper(), user_id);

        return entities.stream()
                .map(entity -> toExpense(entity, user))
                .collect(Collectors.toList());
    }


    @Override
    public void update(Expense expense, long id) {
        jdbcTemplate.update(UPDATE_EXPENSE, expense.getDescription(), expense.getAmount(), expense.getUser().getId(), id);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_EXPENSE_BY_ID, id);
    }

    @Override
    public boolean existsById(long id) {
        return jdbcTemplate.queryForObject(DOES_EXPENSE_EXISTS, Integer.class, id) > 0;
    }



    //Conversion functions
    private Expense toExpense(ExpenseEntity expenseEntity, User user) {
        return new Expense(
                expenseEntity.getId(),
                expenseEntity.getDescription(),
                expenseEntity.getAmount(),
                user,
                expenseEntity.getExpenseDate()
        );
    }

}
