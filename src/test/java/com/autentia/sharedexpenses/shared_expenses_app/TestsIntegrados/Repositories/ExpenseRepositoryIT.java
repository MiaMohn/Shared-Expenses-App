package com.autentia.sharedexpenses.shared_expenses_app.TestsIntegrados.Repositories;

import com.autentia.sharedexpenses.shared_expenses_app.Domain.Expense;
import com.autentia.sharedexpenses.shared_expenses_app.Domain.User;
import com.autentia.sharedexpenses.shared_expenses_app.Repository.Implementers.MySqlExpenseRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ExpenseRepositoryIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MySqlExpenseRepository expenseRepository;

    private Timestamp date = new Timestamp(System.currentTimeMillis());

    @BeforeEach
    public void setup() {
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS user(id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(80) NOT NULL)");
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS expense(" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, description VARCHAR(80) NOT NULL, " +
                "amount DECIMAL(10,2) NOT NULL, expense_date TIMESTAMP, user_id BIGINT NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES user(id) ON DELETE CASCADE)");

        jdbcTemplate.update("DELETE FROM expense");
        jdbcTemplate.update("DELETE FROM user");

        jdbcTemplate.update("INSERT INTO user (id, name) VALUES (1, 'Maria'), (2, 'Belen'), (3, 'Juan')");
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM expense");
        jdbcTemplate.update("DELETE FROM user");
    }

    @Test
    public void shouldFindAllExpenses() {

        expenseRepository.save(new Expense(1L, "Compra", 54.67, new User(1L, "Maria"), date));
        expenseRepository.save(new Expense(2L, "Taxi", 10, new User(1L, "Maria"), date));
        expenseRepository.save(new Expense(3L, "Cena", 100, new User(2L, "Belen"), date));

        List<Expense> expenses = expenseRepository.findAll();

        assertEquals(3, expenses.size());
    }

    @Test
    public void shouldSaveExpense() {

        Expense expense = new Expense(1L, "Compra", 54.67, new User(1L, "Maria"), date);
        expenseRepository.save(expense);

        assertTrue(expenseRepository.existsById(expense.getId()));
    }

    @Test
    public void shouldFindExpenseById() {

        Expense expense = new Expense(1L, "Compra", 54.67, new User(1L, "Maria"), date);
        expenseRepository.save(expense);

        Optional<Expense> createdExpense = expenseRepository.findById(expense.getId());
        assertTrue(createdExpense.isPresent());
    }

    @Test
    public void shouldFindExpensesByUserId() {

        expenseRepository.save(new Expense(1L, "Compra", 54.67, new User(1L, "Maria"), date));
        expenseRepository.save(new Expense(2L, "Taxi", 10, new User(1L, "Maria"), date));
        expenseRepository.save(new Expense(3L, "Cena", 100, new User(2L, "Belen"), date));

        List<Expense> expensesByUser = expenseRepository.findByUserId(1L);

        assertEquals(2, expensesByUser.size());

    }

    @Test
    public void shouldUpdateExpense() {

        Expense expense = new Expense(4L, "Test", 25.50, new User(3L, "Juan"), date);
        expenseRepository.save(expense);

        expense.setDescription("Test updated");
        expense.setAmount(20);
        expenseRepository.update(expense, expense.getId());

        Optional<Expense> updatedExpense = expenseRepository.findById(expense.getId());

        assertEquals("Test updated", updatedExpense.get().getDescription());
        assertEquals(20, updatedExpense.get().getAmount());

    }

    @Test
    public void shouldDeleteExpense() {

        Expense expense = new Expense(5L, "Test", 25.50, new User(2L, "Belen"), date);
        expenseRepository.save(expense);
        assertTrue(expenseRepository.existsById(expense.getId()));

        expenseRepository.deleteById(expense.getId());
        assertFalse(expenseRepository.existsById(expense.getId()));
    }

    @Test
    public void shouldVerifyIfExpenseExists() {

        Expense expense = new Expense(1L, "Compra", 54.67, new User(1L, "Maria"), date);
        expenseRepository.save(expense);

        assertTrue(expenseRepository.existsById(expense.getId()));
    }
}
